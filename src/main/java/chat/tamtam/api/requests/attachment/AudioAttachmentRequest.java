package chat.tamtam.api.requests.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class AudioAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    public AudioAttachmentRequest(long audioId) {
        this(new Payload());
        payload.audioId = audioId;
    }

    @JsonCreator
    AudioAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    public long getAudioId() {
        return payload.audioId;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    static class Payload implements AttachmentRequestPayload {
        @JsonProperty("id")
        private long audioId;
    }
}
