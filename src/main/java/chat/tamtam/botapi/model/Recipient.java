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
import chat.tamtam.botapi.model.ChatType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * New message recepient. Could be user or chat
 */
public class Recipient implements TamTamSerializable {
    @JsonProperty("chat_id")
    private final Long chatId;

    @JsonProperty("chat_type")
    private final ChatType chatType;

    @JsonProperty("user_id")
    private final Long userId;

    @JsonCreator
    public Recipient(@Nullable @JsonProperty("chat_id") Long chatId, @JsonProperty("chat_type") ChatType chatType, @Nullable @JsonProperty("user_id") Long userId) { 
        this.chatId = chatId;
        this.chatType = chatType;
        this.userId = userId;
    }

    /**
    * User identifier
    * @return chatId
    **/
    @Nullable
    public Long getChatId() {
        return chatId;
    }

    /**
    * Chat type
    * @return chatType
    **/
    public ChatType getChatType() {
        return chatType;
    }

    /**
    * Chat identifier
    * @return userId
    **/
    @Nullable
    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        Recipient other = (Recipient) o;
        return Objects.equals(this.chatId, other.chatId) &&
            Objects.equals(this.chatType, other.chatType) &&
            Objects.equals(this.userId, other.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, chatType, userId);
    }

    @Override
    public String toString() {
        return "Recipient{"
            + " chatId='" + chatId + '\''
            + " chatType='" + chatType + '\''
            + " userId='" + userId + '\''
            + '}';
    }
}

