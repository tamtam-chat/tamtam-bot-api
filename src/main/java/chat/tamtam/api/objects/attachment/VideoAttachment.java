package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class VideoAttachment extends Attachment {
    private final String url;

    public VideoAttachment(String url) {
        super(new Payload(url));
        this.url = url;
    }

    @JsonCreator
    public VideoAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.url = payload.url;
    }

    public String getUrl() {
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
