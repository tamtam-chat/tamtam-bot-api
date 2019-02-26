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
 * You will get this &#x60;update&#x60; as soon as message is removed
 */
public class MessageRemovedUpdate extends Update implements TamTamSerializable {

    private final String messageId;

    @JsonCreator
    public MessageRemovedUpdate(@JsonProperty("message_id") String messageId, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.messageId = messageId;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    /**
    * Identifier of removed message
    * @return messageId
    **/
    @JsonProperty("message_id")
    public String getMessageId() {
        return messageId;
    }

    public String getType() {
        return Update.MESSAGE_REMOVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        MessageRemovedUpdate other = (MessageRemovedUpdate) o;
        return Objects.equals(this.messageId, other.messageId) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageRemovedUpdate{"+ super.toString()
            + " messageId='" + messageId + '\''
            + '}';
    }
}
