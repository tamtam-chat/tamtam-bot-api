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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * ChatMember
 */
public class ChatMember extends UserWithPhoto implements TamTamSerializable {

    @NotNull
    private final @Valid Long lastAccessTime;
    @NotNull
    private final @Valid Boolean isOwner;
    @NotNull
    private final @Valid Boolean isAdmin;
    @NotNull
    private final @Valid Long joinTime;
    @Nullable
    private final Set<@Valid ChatAdminPermission> permissions;

    @JsonCreator
    public ChatMember(@JsonProperty("last_access_time") Long lastAccessTime, @JsonProperty("is_owner") Boolean isOwner, @JsonProperty("is_admin") Boolean isAdmin, @JsonProperty("join_time") Long joinTime, @Nullable @JsonProperty("permissions") Set<ChatAdminPermission> permissions, @JsonProperty("user_id") Long userId, @JsonProperty("name") String name, @Nullable @JsonProperty("username") String username, @JsonProperty("is_bot") Boolean isBot, @JsonProperty("last_activity_time") Long lastActivityTime) { 
        super(userId, name, username, isBot, lastActivityTime);
        this.lastAccessTime = lastAccessTime;
        this.isOwner = isOwner;
        this.isAdmin = isAdmin;
        this.joinTime = joinTime;
        this.permissions = permissions;
    }

    /**
    * User last activity time in chat. Can be outdated for super chats and channels (equals to &#x60;join_time&#x60;)
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
    public Boolean isOwner() {
        return isOwner;
    }

    /**
    * @return isAdmin
    **/
    @JsonProperty("is_admin")
    public Boolean isAdmin() {
        return isAdmin;
    }

    /**
    * @return joinTime
    **/
    @JsonProperty("join_time")
    public Long getJoinTime() {
        return joinTime;
    }

    /**
    * Permissions in chat if member is admin. &#x60;null&#x60; otherwise
    * @return permissions
    **/
    @Nullable
    @JsonProperty("permissions")
    public Set<ChatAdminPermission> getPermissions() {
        return permissions;
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
            Objects.equals(this.isAdmin, other.isAdmin) &&
            Objects.equals(this.joinTime, other.joinTime) &&
            Objects.equals(this.permissions, other.permissions) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lastAccessTime != null ? lastAccessTime.hashCode() : 0);
        result = 31 * result + (isOwner != null ? isOwner.hashCode() : 0);
        result = 31 * result + (isAdmin != null ? isAdmin.hashCode() : 0);
        result = 31 * result + (joinTime != null ? joinTime.hashCode() : 0);
        result = 31 * result + (permissions != null ? permissions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatMember{"+ super.toString()
            + " lastAccessTime='" + lastAccessTime + '\''
            + " isOwner='" + isOwner + '\''
            + " isAdmin='" + isAdmin + '\''
            + " joinTime='" + joinTime + '\''
            + " permissions='" + permissions + '\''
            + '}';
    }
}
