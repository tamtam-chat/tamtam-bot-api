package chat.tamtam.api.objects.keyboard;

/**
 * @author alexandrchuprin
 */
public class RequestContactButton extends Button {
    public RequestContactButton() {
        super(Type.REQUEST_CONTACT, "request.contact", Intent.DEFAULT);
    }
}
