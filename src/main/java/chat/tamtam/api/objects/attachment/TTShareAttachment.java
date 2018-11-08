package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class TTShareAttachment extends TTAttachment {
    private final Payload payload;

    public TTShareAttachment(String url) {
        this.payload = new Payload(url);
    }

    @JsonCreator
    public TTShareAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        this.payload = payload;
    }

    public String getUrl() {
        return payload.url;
    }

    @Override
    protected TTAttachmentPayload getPayload() {
        return payload;
    }

    private static class Payload implements TTAttachmentPayload {
        @JsonProperty(URL)
        private final String url;

        @JsonCreator
        private Payload(@JsonProperty(URL) String url) {
            this.url = url;
        }
    }
}
