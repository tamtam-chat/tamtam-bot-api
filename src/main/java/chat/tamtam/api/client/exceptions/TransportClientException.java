package chat.tamtam.api.client.exceptions;

/**
 * @author alexandrchuprin
 */
public class TransportClientException extends Exception {
    public TransportClientException(String message) {
        super(message);
    }

    public TransportClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
