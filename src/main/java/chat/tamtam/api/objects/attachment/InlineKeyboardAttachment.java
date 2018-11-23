package chat.tamtam.api.objects.attachment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import chat.tamtam.api.objects.keyboard.Keyboard;

/**
 * @author alexandrchuprin
 */
public class InlineKeyboardAttachment extends Attachment {
    public InlineKeyboardAttachment(String callbackId, Keyboard keyboard) {
        super(new Payload(keyboard, callbackId));
    }

    static class Payload implements AttachmentPayload {
        @JsonUnwrapped
        @JsonProperty
        private Keyboard keyboard;
        @JsonProperty("callback_id")
        private final String callbackId;

        public Payload(Keyboard keyboard, String callbackId) {
            this.keyboard = keyboard;
            this.callbackId = callbackId;
        }
    }
}
