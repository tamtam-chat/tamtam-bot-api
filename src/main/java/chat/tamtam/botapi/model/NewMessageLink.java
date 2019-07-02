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
import javax.validation.constraints.NotNull;


/**
 * NewMessageLink
 */
public class NewMessageLink implements TamTamSerializable {

    @NotNull
    private final MessageLinkType type;
    @NotNull
    private final String mid;

    @JsonCreator
    public NewMessageLink(@JsonProperty("type") MessageLinkType type, @JsonProperty("mid") String mid) { 
        this.type = type;
        this.mid = mid;
    }

    /**
    * Type of message link
    * @return type
    **/
    @JsonProperty("type")
    public MessageLinkType getType() {
        return type;
    }

    /**
    * Message identifier of original message
    * @return mid
    **/
    @JsonProperty("mid")
    public String getMid() {
        return mid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        NewMessageLink other = (NewMessageLink) o;
        return Objects.equals(this.type, other.type) &&
            Objects.equals(this.mid, other.mid);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (mid != null ? mid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewMessageLink{"
            + " type='" + type + '\''
            + " mid='" + mid + '\''
            + '}';
    }
}
