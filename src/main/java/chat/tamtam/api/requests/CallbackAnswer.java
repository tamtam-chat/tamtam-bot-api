package chat.tamtam.api.requests;

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.TamTamSerializable;
import chat.tamtam.api.objects.Recipient;

/**
 * @author alexandrchuprin
 */
public class CallbackAnswer implements TamTamSerializable {
    private static final String MESSAGE = "message";
    private static final String NOTIFICATION = "notification";
    protected static final String RECIPIENT = "recipient";

    private final Recipient recipient;
    private final String notification;
    private final NewMessage.MessageBody message;

    @JsonCreator
    public CallbackAnswer(@JsonProperty(RECIPIENT) Recipient recipient, @JsonProperty(NOTIFICATION) String notification,
                          @JsonProperty(MESSAGE) NewMessage.MessageBody message) {
        this.recipient = recipient;
        this.notification = notification;
        this.message = message;
    }

    @Nullable
    @JsonProperty(NOTIFICATION)
    public String getNotification() {
        return notification;
    }

    @Nullable
    @JsonProperty(MESSAGE)
    public NewMessage.MessageBody getMessage() {
        return message;
    }

    @Nullable
    @JsonProperty(RECIPIENT)
    public Recipient getRecipient() {
        return recipient;
    }
}
