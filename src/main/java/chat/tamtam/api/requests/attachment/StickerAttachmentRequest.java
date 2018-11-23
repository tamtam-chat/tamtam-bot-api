package chat.tamtam.api.requests.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class StickerAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    public StickerAttachmentRequest(String stickerCode) {
        this(new Payload());
        payload.code = stickerCode;
    }

    @JsonCreator
    StickerAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    public String getCode() {
        return payload.code;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    static class Payload implements AttachmentRequestPayload {
        @JsonProperty
        private String code;
    }
}
