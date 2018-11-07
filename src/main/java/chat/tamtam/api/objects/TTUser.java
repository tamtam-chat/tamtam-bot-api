package chat.tamtam.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class TTUser {
    private final long userId;
    private final String name;

    @JsonCreator
    public TTUser(@JsonProperty("user_id") long userId, @JsonProperty("name") String name) {
        this.userId = userId;
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

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
