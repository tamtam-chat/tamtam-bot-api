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
 * You will receive this update when user has been added to chat where bot is administrator
 */
public class UserAddedToChatUpdate extends Update implements TamTamSerializable {

    private final Long chatId;
    private final Long userId;
    private final Long inviterId;

    @JsonCreator
    public UserAddedToChatUpdate(@JsonProperty("chat_id") Long chatId, @JsonProperty("user_id") Long userId, @JsonProperty("inviter_id") Long inviterId, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.chatId = chatId;
        this.userId = userId;
        this.inviterId = inviterId;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    /**
    * Chat identifier where event has occured
    * @return chatId
    **/
    @JsonProperty("chat_id")
    public Long getChatId() {
        return chatId;
    }

    /**
    * User added to chat
    * @return userId
    **/
    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }

    /**
    * User who added user to chat
    * @return inviterId
    **/
    @JsonProperty("inviter_id")
    public Long getInviterId() {
        return inviterId;
    }

    public String getType() {
        return Update.USER_ADDED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        UserAddedToChatUpdate other = (UserAddedToChatUpdate) o;
        return Objects.equals(this.chatId, other.chatId) &&
            Objects.equals(this.userId, other.userId) &&
            Objects.equals(this.inviterId, other.inviterId) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (inviterId != null ? inviterId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserAddedToChatUpdate{"+ super.toString()
            + " chatId='" + chatId + '\''
            + " userId='" + userId + '\''
            + " inviterId='" + inviterId + '\''
            + '}';
    }
}
