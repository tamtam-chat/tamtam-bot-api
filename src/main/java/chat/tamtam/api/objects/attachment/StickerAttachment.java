package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class StickerAttachment extends Attachment {
    private final Payload payload;

    public StickerAttachment(String url) {
        this.payload = new Payload(url);
    }

    @JsonCreator
    protected StickerAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        this.payload = payload;
    }

    public String getURL() {
        return payload.url;
    }

    @Override
    protected TTAttachmentPayload getPayload() {
        return payload;
    }

    private class Payload implements TTAttachmentPayload {
        @JsonProperty(URL)
        private final String url;

        @JsonCreator
        private Payload(@JsonProperty(URL) String url) {
            this.url = url;
        }
    }
}
