package chat.tamtam.botapi.exceptions;

/**
 * @author alexandrchuprin
 */
public class BadRequestException extends APIException {
    BadRequestException(String message) {
        super(message);
    }
}
