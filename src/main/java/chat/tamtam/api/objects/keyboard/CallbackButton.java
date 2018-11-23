package chat.tamtam.api.objects.keyboard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class CallbackButton extends Button {
    protected static final String PAYLOAD = "payload";
    private final String payload;

    public CallbackButton(String text, String payload) {
        this(text, Intent.DEFAULT, payload);
    }

    public CallbackButton(String text, Intent intent, String payload) {
        super(Type.CALLBACK, text, intent);
        this.payload = payload;
    }

    @JsonProperty(PAYLOAD)
    public String getPayload() {
        return payload;
    }

    @JsonCreator
    protected static CallbackButton create(@JsonProperty(TEXT) String text,
                                           @JsonProperty(value = INTENT) Intent intent,
                                           @JsonProperty(PAYLOAD) String payload) {
        return new CallbackButton(text, intent == null ? Intent.DEFAULT : intent, payload);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CallbackButton)) return false;

        CallbackButton that = (CallbackButton) o;

        return payload != null ? payload.equals(that.payload) : that.payload == null;
    }

    @Override
    public int hashCode() {
        return payload != null ? payload.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CallbackButton{");
        sb.append("payload='").append(payload).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
