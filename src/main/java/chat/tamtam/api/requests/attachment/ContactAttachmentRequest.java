package chat.tamtam.api.requests.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class ContactAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    @JsonCreator
    ContactAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    public String getName() {
        return payload.name;
    }

    public Long getContactId() {
        return payload.contactId;
    }

    public String getVcfInfo() {
        return payload.vcfInfo;
    }

    public String getVcfPhone() {
        return payload.vcfPhone;
    }

    static class Payload implements AttachmentRequestPayload {
        @JsonProperty
        private String name;
        @JsonProperty
        private Long contactId;
        @JsonProperty
        private String vcfInfo;
        @JsonProperty
        private String vcfPhone;
    }
}
