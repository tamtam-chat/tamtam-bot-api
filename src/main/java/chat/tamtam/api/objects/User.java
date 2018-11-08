package chat.tamtam.api.objects;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class User implements Serializable {
    private static final String NAME = "name";
    private static final String USER_ID = "user_id";

    private final long userId;
    private final String name;

    @JsonCreator
    public User(@JsonProperty(USER_ID) long userId, @JsonProperty(NAME) String name) {
        this.userId = userId;
        this.name = name;
    }

    @JsonProperty(USER_ID)
    public long getUserId() {
        return userId;
    }

    @JsonProperty(NAME)
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
