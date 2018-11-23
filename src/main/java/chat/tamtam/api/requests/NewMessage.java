package chat.tamtam.api.requests;

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.TamTamSerializable;
import chat.tamtam.api.objects.Callback;
import chat.tamtam.api.objects.Recipient;
import chat.tamtam.api.requests.attachment.AttachmentRequest;

/**
 * @author alexandrchuprin
 */
public class NewMessage implements TamTamSerializable {
    protected static final String RECIPIENT = "recipient";
    protected static final String MESSAGE = "message";
    protected static final String SENDER_ACTION = "sender_action";
    protected static final String CHAT_CONTROL = "chat_control";
    protected static final String CALLBACK = "callback";

    private final Recipient recipient;
    private final MessageBody messageBody;
    private final SenderAction senderAction;
    private final ChatControl chatControl;
    private final Callback callback;

    @JsonCreator
    protected NewMessage(@JsonProperty(RECIPIENT) Recipient recipient,
                         @JsonProperty(MESSAGE) @Nullable MessageBody messageBody,
                         @JsonProperty(SENDER_ACTION) @Nullable SenderAction senderAction,
                         @JsonProperty(CHAT_CONTROL) ChatControl chatControl,
                         @JsonProperty(CALLBACK) @Nullable Callback callback) {
        this.recipient = recipient;
        this.messageBody = messageBody;
        this.senderAction = senderAction;
        this.chatControl = chatControl;
        this.callback = callback;
    }

    public static NewMessage message(Recipient recipient, MessageBody messageBody) {
        return new NewMessage(recipient, messageBody, null, null, null);
    }

    public static NewMessage senderAction(Recipient recipient, SenderAction action) {
        return new NewMessage(recipient, null, action, null, null);
    }

    public static NewMessage chatControl(Recipient recipient, ChatControl chatControl) {
        return new NewMessage(recipient, null, null, chatControl, null);
    }

    public static NewMessage callback(Recipient recipient, Callback callback) {
        return new NewMessage(recipient, null, null, null, callback);
    }

    @JsonProperty(RECIPIENT)
    public Recipient getRecipient() {
        return recipient;
    }

    @Nullable
    @JsonProperty(MESSAGE)
    public MessageBody getMessage() {
        return messageBody;
    }

    @Nullable
    @JsonProperty(SENDER_ACTION)
    public SenderAction getSenderAction() {
        return senderAction;
    }

    @Nullable
    @JsonProperty(CHAT_CONTROL)
    public ChatControl getChatControl() {
        return chatControl;
    }

    @Nullable
    @JsonProperty(CALLBACK)
    public Callback getCallback() {
        return callback;
    }

    public static class MessageBody implements TamTamSerializable {
        @JsonProperty
        public
        String text;
        @JsonProperty
        String metadata;
        @JsonProperty
        public
        Boolean notify = true;
        @JsonProperty
        private AttachmentRequest attachment;
        @JsonProperty
        private AttachmentRequest[] attachments;

        public AttachmentRequest[] getAttachments() {
            if (attachment != null) {
                return new AttachmentRequest[]{attachment};
            }

            return attachments;
        }
    }

}
