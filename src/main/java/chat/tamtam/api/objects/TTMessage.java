package chat.tamtam.api.objects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.objects.attachment.TTAttachment;

/**
 * @author alexandrchuprin
 */
public class TTMessage {
    private static final String SENDER = "sender";
    private static final String RECEPIENT = "recepient";
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";
    private static final String MID = "mid";
    private static final String PAYLOAD = "payload";

    private final TTUser sender;
    private final TTRecepient recepient;
    private final TTMessagePayload payload;
    private final long timestamp;

    public TTMessage(TTUser sender, TTRecepient recepient, long timestamp, String messageId, long seq, String text,
                     List<TTAttachment> attachments, String replyTo) {
        this(sender, recepient, new TTMessagePayload(messageId, seq, text, attachments, replyTo), timestamp);
    }

    @JsonCreator
    protected TTMessage(@JsonProperty(SENDER) TTUser sender, @JsonProperty(RECEPIENT) TTRecepient recepient,
                        @JsonProperty(PAYLOAD) TTMessagePayload payload, @JsonProperty(TIMESTAMP) long timestamp) {
        this.sender = sender;
        this.recepient = recepient;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    @JsonProperty(SENDER)
    public TTUser getSender() {
        return sender;
    }

    @JsonProperty(RECEPIENT)
    public TTRecepient getRecepient() {
        return recepient;
    }

    @JsonProperty(MESSAGE)
    protected TTMessagePayload getMessage() {
        return payload;
    }

    @JsonProperty(TIMESTAMP)
    public long getTimestamp() {
        return timestamp;
    }

    private static class TTMessagePayload {
        @JsonProperty(MID)
        private final String messageId;
        private final long seq;
        private final String text;
        private final List<TTAttachment> attachments;
        private final String replyTo;

        private TTMessagePayload(String messageId, long seq, String text,
                                 List<TTAttachment> attachments, String replyTo) {
            this.messageId = messageId;
            this.seq = seq;
            this.text = text;
            this.attachments = attachments;
            this.replyTo = replyTo;
        }
    }
}
