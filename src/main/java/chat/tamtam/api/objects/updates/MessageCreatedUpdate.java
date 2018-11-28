package chat.tamtam.api.objects.updates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import chat.tamtam.api.objects.Message;

/**
 * @author alexandrchuprin
 */
public class MessageCreatedUpdate extends Update {
    private final Message message;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public MessageCreatedUpdate(Message message) {
        this.message = message;
    }

    @JsonProperty
    @JsonUnwrapped
    public Message getMessage() {
        return message;
    }
}
