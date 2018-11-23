package chat.tamtam.api.requests.attachment;

import java.io.Serializable;

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PhotoAttachmentRequest.class, name = "image"),
        @JsonSubTypes.Type(value = FileAttachmentRequest.class, name = "file"),
        @JsonSubTypes.Type(value = VideoAttachmentRequest.class, name = "video"),
        @JsonSubTypes.Type(value = AudioAttachmentRequest.class, name = "audio"),
        @JsonSubTypes.Type(value = StickerAttachmentRequest.class, name = "sticker"),
        @JsonSubTypes.Type(value = ContactAttachmentRequest.class, name = "contact"),
        @JsonSubTypes.Type(value = InlineKeyboardAttachmentRequest.class, name = "inline_keyboard"),
})
public abstract class AttachmentRequest implements Serializable {
    static final String PAYLOAD = "payload";

    @JsonProperty(PAYLOAD)
    private final AttachmentRequestPayload payload;

    AttachmentRequest(AttachmentRequestPayload payload) {
        this.payload = payload;
    }

    public abstract <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E;

    public boolean hasPayload() {
        return payload != null;
    }

    public interface Mapper<T, E extends Throwable> {
        @Nullable
        T map(FileAttachmentRequest request) throws E;

        @Nullable
        T map(PhotoAttachmentRequest request) throws E;

        @Nullable
        T map(VideoAttachmentRequest request) throws E;

        @Nullable
        T map(AudioAttachmentRequest request) throws E;

        @Nullable
        T map(StickerAttachmentRequest request) throws E;

        @Nullable
        T map(ContactAttachmentRequest request) throws E;

        @Nullable
        T map(InlineKeyboardAttachmentRequest request) throws E;
    }

    public interface AttachmentRequestPayload extends Serializable {
    }
}
