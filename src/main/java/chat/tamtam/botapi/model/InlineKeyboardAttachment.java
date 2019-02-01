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


/**
 * Buttons in messages
 */
public class InlineKeyboardAttachment extends Attachment implements TamTamSerializable {

    private final String callbackId;
    private final Keyboard payload;

    @JsonCreator
    public InlineKeyboardAttachment(@JsonProperty("callback_id") String callbackId, @JsonProperty("payload") Keyboard payload) { 
        super();
        this.callbackId = callbackId;
        this.payload = payload;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    /**
    * Unique identifier of keyboard
    * @return callbackId
    **/
    @JsonProperty("callback_id")
    public String getCallbackId() {
        return callbackId;
    }

    /**
    * @return payload
    **/
    @JsonProperty("payload")
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
            Objects.equals(this.payload, other.payload);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (callbackId != null ? callbackId.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InlineKeyboardAttachment{"+ super.toString()
            + " callbackId='" + callbackId + '\''
            + " payload='" + payload + '\''
            + '}';
    }
}

