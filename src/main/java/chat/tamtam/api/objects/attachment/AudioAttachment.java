package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class AudioAttachment extends Attachment {
    private final Payload payload;

    public AudioAttachment(String url) {
        this.payload = new Payload(url);
    }

    @JsonCreator
    public AudioAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        this.payload = payload;
    }

    @Override
    protected TTAttachmentPayload getPayload() {
        return payload;
    }

    private static class Payload implements TTAttachmentPayload {
        @JsonProperty(URL)
        private final String url;

        @JsonCreator
        Payload(@JsonProperty(URL) String url) {
            this.url = url;
        }
    }
}
