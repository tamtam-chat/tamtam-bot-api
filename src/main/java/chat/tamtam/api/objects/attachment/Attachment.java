package chat.tamtam.api.objects.attachment;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author alexandrchuprin
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImageAttachment.class, name = "image"),
        @JsonSubTypes.Type(value = VideoAttachment.class, name = "video"),
        @JsonSubTypes.Type(value = AudioAttachment.class, name = "audio"),
        @JsonSubTypes.Type(value = ContactAttachment.class, name = "contact"),
        @JsonSubTypes.Type(value = FileAttachment.class, name = "file"),
        @JsonSubTypes.Type(value = ShareAttachment.class, name = "share"),
        @JsonSubTypes.Type(value = StickerAttachment.class, name = "sticker"),
})
public abstract class Attachment implements Serializable {
    protected static final String PAYLOAD = "payload";
    protected static final String URL = "url";

    @JsonProperty(PAYLOAD)
    protected abstract TTAttachmentPayload getPayload();

    protected interface TTAttachmentPayload extends Serializable {
    }
}
