package chat.tamtam.api.requests.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class VideoAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    public VideoAttachmentRequest(long videoId) {
        this(new Payload());
        payload.videoId = videoId;
    }

    @JsonCreator
    VideoAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    public long getVideoId() {
        return payload.videoId;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    static class Payload implements AttachmentRequestPayload {
        @JsonProperty("id")
        long videoId;
    }
}
