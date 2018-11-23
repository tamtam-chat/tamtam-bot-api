package chat.tamtam.api.requests.attachment;

import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class PhotoAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    public PhotoAttachmentRequest(String url) {
        this(new Payload());
        payload.sourceUrl = url;
    }

    @JsonCreator
    PhotoAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    @Nullable
    public String getPhotoToken() {
        if (payload.photos == null) {
            return null;
        }

        return payload.photos.values()
                .stream()
                .map(m -> m.get("token"))
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public String getSourceUrl() {
        return payload.sourceUrl;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    public static class Payload implements AttachmentRequestPayload {
        @JsonProperty("photos")
        @Nullable
        Map<String, Map<String, String>> photos;

        @Nullable
        @JsonProperty("url")
        String sourceUrl;
    }
}
