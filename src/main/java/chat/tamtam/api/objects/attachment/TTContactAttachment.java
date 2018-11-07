package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.objects.TTUser;

/**
 * @author alexandrchuprin
 */
public class TTContactAttachment extends TTAttachment {
    private final Payload payload;

    @JsonCreator
    public TTContactAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        this.payload = payload;
    }

    public TTContactAttachment(String vcfInfo, TTUser ttUser) {
        payload = new Payload(vcfInfo, ttUser);
    }

    @Override
    protected TTAttachmentPayload getPayload() {
        return payload;
    }

    private class Payload implements TTAttachmentPayload {
        @JsonProperty("url")
        private final String vcfInfo;
        @JsonProperty("tamInfo")
        private final TTUser ttUser;

        private Payload(String vcfInfo, TTUser ttUser) {
            this.vcfInfo = vcfInfo;
            this.ttUser = ttUser;
        }
    }
}
