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
 * You will receive this update when user has been removed from chat where bot is administrator
 */
public class UserRemovedFromChatUpdate extends Update implements TamTamSerializable {

    @NotNull
    private final @Valid Long chatId;
    @NotNull
    private final @Valid User user;
    private @Valid Long adminId;

    @JsonCreator
    public UserRemovedFromChatUpdate(@JsonProperty("chat_id") Long chatId, @JsonProperty("user") User user, @JsonProperty("timestamp") Long timestamp) { 
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
    * Chat identifier where event has occurred
    * @return chatId
    **/
    @JsonProperty("chat_id")
    public Long getChatId() {
        return chatId;
    }

    /**
    * User removed from chat
    * @return user
    **/
    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    public UserRemovedFromChatUpdate adminId(Long adminId) {
        this.setAdminId(adminId);
        return this;
    }

    /**
    * Administrator who removed user from chat. Can be &#x60;null&#x60; in case when user left chat
    * @return adminId
    **/
    @JsonProperty("admin_id")
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    @JsonProperty("update_type")
    @Override
    public String getType() {
        return Update.USER_REMOVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        UserRemovedFromChatUpdate other = (UserRemovedFromChatUpdate) o;
        return Objects.equals(this.chatId, other.chatId) &&
            Objects.equals(this.user, other.user) &&
            Objects.equals(this.adminId, other.adminId) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (adminId != null ? adminId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRemovedFromChatUpdate{"+ super.toString()
            + " chatId='" + chatId + '\''
            + " user='" + user + '\''
            + " adminId='" + adminId + '\''
            + '}';
    }
}
