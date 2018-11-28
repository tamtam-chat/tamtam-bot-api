package chat.tamtam.api.client.exceptions;

/**
 * @author alexandrchuprin
 */
public class TooManyRequestsException extends APIException {
    public TooManyRequestsException(String message) {
        super(message);
    }
}
