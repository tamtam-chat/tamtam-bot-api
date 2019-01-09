/*
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

package chat.tamtam.botapi.model;

import java.util.Objects;
import java.util.Arrays;
import chat.tamtam.botapi.model.Callback;
import chat.tamtam.botapi.model.Update;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * You will get this &#x60;update&#x60; as soon as user presses button
 */
public class MessageCallbackUpdate extends Update implements TamTamSerializable {
    @JsonProperty("callback")
    private final Callback callback;

    @JsonCreator
    public MessageCallbackUpdate(@JsonProperty("callback") Callback callback, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.callback = callback;
    }

    /**
    * @return callback
    **/
    public Callback getCallback() {
        return callback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        MessageCallbackUpdate other = (MessageCallbackUpdate) o;
        return Objects.equals(this.callback, other.callback) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callback, super.hashCode());
    }

    @Override
    public String toString() {
        return "MessageCallbackUpdate{"+ super.toString()
            + " callback='" + callback + '\''
            + '}';
    }
}

