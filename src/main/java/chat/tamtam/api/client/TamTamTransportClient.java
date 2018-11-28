package chat.tamtam.api.client;

import org.jetbrains.annotations.Nullable;

import chat.tamtam.api.client.exceptions.TransportClientException;

/**
 * @author alexandrchuprin
 */
public interface TamTamTransportClient {
    String getEndpoint();

    ClientResponse get(String url) throws TransportClientException;

    ClientResponse post(String url, @Nullable byte[] body) throws TransportClientException;
}
