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
import chat.tamtam.botapi.model.Attachment;
import chat.tamtam.botapi.model.Keyboard;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * Buttons in messages
 */
public class InlineKeyboardAttachment extends Attachment implements TamTamSerializable {
    @JsonProperty("callback_id")
    private final String callbackId;

    @JsonProperty("payload")
    private final Keyboard payload;

    @JsonCreator
    public InlineKeyboardAttachment(@JsonProperty("callback_id") String callbackId, @JsonProperty("payload") Keyboard payload) { 
        super();
        this.callbackId = callbackId;
        this.payload = payload;
    }

    /**
    * Unique identifier of keyboard
    * @return callbackId
    **/
    public String getCallbackId() {
        return callbackId;
    }

    /**
    * @return payload
    **/
    public Keyboard getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        InlineKeyboardAttachment other = (InlineKeyboardAttachment) o;
        return Objects.equals(this.callbackId, other.callbackId) &&
            Objects.equals(this.payload, other.payload) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callbackId, payload, super.hashCode());
    }

    @Override
    public String toString() {
        return "InlineKeyboardAttachment{"+ super.toString()
            + " callbackId='" + callbackId + '\''
            + " payload='" + payload + '\''
            + '}';
    }
}

