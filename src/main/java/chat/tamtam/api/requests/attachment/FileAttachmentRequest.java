package chat.tamtam.api.requests.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class FileAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    public FileAttachmentRequest(long fileId) {
        this(new Payload(fileId));
    }

    @JsonCreator
    FileAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    public long getFileId() {
        return payload.fileId;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    public static class Payload implements AttachmentRequestPayload {
        protected static final String FILE_ID = "fileId";

        @JsonProperty(FILE_ID)
        private final long fileId;

        @JsonCreator
        public Payload(@JsonProperty(FILE_ID) long fileId) {
            this.fileId = fileId;
        }
    }
}
