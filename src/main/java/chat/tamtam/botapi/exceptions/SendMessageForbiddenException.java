package chat.tamtam.botapi.exceptions;

/**
 * @author alexandrchuprin
 */
public class SendMessageForbiddenException extends APIException {
    public SendMessageForbiddenException(String message) {
        super(message);
    }
}
