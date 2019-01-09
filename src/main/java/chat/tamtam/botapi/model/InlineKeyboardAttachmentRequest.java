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
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequestPayload;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * Request to attach keyboard to message
 */
public class InlineKeyboardAttachmentRequest extends AttachmentRequest implements TamTamSerializable {
    @JsonProperty("payload")
    private final InlineKeyboardAttachmentRequestPayload payload;

    @JsonCreator
    public InlineKeyboardAttachmentRequest(@JsonProperty("payload") InlineKeyboardAttachmentRequestPayload payload) { 
        super();
        this.payload = payload;
    }

    /**
    * @return payload
    **/
    public InlineKeyboardAttachmentRequestPayload getPayload() {
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

        InlineKeyboardAttachmentRequest other = (InlineKeyboardAttachmentRequest) o;
        return Objects.equals(this.payload, other.payload) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload, super.hashCode());
    }

    @Override
    public String toString() {
        return "InlineKeyboardAttachmentRequest{"+ super.toString()
            + " payload='" + payload + '\''
            + '}';
    }
}

