package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class FileAttachment extends Attachment {
    private final String url;

    public FileAttachment(String url) {
        super(new Payload(url));
        this.url = url;
    }

    @JsonCreator
    public FileAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.url = payload.url;
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
