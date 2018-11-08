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
        @JsonSubTypes.Type(value = TTImageAttachment.class, name = "image"),
        @JsonSubTypes.Type(value = TTVideoAttachment.class, name = "video"),
        @JsonSubTypes.Type(value = TTAudioAttachment.class, name = "audio"),
        @JsonSubTypes.Type(value = TTContactAttachment.class, name = "contact"),
        @JsonSubTypes.Type(value = TTFileAttachment.class, name = "file"),
        @JsonSubTypes.Type(value = TTShareAttachment.class, name = "share"),
        @JsonSubTypes.Type(value = TTStickerAttachment.class, name = "sticker"),
})
public abstract class TTAttachment implements Serializable {
    protected static final String PAYLOAD = "payload";
    protected static final String URL = "url";

    @JsonProperty(PAYLOAD)
    protected abstract TTAttachmentPayload getPayload();

    protected interface TTAttachmentPayload extends Serializable {
    }
}
