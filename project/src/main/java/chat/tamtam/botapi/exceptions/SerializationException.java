package chat.tamtam.botapi.exceptions;

/**
 * @author alexandrchuprin
 */
public class SerializationException extends ClientException {
    public SerializationException(Exception e) {
        super(e);
    }
}
