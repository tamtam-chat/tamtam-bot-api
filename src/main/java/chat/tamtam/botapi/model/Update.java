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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Objects;


/**
 * &#x60;Update&#x60; object repsesents different types of events that happened in chat. See its inheritors
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "update_type", visible = true, defaultImpl = Update.class)
@JsonSubTypes({
  @JsonSubTypes.Type(value = MessageCreatedUpdate.class, name = "message_created"),
  @JsonSubTypes.Type(value = MessageCallbackUpdate.class, name = "message_callback"),
  @JsonSubTypes.Type(value = MessageEditedUpdate.class, name = "message_edited"),
  @JsonSubTypes.Type(value = MessageRemovedUpdate.class, name = "message_removed"),
  @JsonSubTypes.Type(value = MessageRestoredUpdate.class, name = "message_restored"),
  @JsonSubTypes.Type(value = BotAddedToChatUpdate.class, name = "bot_added"),
  @JsonSubTypes.Type(value = BotRemovedFromChatUpdate.class, name = "bot_removed"),
  @JsonSubTypes.Type(value = UserAddedToChatUpdate.class, name = "user_added"),
  @JsonSubTypes.Type(value = UserRemovedFromChatUpdate.class, name = "user_removed"),
  @JsonSubTypes.Type(value = BotStartedUpdate.class, name = "bot_started"),
  @JsonSubTypes.Type(value = ChatTitleChangedUpdate.class, name = "chat_title_changed"),
})
public class Update implements TamTamSerializable {

    private final Long timestamp;

    @JsonCreator
    public Update(@JsonProperty("timestamp") Long timestamp) { 
        this.timestamp = timestamp;
    }

    public void visit(Visitor visitor) {
        visitor.visitDefault(this);
    }

    /**
    * Unix-time when event has occured
    * @return timestamp
    **/
    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        Update other = (Update) o;
        return Objects.equals(this.timestamp, other.timestamp);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Update{"
            + " timestamp='" + timestamp + '\''
            + '}';
    }

    public interface Visitor {
        void visit(MessageCreatedUpdate model);
        void visit(MessageCallbackUpdate model);
        void visit(MessageEditedUpdate model);
        void visit(MessageRemovedUpdate model);
        void visit(MessageRestoredUpdate model);
        void visit(BotAddedToChatUpdate model);
        void visit(BotRemovedFromChatUpdate model);
        void visit(UserAddedToChatUpdate model);
        void visit(UserRemovedFromChatUpdate model);
        void visit(BotStartedUpdate model);
        void visit(ChatTitleChangedUpdate model);
        void visitDefault(Update model);
    }
}

