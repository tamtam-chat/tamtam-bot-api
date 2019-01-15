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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * User
 */
public class User implements TamTamSerializable {
    @JsonProperty("user_id")
    private final Long userId;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("username")
    private final String username;

    @JsonCreator
    public User(@JsonProperty("user_id") Long userId, @JsonProperty("name") String name, @Nullable @JsonProperty("username") String username) { 
        this.userId = userId;
        this.name = name;
        this.username = username;
    }

    /**
    * Users identifier
    * @return userId
    **/
    public Long getUserId() {
        return userId;
    }

    /**
    * Users visible name
    * @return name
    **/
    public String getName() {
        return name;
    }

    /**
    * Unique public user name. Can be &#x60;null&#x60; if user is not accessible or it is not set
    * @return username
    **/
    @Nullable
    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        User other = (User) o;
        return Objects.equals(this.userId, other.userId) &&
            Objects.equals(this.name, other.name) &&
            Objects.equals(this.username, other.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, username);
    }

    @Override
    public String toString() {
        return "User{"
            + " userId='" + userId + '\''
            + " name='" + name + '\''
            + " username='" + username + '\''
            + '}';
    }
}

