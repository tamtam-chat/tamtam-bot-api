/*
 * ---------------------------------------------------------------------
 * TamTam chat Bot API
 * ---------------------------------------------------------------------
 * Copyright (C) 2018 Mail.Ru Group
 * ---------------------------------------------------------------------
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
 * ---------------------------------------------------------------------
 */

package chat.tamtam.botapi.client;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

import chat.tamtam.botapi.Version;
import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.client.impl.OkHttpTransportClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.exceptions.ExceptionMapper;
import chat.tamtam.botapi.exceptions.RequiredParameterMissingException;
import chat.tamtam.botapi.exceptions.SerializationException;
import chat.tamtam.botapi.exceptions.ServiceNotAvailableException;
import chat.tamtam.botapi.exceptions.TransportClientException;
import chat.tamtam.botapi.model.Error;
import chat.tamtam.botapi.queries.QueryParam;
import chat.tamtam.botapi.queries.TamTamQuery;
import chat.tamtam.botapi.queries.upload.TamTamUploadQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alexandrchuprin
 */
public class TamTamClient implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    static final String ENDPOINT_ENV_VAR_NAME = "TAMTAM_BOTAPI_ENDPOINT";
    private static final String ENDPOINT = "https://botapi.tamtam.chat";
    private final String accessToken;
    private final TamTamTransportClient transport;
    private final TamTamSerializer serializer;
    private final String endpoint;

    public TamTamClient(String accessToken, TamTamTransportClient transport, TamTamSerializer serializer) {
        this.endpoint = createEndpoint();
        this.accessToken = Objects.requireNonNull(accessToken, "accessToken");
        this.transport = Objects.requireNonNull(transport, "transport");
        this.serializer = Objects.requireNonNull(serializer, "serializer");
    }

    public static TamTamClient create(String accessToken) {
        Objects.requireNonNull(accessToken, "No access token given. Get it using https://tt.me/primebot");
        OkHttpTransportClient transport = new OkHttpTransportClient();
        JacksonSerializer serializer = new JacksonSerializer();
        return new TamTamClient(accessToken, transport, serializer);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public TamTamSerializer getSerializer() {
        return serializer;
    }

    public TamTamTransportClient getTransport() {
        return transport;
    }

    public <T> Future<T> newCall(TamTamUploadQuery<T> query) throws ClientException {
        try {
            String url = buildURL(query);
            Future<ClientResponse> call = query.getUploadExec().newCall(getTransport());
            return new FutureResult<>(call, rawResponse -> handleResponse(rawResponse, query, url));
        } catch (InterruptedException e) {
            throw new ClientException(e);
        }
    }

    public <T> Future<T> newCall(TamTamQuery<T> query) throws ClientException {
        TamTamTransportClient.Method method = query.getMethod();
        String url = buildURL(query);
        byte[] requestBody = getSerializer().serialize(query.getBody());

        Future<ClientResponse> call;
        try {
            switch (method) {
                case GET:
                    call = getTransport().get(url);
                    break;
                case POST:
                    call = getTransport().post(url, requestBody);
                    break;
                case PUT:
                    call = getTransport().put(url, requestBody);
                    break;
                case DELETE:
                    call = getTransport().delete(url);
                    break;
                case PATCH:
                    call = getTransport().patch(url, requestBody);
                    break;
                default:
                    throw new ClientException(400, "Method " + method.name() + " is not supported.");
            }
        } catch (TransportClientException e) {
            throw new ClientException(e);
        }

        return new FutureResult<>(call, rawResponse -> handleResponse(rawResponse, query, url));
    }

    @Override
    public void close() throws IOException {
        transport.close();
    }

    public String buildURL(TamTamQuery<?> query) throws ClientException {
        String url = query.getUrl();
        StringBuilder sb = new StringBuilder(url);
        if (!url.regionMatches(true, 0, "http", 0, 4)) {
            sb.insert(0, getEndpoint());
        }

        if (url.indexOf('?') == -1) {
            sb.append('?');
        } else {
            sb.append('&');
        }

        sb.append("access_token=").append(getAccessToken());
        sb.append('&');
        sb.append("v=").append(Version.get());

        List<QueryParam<?>> params = query.getParams();
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

    String getEnvironment(String name) {
        return System.getenv(name);
    }

    protected String encodeParam(String paramValue) throws UnsupportedEncodingException {
        return URLEncoder.encode(paramValue, StandardCharsets.UTF_8.name());
    }

    private <T> T handleResponse(ClientResponse response, TamTamQuery<T> query, String url) throws ClientException, APIException {
        String responseBody = response.getBodyAsString();
        if (response.getStatusCode() == 503) {
            LOG.error("Error 503 while executing query, query url: {}, query body: {}, responseBody: {}",
                    url,
                    query.getBody(),
                    responseBody);
            throw new ServiceNotAvailableException(responseBody);
        }

        TamTamSerializer serializer = getSerializer();
        if (response.getStatusCode() / 100 == 2) {
            return serializer.deserialize(responseBody, query.getResponseType());
        }

        LOG.error("Error while executing query, query url: {}, query body: {}, responseBody: {}",
                url,
                query.getBody(),
                responseBody);

        Error error;
        try {
            error = serializer.deserialize(responseBody, Error.class);
        } catch (SerializationException e) {
            throw new APIException(response.getStatusCode(), responseBody);
        }

        if (error == null) {
            throw new APIException(response.getStatusCode());
        }

        throw ExceptionMapper.map(response.getStatusCode(), error);
    }

    private String createEndpoint() {
        String env = getEnvironment(ENDPOINT_ENV_VAR_NAME);
        if (env != null) {
            return env;
        }

        return System.getProperty("tamtam.botapi.endpoint", ENDPOINT);
    }
}
