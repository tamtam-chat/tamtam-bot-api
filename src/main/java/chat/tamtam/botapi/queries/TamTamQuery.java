package chat.tamtam.botapi.queries;

/*-
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jetbrains.annotations.NotNull;

import chat.tamtam.botapi.client.ClientResponse;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.TamTamTransportClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.exceptions.TransportClientException;

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
            throw new ClientException(e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ClientException) {
                throw (ClientException) cause;
            }
            if (cause instanceof APIException) {
                throw (APIException) cause;
            }

            throw new ClientException("Request " + url + " failed", cause);
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
                default:
                    throw new ClientException(400, "Method " + method.name() + " is not supported.");
            }
        } catch (TransportClientException e) {
            throw new ClientException(e);
        }
    }

    private T deserialize(ClientResponse response) throws ClientException, APIException {
        String responseBody = response.getBodyAsString();
        if (response.getStatusCode() / 100 != 2) {
            throw new ClientException(response.getStatusCode(), responseBody);
        }

        return tamTamClient.getSerializer().deserialize(responseBody, responseType);
    }

    private String buildURL() throws ClientException {
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

        if (params == null) {
            return sb.toString();
        }

        for (QueryParam<?> param : params) {
            String name = param.getName();
            if (param.getValue() == null) {
                if (param.isRequired()) {
                    throw new IllegalArgumentException("Required param " + name + " is missing.");
                }

                continue;
            }

            sb.append('&');
            sb.append(name);
            sb.append('=');
            try {
                sb.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new ClientException(e);
            }
        }

        return sb.toString();
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
            }
        }

        @Override
        public T get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException,
                TimeoutException {
            try {
                return deserialize(delegate.get(timeout, unit));
            } catch (ClientException | APIException e) {
                throw new ExecutionException(e);
            }
        }
    }

    protected enum Method {
        GET, POST, PUT, HEAD, DELETE
    }
}
