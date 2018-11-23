package chat.tamtam.api.requests.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class AudioAttachmentRequest extends AttachmentRequest {
    private final long audioId;

    @JsonCreator
    AudioAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.audioId = payload.audioId;
    }

    public long getAudioId() {
        return audioId;
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
