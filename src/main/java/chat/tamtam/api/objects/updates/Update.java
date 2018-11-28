package chat.tamtam.api.objects.updates;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import chat.tamtam.api.TamTamSerializable;

/**
 * @author alexandrchuprin
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "update_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageCreatedUpdate.class, name = "message_created"),
        @JsonSubTypes.Type(value = MessageCallbackUpdate.class, name = "message_callback"),
})
public abstract class Update implements TamTamSerializable {
}
