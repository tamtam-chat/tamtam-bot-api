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
import chat.tamtam.botapi.model.ContactAttachmentPayload;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * ContactAttachment
 */
public class ContactAttachment extends Attachment implements TamTamSerializable {
  
    private final ContactAttachmentPayload payload;

    @JsonCreator
    public ContactAttachment(@JsonProperty("payload") ContactAttachmentPayload payload) { 
        super();
        this.payload = payload;
    }

    /**
    * @return payload
    **/
    @JsonProperty("payload")
    public ContactAttachmentPayload getPayload() {
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

        ContactAttachment other = (ContactAttachment) o;
        return Objects.equals(this.payload, other.payload) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload, super.hashCode());
    }

    @Override
    public String toString() {
        return "ContactAttachment{"+ super.toString()
            + " payload='" + payload + '\''
            + '}';
    }
}

