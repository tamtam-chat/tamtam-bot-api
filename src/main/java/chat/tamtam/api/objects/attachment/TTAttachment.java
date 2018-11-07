package chat.tamtam.api.objects.attachment;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author alexandrchuprin
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TTImageAttachment.class, name = "image"),
})
public abstract class TTAttachment implements Serializable {
    protected static final String PAYLOAD = "payload";

    @JsonProperty("payload")
    protected abstract TTAttachmentPayload getPayload();

    protected interface TTAttachmentPayload extends Serializable {
    }
}
