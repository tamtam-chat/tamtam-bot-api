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
import chat.tamtam.botapi.model.UserWithPhoto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * ChatMember
 */
public class ChatMember extends UserWithPhoto implements TamTamSerializable {
  
    private final Long lastAccessTime;
    private final Boolean isOwner;

    @JsonCreator
    public ChatMember(@JsonProperty("last_access_time") Long lastAccessTime, @JsonProperty("is_owner") Boolean isOwner, @JsonProperty("user_id") Long userId, @JsonProperty("name") String name, @Nullable @JsonProperty("username") String username, @JsonProperty("avatar_url") String avatarUrl, @JsonProperty("full_avatar_url") String fullAvatarUrl) { 
        super(avatarUrl, fullAvatarUrl, userId, name, username);
        this.lastAccessTime = lastAccessTime;
        this.isOwner = isOwner;
    }

    /**
    * @return lastAccessTime
    **/
    @JsonProperty("last_access_time")
    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    /**
    * @return isOwner
    **/
    @JsonProperty("is_owner")
    public Boolean getIsOwner() {
        return isOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ChatMember other = (ChatMember) o;
        return Objects.equals(this.lastAccessTime, other.lastAccessTime) &&
            Objects.equals(this.isOwner, other.isOwner) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastAccessTime, isOwner, super.hashCode());
    }

    @Override
    public String toString() {
        return "ChatMember{"+ super.toString()
            + " lastAccessTime='" + lastAccessTime + '\''
            + " isOwner='" + isOwner + '\''
            + '}';
    }
}

