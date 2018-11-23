package chat.tamtam.api.objects;

import java.io.Serializable;

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.TamTamSerializable;

/**
 * @author alexandrchuprin
 */
public class Recipient implements TamTamSerializable {
    private static final String CHAT_ID = "chat_id";
    private static final String USER_ID = "user_id";

    private final Long chatId;
    private final Long userId;

    @JsonCreator
    protected Recipient(@JsonProperty(CHAT_ID) Long chatId, @JsonProperty(USER_ID) Long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    public static Recipient chat(long chatId) {
        return new Recipient(chatId, null);
    }

    public static Recipient user(long userId) {
        return new Recipient(null, userId);
    }

    @Nullable
    @JsonProperty(CHAT_ID)
    public Long getChatId() {
        return chatId;
    }

    @Nullable
    @JsonProperty(USER_ID)
    public Long getUserId() {
        return userId;
    }
}
