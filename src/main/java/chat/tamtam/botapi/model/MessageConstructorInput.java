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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;

import org.jetbrains.annotations.Nullable;

/**
 * Bot will get this input in case when user sends message to bot manually
 */
public class MessageConstructorInput extends ConstructorInput implements TamTamSerializable {

    @Nullable
    private final List<@Valid NewMessageBody> messages;

    @JsonCreator
    public MessageConstructorInput(@Nullable @JsonProperty("messages") List<NewMessageBody> messages) { 
        super();
        this.messages = messages;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T map(Mapper<T> mapper) {
        return mapper.map(this);
    }

    /**
    * Messages sent by user during construction process. Typically it is single element array but sometimes it can contains multiple messages. Can be empty on initial request when user just opened constructor
    * @return messages
    **/
    @Nullable
    @JsonProperty("messages")
    public List<NewMessageBody> getMessages() {
        return messages;
    }

    @JsonProperty("input_type")
    @Override
    public String getType() {
        return ConstructorInput.MESSAGE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        MessageConstructorInput other = (MessageConstructorInput) o;
        return Objects.equals(this.messages, other.messages);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (messages != null ? messages.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageConstructorInput{"+ super.toString()
            + " messages='" + messages + '\''
            + '}';
    }
}
