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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * You will get this &#x60;update&#x60; as soon as message is removed
 */
public class MessageRemovedUpdate extends Update implements TamTamSerializable {

    @NotNull
    private final @Valid String messageId;
    @NotNull
    private final @Valid Long chatId;
    @NotNull
    private final @Valid Long userId;

    @JsonCreator
    public MessageRemovedUpdate(@JsonProperty("message_id") String messageId, @JsonProperty("chat_id") Long chatId, @JsonProperty("user_id") Long userId, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.messageId = messageId;
        this.chatId = chatId;
        this.userId = userId;
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
    * Identifier of removed message
    * @return messageId
    **/
    @JsonProperty("message_id")
    public String getMessageId() {
        return messageId;
    }

    /**
    * Chat identifier where message has been deleted
    * @return chatId
    **/
    @JsonProperty("chat_id")
    public Long getChatId() {
        return chatId;
    }

    /**
    * User who deleted this message
    * @return userId
    **/
    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }

    @JsonProperty("update_type")
    @Override
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
            Objects.equals(this.chatId, other.chatId) &&
            Objects.equals(this.userId, other.userId) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageRemovedUpdate{"+ super.toString()
            + " messageId='" + messageId + '\''
            + " chatId='" + chatId + '\''
            + " userId='" + userId + '\''
            + '}';
    }
}
