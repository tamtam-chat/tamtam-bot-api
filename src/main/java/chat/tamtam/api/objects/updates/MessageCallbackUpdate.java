package chat.tamtam.api.objects.updates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import chat.tamtam.api.objects.Callback;

/**
 * @author alexandrchuprin
 */
public class MessageCallbackUpdate extends Update {
    private final Callback callback;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public MessageCallbackUpdate(Callback callback) {
        this.callback = callback;
    }

    @JsonUnwrapped
    public Callback getCallback() {
        return callback;
    }
}
