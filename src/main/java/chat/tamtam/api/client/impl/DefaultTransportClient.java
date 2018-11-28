package chat.tamtam.api.client.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import chat.tamtam.api.client.ClientResponse;
import chat.tamtam.api.client.TamTamTransportClient;
import chat.tamtam.api.client.exceptions.TransportClientException;
import one.nio.http.HttpClient;
import one.nio.http.HttpException;
import one.nio.http.Response;
import one.nio.net.ConnectionString;
import one.nio.pool.PoolException;

/**
 * @author alexandrchuprin
 */
public class DefaultTransportClient implements TamTamTransportClient {
    private static String ENDPOINT = "https://botapi.tamtam.chat";

    private final String endpoint;
    private final HttpClient httpClient;

    public DefaultTransportClient() {
        this.endpoint = createEndpoint();
        this.httpClient = new HttpClient(new ConnectionString(getEndpoint()));
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public ClientResponse get(String url) throws TransportClientException {
        Response response;
        try {
            response = httpClient.get(url);
        } catch (InterruptedException | PoolException | IOException | HttpException e) {
            throw new TransportClientException("Failed to execute request: " + url, e);
        }

        return new ClientResponse(response.getStatus(), response.getBody(), parseHeaders(response.getHeaders()));
    }

    @Override
    public ClientResponse post(String url, byte[] body) throws TransportClientException {
        Response response;
        try {
            response = httpClient.post(url, body, "Content-Type: application/json");
        } catch (InterruptedException | PoolException | IOException | HttpException e) {
            throw new TransportClientException("Failed to execute request: " + url, e);
        }

        return new ClientResponse(response.getStatus(), response.getBody(), parseHeaders(response.getHeaders()));
    }

    private static String createEndpoint() {
        String env = System.getenv("TAMTAM_BOTAPI_ENDPOINT");
        if (env != null) {
            return env;
        }

        return System.getProperty("tamtam.botapi.endpoint", ENDPOINT);
    }

    private static Map<String, String> parseHeaders(String[] headers) {
        return Arrays.stream(headers)
                .map(DefaultTransportClient::parseHeader)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(h -> h[0], h -> h[1]));
    }

    @Nullable
    private static String[] parseHeader(String header) {
        if (header == null) {
            return null;
        }

        int separator = header.indexOf(':');
        if (separator == -1 || separator == header.length() - 1) {
            return null;
        }

        String key = header.substring(0, separator);
        String value = header.substring(separator + 1).trim();
        return new String[]{key, value};
    }
}
