/*
 * ------------------------------------------------------------------------
 * TamTam chat Bot API
 * ------------------------------------------------------------------------
 * Copyright (C) 2018 Mail.Ru Group
 * ------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ------------------------------------------------------------------------
 */

package chat.tamtam.botapi.queries;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jetbrains.annotations.NotNull;

import chat.tamtam.botapi.Version;
import chat.tamtam.botapi.client.ClientResponse;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.TamTamSerializer;
import chat.tamtam.botapi.client.TamTamTransportClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.exceptions.ExceptionMapper;
import chat.tamtam.botapi.exceptions.RequiredParameterMissingException;
import chat.tamtam.botapi.exceptions.SerializationException;
import chat.tamtam.botapi.exceptions.ServiceNotAvailableException;
import chat.tamtam.botapi.exceptions.TransportClientException;
import chat.tamtam.botapi.model.Error;

/**
 * @author alexandrchuprin
 */
public class TamTamQuery<T> {
    private final TamTamClient tamTamClient;
    private final String url;
    private final Class<T> responseType;
    private final Object body;
    private final Method method;
    private List<QueryParam<?>> params;

    public TamTamQuery(TamTamClient tamTamClient, String url, Class<T> responseType) {
        this(tamTamClient, url, null, responseType, Method.POST);
    }

    public TamTamQuery(TamTamClient tamTamClient, String url, Class<T> responseType, Method method) {
        this(tamTamClient, url, null, responseType, method);
    }

    public TamTamQuery(TamTamClient tamTamClient, String url, Object body, Class<T> responseType, Method method) {
        this.tamTamClient = tamTamClient;
        this.url = url;
        this.responseType = responseType;
        this.body = body;
        this.method = method;
    }

    public T execute() throws APIException, ClientException {
        try {
            ClientResponse response = call().get();
            return deserialize(response);
        } catch (InterruptedException e) {
            throw new ClientException("Current request was interrupted", e);
        } catch (ExecutionException e) {
            throw new ClientException("Request " + url + " failed", e.getCause());
        }
    }

    public Future<T> enqueue() throws ClientException {
        return new FutureResult(call());
    }

    void addParam(@NotNull QueryParam param) {
        if (params == null) {
            params = new ArrayList<>();
        }

        params.add(param);
    }

    protected Future<ClientResponse> call() throws ClientException {
        String url = buildURL();
        byte[] requestBody = tamTamClient.getSerializer().serialize(body);
        TamTamTransportClient transport = tamTamClient.getTransport();

        try {
            switch (method) {
                case GET:
                    return transport.get(url);
                case POST:
                    return transport.post(url, requestBody);
                case PUT:
                    return transport.put(url, requestBody);
                case DELETE:
                    return transport.delete(url);
                case PATCH:
                    return transport.patch(url, requestBody);
                default:
                    throw new ClientException(400, "Method " + method.name() + " is not supported.");
            }
        } catch (TransportClientException e) {
            throw new ClientException(e);
        }
    }

    static String substitute(String pathTemplate, Object... substitutions) {
        StringBuilder sb = new StringBuilder();
        int nextSubst = 0;
        char[] chars = pathTemplate.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '{') {
                i = pathTemplate.indexOf('}', i);
                sb.append(substitutions[nextSubst++]);
                continue;
            }

            sb.append(c);
        }

        return sb.toString();
    }

    private T deserialize(ClientResponse response) throws ClientException, APIException {
        String responseBody = response.getBodyAsString();
        if (response.getStatusCode() == 503) {
            throw new ServiceNotAvailableException(responseBody);
        }

        TamTamSerializer serializer = tamTamClient.getSerializer();
        if (response.getStatusCode() / 100 != 2) {
            try {
                Error error = serializer.deserialize(responseBody, Error.class);
                if (error == null) {
                    throw new APIException(response.getStatusCode());
                }

                throw ExceptionMapper.map(response.getStatusCode(), error);
            } catch (SerializationException e) {
                throw new APIException(response.getStatusCode(), responseBody);
            }
        }

        return serializer.deserialize(responseBody, responseType);
    }

    String buildURL() throws ClientException {
        StringBuilder sb = new StringBuilder(url);
        if (!url.regionMatches(true, 0, "http", 0, 4)) {
            sb.insert(0, tamTamClient.getEndpoint());
        }

        if (url.indexOf('?') == -1) {
            sb.append('?');
        } else {
            sb.append('&');
        }

        sb.append("access_token=").append(tamTamClient.getAccessToken());
        sb.append('&');
        sb.append("v=").append(Version.get());

        if (params == null) {
            return sb.toString();
        }

        for (QueryParam<?> param : params) {
            String name = param.getName();
            if (param.getValue() == null) {
                if (param.isRequired()) {
                    throw new RequiredParameterMissingException("Required param " + name + " is missing.");
                }

                continue;
            }

            sb.append('&');
            sb.append(name);
            sb.append('=');
            try {
                sb.append(encodeParam(param.format()));
            } catch (UnsupportedEncodingException e) {
                throw new ClientException(e);
            }
        }

        return sb.toString();
    }

    protected String encodeParam(String paramValue) throws UnsupportedEncodingException {
        return URLEncoder.encode(paramValue, StandardCharsets.UTF_8.name());
    }

    protected enum Method {
        GET, POST, PUT, HEAD, DELETE, PATCH, OPTIONS
    }

    private class FutureResult implements Future<T> {
        private final Future<ClientResponse> delegate;

        private FutureResult(Future<ClientResponse> delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return delegate.cancel(mayInterruptIfRunning);
        }

        @Override
        public boolean isCancelled() {
            return delegate.isCancelled();
        }

        @Override
        public boolean isDone() {
            return delegate.isDone();
        }

        @Override
        public T get() throws InterruptedException, ExecutionException {
            try {
                return deserialize(delegate.get());
            } catch (ClientException | APIException e) {
                throw new ExecutionException(e);
            } catch (ExecutionException e) {
                throw unwrap(e);
            }
        }

        @Override
        public T get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException,
                TimeoutException {
            try {
                return deserialize(delegate.get(timeout, unit));
            } catch (ClientException | APIException e) {
                throw new ExecutionException(e);
            } catch (ExecutionException e) {
                throw unwrap(e);
            }
        }

        private ExecutionException unwrap(ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause == null) {
                return e;
            }

            if (cause instanceof TransportClientException) {
                return new ExecutionException(new ClientException(cause));
            }

            return e;
        }
    }
}
