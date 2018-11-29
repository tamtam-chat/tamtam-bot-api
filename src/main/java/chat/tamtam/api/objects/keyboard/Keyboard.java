package chat.tamtam.api.objects.keyboard;

/*-
 * ------------------------------------------------------------------------
 * TamTam chat Bot API
 * ------------------------------------------------------------------------
 * Copyright (C) 2018 Mail.Ru Group
 * ------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ------------------------------------------------------------------------
 */

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
