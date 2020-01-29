package chat.tamtam.botapi.exceptions;

/**
 * @author alexandrchuprin
 */
public class NotFoundException extends APIException {
    public NotFoundException(String message) {
        super(404, message);
    }
}
