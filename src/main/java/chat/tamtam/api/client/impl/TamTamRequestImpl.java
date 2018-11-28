package chat.tamtam.api.client.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.Future;

import chat.tamtam.api.client.ClientResponse;
import chat.tamtam.api.client.TamTamRequest;
import chat.tamtam.api.client.TamTamSerializer;
import chat.tamtam.api.client.TamTamTransportClient;
import chat.tamtam.api.client.exceptions.APIException;
import chat.tamtam.api.client.exceptions.ClientException;
import chat.tamtam.api.client.exceptions.TransportClientException;
import chat.tamtam.api.requests.queries.TamTamQuery;

/**
 * @author alexandrchuprin
 */
public class TamTamRequestImpl<T> implements TamTamRequest<T> {
    private final TamTamTransportClient transport;
    private final TamTamSerializer serializer;
    private final String accessToken;
    private final TamTamQuery<T> query;

    public TamTamRequestImpl(TamTamTransportClient transport, TamTamSerializer serializer, String accessToken,
                             TamTamQuery<T> query) {
        this.transport = transport;
        this.serializer = serializer;
        this.accessToken = accessToken;
        this.query = query;
    }

    @Override
    public T get() throws APIException, ClientException {
        try {
            String url = buildURL(query.getURL(), query.getParams());
            byte[] requestBody = serializer.serialize(query.getBody());
            ClientResponse response;
            if (query.isPost()) {
                response = transport.post(url, requestBody);
            } else {
                response = transport.get(url);
            }

            String responseBody = response.getBodyAsString();
            if (response.getStatusCode() / 100 != 2) {
                throw new ClientException(responseBody);
            }

            return serializer.deserialize(responseBody, query.getResponseType());
        } catch (TransportClientException e) {
            throw new ClientException(e);
        }
    }

    private String buildURL(String url, List<RequestParam<?>> params) throws ClientException {
        StringBuilder sb = new StringBuilder(url);
        boolean appendDelimiter = false;
        if (url.indexOf('?') == -1) {
            sb.append('?');
        }

        sb.append("access_token=").append(accessToken);

        for (RequestParam<?> param : params) {
            String name = param.getName();
            if (param.getValue() == null) {
                if (param.isRequired()) {
                    throw new IllegalArgumentException("Required param " + name + " is missing.");
                }

                continue;
            }

            if (appendDelimiter) {
                sb.append('&');
            }

            sb.append(name);
            sb.append('=');
            try {
                sb.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new ClientException(e);
            }

            appendDelimiter = true;
        }

        return sb.toString();
    }

    @Override
    public Future<T> enqueue() {
        throw new UnsupportedOperationException("Async is unsupported now.");
    }
}
