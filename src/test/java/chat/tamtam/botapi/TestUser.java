package chat.tamtam.botapi;

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.botapi.model.User;

/**
 * @author alexandrchuprin
 */
public class TestUser extends User {
    @JsonCreator
    public TestUser(@JsonProperty("user_id") Long userId, @JsonProperty("name") String name,
                    @Nullable @JsonProperty("username") String username, @JsonProperty("is_bot") Boolean isBot,
                    @JsonProperty("last_activity_time") Long lastActivityTime) {
        super(userId, name, username, isBot, lastActivityTime);
    }
}
