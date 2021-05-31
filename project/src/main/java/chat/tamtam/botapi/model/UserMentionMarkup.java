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
 * Represents user mention in text. Mention can be both by user&#39;s username or ID if user doesn&#39;t have username
 */
public class UserMentionMarkup extends MarkupElement implements TamTamSerializable {

    @Nullable
    private @Valid String userLink;
    @Nullable
    private @Valid Long userId;

    @JsonCreator
    public UserMentionMarkup(@JsonProperty("from") Integer from, @JsonProperty("length") Integer length) { 
        super(from, length);
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T map(Mapper<T> mapper) {
        return mapper.map(this);
    }

    public UserMentionMarkup userLink(@Nullable String userLink) {
        this.setUserLink(userLink);
        return this;
    }

    /**
    * &#x60;@username&#x60; of mentioned user
    * @return userLink
    **/
    @Nullable
    @JsonProperty("user_link")
    public String getUserLink() {
        return userLink;
    }

    public void setUserLink(@Nullable String userLink) {
        this.userLink = userLink;
    }

    public UserMentionMarkup userId(@Nullable Long userId) {
        this.setUserId(userId);
        return this;
    }

    /**
    * Identifier of mentioned user without username
    * @return userId
    **/
    @Nullable
    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @JsonProperty("type")
    @Override
    public String getType() {
        return MarkupElement.USER_MENTION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        UserMentionMarkup other = (UserMentionMarkup) o;
        return Objects.equals(this.userLink, other.userLink) &&
            Objects.equals(this.userId, other.userId) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userLink != null ? userLink.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserMentionMarkup{"+ super.toString()
            + " userLink='" + userLink + '\''
            + " userId='" + userId + '\''
            + '}';
    }
}
