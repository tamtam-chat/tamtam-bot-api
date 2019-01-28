/*
* ---------------------------------------------------------------------
* TamTam chat Bot API
* ---------------------------------------------------------------------
* Copyright (C) 2018 Mail.Ru Group
* ---------------------------------------------------------------------
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
* ---------------------------------------------------------------------
*/

package chat.tamtam.botapi.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

/**
 * InlineKeyboardAttachmentPayload
 */
public class InlineKeyboardAttachmentPayload implements TamTamSerializable {
    @JsonProperty("callback_id")
    private final String callbackId;

    @JsonProperty("buttons")
    private final List<List<Button>> buttons;

    @JsonCreator
    public InlineKeyboardAttachmentPayload(@JsonProperty("callback_id") String callbackId, @JsonProperty("buttons") List<List<Button>> buttons) { 
        this.callbackId = callbackId;
        this.buttons = buttons;
    }

    /**
    * @return callbackId
    **/
    public String getCallbackId() {
        return callbackId;
    }

    /**
    * @return buttons
    **/
    public List<List<Button>> getButtons() {
        return buttons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        InlineKeyboardAttachmentPayload other = (InlineKeyboardAttachmentPayload) o;
        return Objects.equals(this.callbackId, other.callbackId) &&
            Objects.equals(this.buttons, other.buttons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callbackId, buttons);
    }

    @Override
    public String toString() {
        return "InlineKeyboardAttachmentPayload{"
            + " callbackId='" + callbackId + '\''
            + " buttons='" + buttons + '\''
            + '}';
    }
}

