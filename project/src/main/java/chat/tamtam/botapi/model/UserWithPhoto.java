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
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * UserWithPhoto
 */
public class UserWithPhoto extends User implements TamTamSerializable {

    @Nullable
    @Size(max = 16000)
    private @Valid String description;
    private @Valid String avatarUrl;
    private @Valid String fullAvatarUrl;

    @JsonCreator
    public UserWithPhoto(@JsonProperty("user_id") Long userId, @JsonProperty("name") String name, @Nullable @JsonProperty("username") String username, @JsonProperty("is_bot") Boolean isBot) { 
        super(userId, name, username, isBot);
    }

    public UserWithPhoto description(@Nullable String description) {
        this.setDescription(description);
        return this;
    }

    /**
    * User description. Can be &#x60;null&#x60; if user did not fill it out
    * @return description
    **/
    @Nullable
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public UserWithPhoto description(@Nullable String description) {
        this.setDescription(description);
        return this;
    }

    /**
    * User description. Can be &#x60;null&#x60; if user did not fill it out
    * @return description
    **/
    @Nullable
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public UserWithPhoto avatarUrl(String avatarUrl) {
        this.setAvatarUrl(avatarUrl);
        return this;
    }

    /**
    * URL of avatar
    * @return avatarUrl
    **/
    @JsonProperty("avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public UserWithPhoto fullAvatarUrl(String fullAvatarUrl) {
        this.setFullAvatarUrl(fullAvatarUrl);
        return this;
    }

    /**
    * URL of avatar of a bigger size
    * @return fullAvatarUrl
    **/
    @JsonProperty("full_avatar_url")
    public String getFullAvatarUrl() {
        return fullAvatarUrl;
    }

    public void setFullAvatarUrl(String fullAvatarUrl) {
        this.fullAvatarUrl = fullAvatarUrl;
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
        return Objects.equals(this.description, other.description) &&
            Objects.equals(this.avatarUrl, other.avatarUrl) &&
            Objects.equals(this.fullAvatarUrl, other.fullAvatarUrl) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result + (fullAvatarUrl != null ? fullAvatarUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserWithPhoto{"+ super.toString()
            + " description='" + description + '\''
            + " avatarUrl='" + avatarUrl + '\''
            + " fullAvatarUrl='" + fullAvatarUrl + '\''
            + '}';
    }
}
