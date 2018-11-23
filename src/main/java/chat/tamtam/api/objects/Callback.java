package chat.tamtam.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.TamTamSerializable;
import chat.tamtam.api.objects.User;

/**
 * @author alexandrchuprin
 */
public class Callback implements TamTamSerializable {
    private static final String ID = "id";
    private static final String PAYLOAD = "payload";
    private static final String USER = "user";

    private final User user;
    private final String id;
    private final String payload;

    @JsonCreator
    public Callback(@JsonProperty(USER) User user, @JsonProperty(ID) String id,
                    @JsonProperty(PAYLOAD) String payload) {
        this.user = user;
        this.id = id;
        this.payload = payload;
    }

    @JsonProperty(USER)
    public User getUser() {
        return user;
    }

    @JsonProperty(ID)
    public String getId() {
        return id;
    }

    @JsonProperty(PAYLOAD)
    public String getPayload() {
        return payload;
    }
}
