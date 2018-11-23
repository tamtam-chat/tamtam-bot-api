package chat.tamtam.api.requests.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.objects.keyboard.Button;
import chat.tamtam.api.objects.keyboard.Keyboard;

/**
 * @author alexandrchuprin
 */
public class InlineKeyboardAttachmentRequest extends AttachmentRequest {
    private final Keyboard keyboard;

    @JsonCreator
    protected InlineKeyboardAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        keyboard = payload.keyboard;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    private static class Payload implements AttachmentRequestPayload {
        private final Keyboard keyboard;

        @JsonCreator
        private Payload(@JsonProperty("buttons") Button[][] buttons) {
            this.keyboard = new Keyboard(buttons);
        }
    }
}
