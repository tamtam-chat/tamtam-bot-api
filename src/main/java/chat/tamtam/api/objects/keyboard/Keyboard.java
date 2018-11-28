package chat.tamtam.api.objects.keyboard;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class Keyboard implements Serializable {
    private final Button[][] buttons;

    @JsonCreator
    public Keyboard(@JsonProperty("buttons") Button[][] buttons) {
//        if (buttons == null || buttons.length == 0) {
//            throw new IllegalArgumentException("Buttons array is empty.");
//        }

        this.buttons = buttons;
    }

    public Button[][] getButtons() {
        return buttons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Keyboard)) return false;

        Keyboard that = (Keyboard) o;

        return Arrays.deepEquals(buttons, that.buttons);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(buttons);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Keyboard{");
        sb.append("buttons=").append(Arrays.toString(buttons));
        sb.append('}');
        return sb.toString();
    }

}
