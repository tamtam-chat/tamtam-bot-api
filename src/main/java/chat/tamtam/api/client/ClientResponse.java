package chat.tamtam.api.client;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ClientResponse {
    private final int statusCode;
    private final byte[] body;
    private final Map<String, String> headers;

    public ClientResponse(int statusCode, byte[] body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public byte[] getBody() {
        return body;
    }

    public String getBodyAsString() {
        return new String(body, StandardCharsets.UTF_8);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
