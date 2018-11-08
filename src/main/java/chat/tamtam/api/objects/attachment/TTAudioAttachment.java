package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class TTAudioAttachment extends TTAttachment {
    private final Payload payload;

    public TTAudioAttachment(String url) {
        this.payload = new Payload(url);
    }

    @JsonCreator
    public TTAudioAttachment(@JsonProperty(PAYLOAD) Payload payload) {
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
