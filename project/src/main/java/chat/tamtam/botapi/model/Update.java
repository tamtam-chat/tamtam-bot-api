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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * &#x60;Update&#x60; object repsesents different types of events that happened in chat. See its inheritors
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "update_type", visible = true, defaultImpl = Update.class, include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value = MessageCreatedUpdate.class, name = Update.MESSAGE_CREATED),
  @JsonSubTypes.Type(value = MessageCallbackUpdate.class, name = Update.MESSAGE_CALLBACK),
  @JsonSubTypes.Type(value = MessageEditedUpdate.class, name = Update.MESSAGE_EDITED),
  @JsonSubTypes.Type(value = MessageRemovedUpdate.class, name = Update.MESSAGE_REMOVED),
  @JsonSubTypes.Type(value = BotAddedToChatUpdate.class, name = Update.BOT_ADDED),
  @JsonSubTypes.Type(value = BotRemovedFromChatUpdate.class, name = Update.BOT_REMOVED),
  @JsonSubTypes.Type(value = UserAddedToChatUpdate.class, name = Update.USER_ADDED),
  @JsonSubTypes.Type(value = UserRemovedFromChatUpdate.class, name = Update.USER_REMOVED),
  @JsonSubTypes.Type(value = BotStartedUpdate.class, name = Update.BOT_STARTED),
  @JsonSubTypes.Type(value = ChatTitleChangedUpdate.class, name = Update.CHAT_TITLE_CHANGED),
})
public class Update implements TamTamSerializable {
    public static final String MESSAGE_CREATED = "message_created";
    public static final String MESSAGE_CALLBACK = "message_callback";
    public static final String MESSAGE_EDITED = "message_edited";
    public static final String MESSAGE_REMOVED = "message_removed";
    public static final String BOT_ADDED = "bot_added";
    public static final String BOT_REMOVED = "bot_removed";
    public static final String USER_ADDED = "user_added";
    public static final String USER_REMOVED = "user_removed";
    public static final String BOT_STARTED = "bot_started";
    public static final String CHAT_TITLE_CHANGED = "chat_title_changed";
    public static final Set<String> TYPES = new HashSet<>(Arrays.asList(
        MESSAGE_CREATED, 
        MESSAGE_CALLBACK, 
        MESSAGE_EDITED, 
        MESSAGE_REMOVED, 
        BOT_ADDED, 
        BOT_REMOVED, 
        USER_ADDED, 
        USER_REMOVED, 
        BOT_STARTED, 
        CHAT_TITLE_CHANGED
    ));

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

    @JsonProperty("update_type")
    public String getType() {
        throw new UnsupportedOperationException("Model has no concrete type.");
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
        void visit(BotAddedToChatUpdate model);
        void visit(BotRemovedFromChatUpdate model);
        void visit(UserAddedToChatUpdate model);
        void visit(UserRemovedFromChatUpdate model);
        void visit(BotStartedUpdate model);
        void visit(ChatTitleChangedUpdate model);
        void visitDefault(Update model);
    }
}
