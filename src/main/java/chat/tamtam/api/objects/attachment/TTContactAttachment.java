package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.objects.TTUser;

/**
 * @author alexandrchuprin
 */
public class TTContactAttachment extends TTAttachment {
    private static final String VCF_INFO = "vcfInfo";
    private static final String TAM_INFO = "tamInfo";

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

    private static class Payload implements TTAttachmentPayload {
        @JsonProperty(VCF_INFO)
        private final String vcfInfo;
        @JsonProperty(TAM_INFO)
        private final TTUser ttUser;

        @JsonCreator
        private Payload(@JsonProperty(VCF_INFO) String vcfInfo, @JsonProperty(TAM_INFO) TTUser ttUser) {
            this.vcfInfo = vcfInfo;
            this.ttUser = ttUser;
        }
    }
}
