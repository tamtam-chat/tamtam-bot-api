package chat.tamtam.api.objects.keyboard;

/**
 * @author alexandrchuprin
 */
public class RequestGeoLocationButton extends Button {
    public RequestGeoLocationButton() {
        super(Type.REQUEST_GEO_LOCATION, "request.geolocation", Intent.DEFAULT);
    }
}
