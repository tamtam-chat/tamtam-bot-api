package chat.tamtam.api.objects;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class Recepient implements Serializable {
    private static final String CHAT_ID = "chat_id";

    private final long chatId;

    public Recepient(@JsonProperty(CHAT_ID) long chatId) {
        this.chatId = chatId;
    }

    @JsonProperty(CHAT_ID)
    public long getChatId() {
        return chatId;
    }
}
