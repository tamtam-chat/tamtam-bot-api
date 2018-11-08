package chat.tamtam.api.objects;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class TTUser implements Serializable {
    private static final String NAME = "name";
    private static final String USER_ID = "user_id";

    private final long userId;
    private final String name;

    @JsonCreator
    public TTUser(@JsonProperty(USER_ID) long userId, @JsonProperty(NAME) String name) {
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
        if (!(o instanceof TTUser)) return false;

        TTUser ttUser = (TTUser) o;

        if (userId != ttUser.userId) return false;
        return name != null ? name.equals(ttUser.name) : ttUser.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
