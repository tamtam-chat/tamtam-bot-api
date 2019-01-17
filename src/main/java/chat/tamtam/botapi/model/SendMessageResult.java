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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * SendMessageResult
 */
public class SendMessageResult implements TamTamSerializable {
  
    private final Long chatId;
    private final Long recipientId;
    private final String messageId;

    @JsonCreator
    public SendMessageResult(@JsonProperty("chat_id") Long chatId, @Nullable @JsonProperty("recipient_id") Long recipientId, @JsonProperty("message_id") String messageId) { 
        this.chatId = chatId;
        this.recipientId = recipientId;
        this.messageId = messageId;
    }

    /**
    * Identifier of chat message was created in
    * @return chatId
    **/
    @JsonProperty("chat_id")
    public Long getChatId() {
        return chatId;
    }

    /**
    * In most cases same as chat_id.
    * @return recipientId
    **/
    @Nullable
    @JsonProperty("recipient_id")
    public Long getRecipientId() {
        return recipientId;
    }

    /**
    * Unique identifier of created message
    * @return messageId
    **/
    @JsonProperty("message_id")
    public String getMessageId() {
        return messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        SendMessageResult other = (SendMessageResult) o;
        return Objects.equals(this.chatId, other.chatId) &&
            Objects.equals(this.recipientId, other.recipientId) &&
            Objects.equals(this.messageId, other.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, recipientId, messageId);
    }

    @Override
    public String toString() {
        return "SendMessageResult{"
            + " chatId='" + chatId + '\''
            + " recipientId='" + recipientId + '\''
            + " messageId='" + messageId + '\''
            + '}';
    }
}

