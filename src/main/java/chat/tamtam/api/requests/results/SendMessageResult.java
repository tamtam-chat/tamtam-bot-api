package chat.tamtam.api.requests.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class SendMessageResult {
    private final long chatId;
    private final String messageId;

    @JsonCreator
    public SendMessageResult(@JsonProperty("chat_id") long chatId, @JsonProperty("message_id") String messageId) {
        this.chatId = chatId;
        this.messageId = messageId;
    }

    public long getChatId() {
        return chatId;
    }

    public String getMessageId() {
        return messageId;
    }
}
