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
 * Bot gets this type of update as soon as title has been changed in chat
 */
public class ChatTitleChangedUpdate extends Update implements TamTamSerializable {

    private final Long chatId;
    private final Long userId;
    private final String title;

    @JsonCreator
    public ChatTitleChangedUpdate(@JsonProperty("chat_id") Long chatId, @JsonProperty("user_id") Long userId, @JsonProperty("title") String title, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.chatId = chatId;
        this.userId = userId;
        this.title = title;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    /**
    * Chat identifier where event has occurred
    * @return chatId
    **/
    @JsonProperty("chat_id")
    public Long getChatId() {
        return chatId;
    }

    /**
    * User who changed title
    * @return userId
    **/
    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }

    /**
    * New title
    * @return title
    **/
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public String getType() {
        return Update.CHAT_TITLE_CHANGED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ChatTitleChangedUpdate other = (ChatTitleChangedUpdate) o;
        return Objects.equals(this.chatId, other.chatId) &&
            Objects.equals(this.userId, other.userId) &&
            Objects.equals(this.title, other.title) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatTitleChangedUpdate{"+ super.toString()
            + " chatId='" + chatId + '\''
            + " userId='" + userId + '\''
            + " title='" + title + '\''
            + '}';
    }
}
