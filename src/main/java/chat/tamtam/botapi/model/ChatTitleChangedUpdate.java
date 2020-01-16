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
 * Bot gets this type of update as soon as title has been changed in chat
 */
public class ChatTitleChangedUpdate extends Update implements TamTamSerializable {

    @NotNull
    private final @Valid Long chatId;
    @NotNull
    private final @Valid User user;
    @NotNull
    private final @Valid String title;

    @JsonCreator
    public ChatTitleChangedUpdate(@JsonProperty("chat_id") Long chatId, @JsonProperty("user") User user, @JsonProperty("title") String title, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.chatId = chatId;
        this.user = user;
        this.title = title;
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
    * Chat identifier where event has occurred
    * @return chatId
    **/
    @JsonProperty("chat_id")
    public Long getChatId() {
        return chatId;
    }

    /**
    * User who changed title
    * @return user
    **/
    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    /**
    * New title
    * @return title
    **/
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("update_type")
    @Override
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
            Objects.equals(this.user, other.user) &&
            Objects.equals(this.title, other.title) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatTitleChangedUpdate{"+ super.toString()
            + " chatId='" + chatId + '\''
            + " user='" + user + '\''
            + " title='" + title + '\''
            + '}';
    }
}
