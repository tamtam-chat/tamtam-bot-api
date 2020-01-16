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
 * You will receive this update when bot has been removed from chat
 */
public class BotRemovedFromChatUpdate extends Update implements TamTamSerializable {

    @NotNull
    private final @Valid Long chatId;
    @NotNull
    private final @Valid User user;

    @JsonCreator
    public BotRemovedFromChatUpdate(@JsonProperty("chat_id") Long chatId, @JsonProperty("user") User user, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.chatId = chatId;
        this.user = user;
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
    * Chat identifier bot removed from
    * @return chatId
    **/
    @JsonProperty("chat_id")
    public Long getChatId() {
        return chatId;
    }

    /**
    * User who removed bot from chat
    * @return user
    **/
    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("update_type")
    @Override
    public String getType() {
        return Update.BOT_REMOVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        BotRemovedFromChatUpdate other = (BotRemovedFromChatUpdate) o;
        return Objects.equals(this.chatId, other.chatId) &&
            Objects.equals(this.user, other.user) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BotRemovedFromChatUpdate{"+ super.toString()
            + " chatId='" + chatId + '\''
            + " user='" + user + '\''
            + '}';
    }
}
