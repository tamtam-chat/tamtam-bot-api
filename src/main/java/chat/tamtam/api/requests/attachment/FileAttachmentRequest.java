package chat.tamtam.api.requests.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class FileAttachmentRequest extends AttachmentRequest {
    private final long fileId;

    @JsonCreator
    public FileAttachmentRequest(@JsonProperty(PAYLOAD) FileAttachRequestPayload payload) {
        super(payload);
        this.fileId = payload.fileId;
    }

    public long getFileId() {
        return fileId;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    public static class FileAttachRequestPayload implements AttachmentRequestPayload {
        long fileId;
    }
}
