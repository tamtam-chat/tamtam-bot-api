package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class TTStickerAttachment extends TTAttachment {
    private final Payload payload;

    public TTStickerAttachment(String url) {
        this.payload = new Payload(url);
    }

    @JsonCreator
    protected TTStickerAttachment(@JsonProperty(PAYLOAD) Payload payload) {
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
