package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class AudioAttachment extends Attachment {
    private final String url;

    public AudioAttachment(String url) {
        super(new Payload(url));
        this.url = url;
    }

    @JsonCreator
    public AudioAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.url = payload.url;
    }

    private static class Payload implements AttachmentPayload {
        @JsonProperty(URL)
        private final String url;

        @JsonCreator
        Payload(@JsonProperty(URL) String url) {
            this.url = url;
        }
    }
}
