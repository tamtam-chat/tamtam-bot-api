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

import org.jetbrains.annotations.Nullable;

/**
 * You will receive this update when user has been added to chat where bot is administrator
 */
public class UserAddedToChatUpdate extends Update implements TamTamSerializable {

    @NotNull
    private final @Valid Long chatId;
    @NotNull
    private final @Valid User user;
    @Nullable
    private @Valid Long inviterId;
    @NotNull
    private final @Valid Boolean isChannel;

    @JsonCreator
    public UserAddedToChatUpdate(@JsonProperty("chat_id") Long chatId, @JsonProperty("user") User user, @JsonProperty("is_channel") Boolean isChannel, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.chatId = chatId;
        this.user = user;
        this.isChannel = isChannel;
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
    * User added to chat
    * @return user
    **/
    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    public UserAddedToChatUpdate inviterId(@Nullable Long inviterId) {
        this.setInviterId(inviterId);
        return this;
    }

    /**
    * User who added user to chat. Can be &#x60;null&#x60; in case when user joined chat by link
    * @return inviterId
    **/
    @Nullable
    @JsonProperty("inviter_id")
    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(@Nullable Long inviterId) {
        this.inviterId = inviterId;
    }

    /**
    * Indicates whether user has been added to channel or not
    * @return isChannel
    **/
    @JsonProperty("is_channel")
    public Boolean isChannel() {
        return isChannel;
    }

    @JsonProperty("update_type")
    @Override
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
            Objects.equals(this.user, other.user) &&
            Objects.equals(this.inviterId, other.inviterId) &&
            Objects.equals(this.isChannel, other.isChannel) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (inviterId != null ? inviterId.hashCode() : 0);
        result = 31 * result + (isChannel != null ? isChannel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserAddedToChatUpdate{"+ super.toString()
            + " chatId='" + chatId + '\''
            + " user='" + user + '\''
            + " inviterId='" + inviterId + '\''
            + " isChannel='" + isChannel + '\''
            + '}';
    }
}
