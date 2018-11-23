package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class StickerAttachment extends Attachment {
    private final String url;

    public StickerAttachment(String url) {
        super(new Payload(url));
        this.url = url;
    }

    @JsonCreator
    protected StickerAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.url = payload.url;
    }

    public String getURL() {
        return url;
    }

    private static class Payload implements AttachmentPayload {
        @JsonProperty(URL)
        private final String url;

        @JsonCreator
        private Payload(@JsonProperty(URL) String url) {
            this.url = url;
        }
    }
}
