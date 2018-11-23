package chat.tamtam.api.requests.attachment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.objects.keyboard.Button;
import chat.tamtam.api.objects.keyboard.Keyboard;

/**
 * @author alexandrchuprin
 */
public class InlineKeyboardAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    public InlineKeyboardAttachmentRequest(Button[][] buttons) {
        this(new Payload(buttons));
    }

    @JsonCreator
    InlineKeyboardAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    public Keyboard getKeyboard() {
        return payload.keyboard;
    }

    private static class Payload implements AttachmentRequestPayload {
        @JsonProperty
        private final Keyboard keyboard;

        @JsonCreator
        private Payload(@JsonProperty("buttons") Button[][] buttons) {
            this.keyboard = new Keyboard(buttons);
        }
    }
}
