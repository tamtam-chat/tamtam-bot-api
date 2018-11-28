package chat.tamtam.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.TamTamSerializable;

/**
 * @author alexandrchuprin
 */
public class Subscription implements TamTamSerializable {
    private final String url;
    private final long time;

    @JsonCreator
    public Subscription(@JsonProperty("url") String url, @JsonProperty("time") long time) {
        this.url = url;
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public long getTime() {
        return time;
    }
}
