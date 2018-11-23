package chat.tamtam.api.requests.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class VideoAttachmentRequest extends AttachmentRequest {
    private final long videoId;

    @JsonCreator
    public VideoAttachmentRequest(@JsonProperty(PAYLOAD) VideoAttachRequestPayload payload) {
        super(payload);
        this.videoId = payload.videoId;
    }

    public long getVideoId() {
        return videoId;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    static class VideoAttachRequestPayload implements AttachmentRequestPayload {
        @JsonProperty("id")
        long videoId;
    }
}
