package chat.tamtam.api.requests.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.TamTamSerializable;

/**
 * @author alexandrchuprin
 */
public class SimpleQueryResult implements TamTamSerializable {
    protected static final String SUCCESS = "success";

    private final boolean isSuccessful;

    @JsonCreator
    public SimpleQueryResult(@JsonProperty(SUCCESS) boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    @JsonProperty(SUCCESS)
    public boolean isSuccessful() {
        return isSuccessful;
    }
}
