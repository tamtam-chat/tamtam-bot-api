package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class ImageAttachment extends Attachment {
    private static final String PHOTO_ID = "photo_id";

    private final long photoId;
    private final String url;

    public ImageAttachment(long photoId, String url) {
        super(new Payload(photoId, url));
        this.photoId = photoId;
        this.url = url;
    }

    @JsonCreator
    protected ImageAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.photoId = payload.photoId;
        this.url = payload.url;
    }

    public String getURL() {
        return url;
    }

    public long getPhotoId() {
        return photoId;
    }

    private static class Payload implements AttachmentPayload {
        @JsonProperty(PHOTO_ID)
        private final long photoId;
        @JsonProperty(URL)
        private final String url;

        @JsonCreator
        Payload(@JsonProperty(PHOTO_ID) long photoId, @JsonProperty(URL) String url) {
            this.photoId = photoId;
            this.url = url;
        }
    }
}
