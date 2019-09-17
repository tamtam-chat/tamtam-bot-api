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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jetbrains.annotations.Nullable;

/**
 * You will get this &#x60;update&#x60; as soon as user presses button
 */
public class MessageCallbackUpdate extends Update implements TamTamSerializable {

    @NotNull
    private final @Valid Callback callback;
    @Nullable
    private final @Valid Message message;

    @JsonCreator
    public MessageCallbackUpdate(@JsonProperty("callback") Callback callback, @Nullable @JsonProperty("message") Message message, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.callback = callback;
        this.message = message;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    /**
    * @return callback
    **/
    @JsonProperty("callback")
    public Callback getCallback() {
        return callback;
    }

    /**
    * Original message containing inline keyboard. Can be &#x60;null&#x60; in case it had been deleted by the moment a bot got this update
    * @return message
    **/
    @Nullable
    @JsonProperty("message")
    public Message getMessage() {
        return message;
    }

    @JsonProperty("update_type")
    @Override
    public String getType() {
        return Update.MESSAGE_CALLBACK;
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
            Objects.equals(this.message, other.message) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (callback != null ? callback.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageCallbackUpdate{"+ super.toString()
            + " callback='" + callback + '\''
            + " message='" + message + '\''
            + '}';
    }
}
