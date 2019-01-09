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
import chat.tamtam.botapi.model.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * Paginated list of messages
 */
public class MessageList implements TamTamSerializable {
    @JsonProperty("messages")
    private final List<Message> messages;

    @JsonCreator
    public MessageList(@JsonProperty("messages") List<Message> messages) { 
        this.messages = messages;
    }

    /**
    * List of messages
    * @return messages
    **/
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        MessageList other = (MessageList) o;
        return Objects.equals(this.messages, other.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messages);
    }

    @Override
    public String toString() {
        return "MessageList{"
            + " messages='" + messages + '\''
            + '}';
    }
}

