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
import chat.tamtam.botapi.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * UserWithPhoto
 */
public class UserWithPhoto extends User implements TamTamSerializable {
  
    private final String avatarUrl;
    private final String fullAvatarUrl;

    @JsonCreator
    public UserWithPhoto(@JsonProperty("avatar_url") String avatarUrl, @JsonProperty("full_avatar_url") String fullAvatarUrl, @JsonProperty("user_id") Long userId, @JsonProperty("name") String name, @Nullable @JsonProperty("username") String username) { 
        super(userId, name, username);
        this.avatarUrl = avatarUrl;
        this.fullAvatarUrl = fullAvatarUrl;
    }

    /**
    * URL of avatar
    * @return avatarUrl
    **/
    @JsonProperty("avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
    * URL of avatar of a bigger size
    * @return fullAvatarUrl
    **/
    @JsonProperty("full_avatar_url")
    public String getFullAvatarUrl() {
        return fullAvatarUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        UserWithPhoto other = (UserWithPhoto) o;
        return Objects.equals(this.avatarUrl, other.avatarUrl) &&
            Objects.equals(this.fullAvatarUrl, other.fullAvatarUrl) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(avatarUrl, fullAvatarUrl, super.hashCode());
    }

    @Override
    public String toString() {
        return "UserWithPhoto{"+ super.toString()
            + " avatarUrl='" + avatarUrl + '\''
            + " fullAvatarUrl='" + fullAvatarUrl + '\''
            + '}';
    }
}

