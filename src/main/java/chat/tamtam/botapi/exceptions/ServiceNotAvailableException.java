package chat.tamtam.botapi.exceptions;

/**
 * @author alexandrchuprin
 */
public class ServiceNotAvailableException extends ClientException {
    public ServiceNotAvailableException(String message) {
        super(503, "Service not available. Please try later. " + message);
    }
}
