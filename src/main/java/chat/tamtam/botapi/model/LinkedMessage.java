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
 * LinkedMessage
 */
public class LinkedMessage implements TamTamSerializable {

    private final MessageLinkType type;
    private final Message message;

    @JsonCreator
    public LinkedMessage(@JsonProperty("type") MessageLinkType type, @JsonProperty("message") Message message) { 
        this.type = type;
        this.message = message;
    }

    /**
    * Type of linked message
    * @return type
    **/
    @JsonProperty("type")
    public MessageLinkType getType() {
        return type;
    }

    /**
    * @return message
    **/
    @JsonProperty("message")
    public Message getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        LinkedMessage other = (LinkedMessage) o;
        return Objects.equals(this.type, other.type) &&
            Objects.equals(this.message, other.message);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LinkedMessage{"
            + " type='" + type + '\''
            + " message='" + message + '\''
            + '}';
    }
}

