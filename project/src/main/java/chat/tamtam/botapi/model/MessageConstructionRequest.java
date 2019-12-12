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
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * Bot will get this update when user sent to bot any message or pressed button during construction process
 */
public class MessageConstructionRequest extends Update implements TamTamSerializable {

    @NotNull
    private final @Valid UserWithPhoto user;
    private @Valid String userLocale;
    @NotNull
    private final @Valid String sessionId;
    @Nullable
    @Size(max = 8192)
    private @Valid String data;
    @NotNull
    private final @Valid ConstructorInput input;

    @JsonCreator
    public MessageConstructionRequest(@JsonProperty("user") UserWithPhoto user, @JsonProperty("session_id") String sessionId, @JsonProperty("input") ConstructorInput input, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.user = user;
        this.sessionId = sessionId;
        this.input = input;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    /**
    * User who initiated this request
    * @return user
    **/
    @JsonProperty("user")
    public UserWithPhoto getUser() {
        return user;
    }

    public MessageConstructionRequest userLocale(String userLocale) {
        this.setUserLocale(userLocale);
        return this;
    }

    /**
    * Current user locale in IETF BCP 47 format
    * @return userLocale
    **/
    @JsonProperty("user_locale")
    public String getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(String userLocale) {
        this.userLocale = userLocale;
    }

    /**
    * Constructor session identifier
    * @return sessionId
    **/
    @JsonProperty("session_id")
    public String getSessionId() {
        return sessionId;
    }

    public MessageConstructionRequest data(@Nullable String data) {
        this.setData(data);
        return this;
    }

    /**
    * data received from previous &#x60;ConstructorAnswer&#x60;
    * @return data
    **/
    @Nullable
    @JsonProperty("data")
    public String getData() {
        return data;
    }

    public void setData(@Nullable String data) {
        this.data = data;
    }

    /**
    * User&#39;s input. It can be message (text/attachments) or simple button&#39;s callback
    * @return input
    **/
    @JsonProperty("input")
    public ConstructorInput getInput() {
        return input;
    }

    @JsonProperty("update_type")
    @Override
    public String getType() {
        return Update.MESSAGE_CONSTRUCTION_REQUEST;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        MessageConstructionRequest other = (MessageConstructionRequest) o;
        return Objects.equals(this.user, other.user) &&
            Objects.equals(this.userLocale, other.userLocale) &&
            Objects.equals(this.sessionId, other.sessionId) &&
            Objects.equals(this.data, other.data) &&
            Objects.equals(this.input, other.input) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (userLocale != null ? userLocale.hashCode() : 0);
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (input != null ? input.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageConstructionRequest{"+ super.toString()
            + " user='" + user + '\''
            + " userLocale='" + userLocale + '\''
            + " sessionId='" + sessionId + '\''
            + " data='" + data + '\''
            + " input='" + input + '\''
            + '}';
    }
}
