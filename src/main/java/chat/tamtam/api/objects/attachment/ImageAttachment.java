package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class ImageAttachment extends Attachment {
    private static final String PHOTO_ID = "photo_id";

    private final Payload payload;

    public ImageAttachment(long photoId, String url) {
        this.payload = new Payload(photoId, url);
    }

    @JsonCreator
    protected ImageAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        this.payload = payload;
    }

    public String getURL() {
        return payload.url;
    }

    public long getPhotoId() {
        return payload.photoId;
    }

    @Override
    protected TTAttachmentPayload getPayload() {
        return payload;
    }

    private static class Payload implements TTAttachmentPayload {
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
