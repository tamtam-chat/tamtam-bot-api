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
 * Bot will get this update when constructed message has been posted to any chat
 */
public class MessageConstructedUpdate extends Update implements TamTamSerializable {

    @NotNull
    private final @Valid String sessionId;
    @NotNull
    private final @Valid ConstructedMessage message;

    @JsonCreator
    public MessageConstructedUpdate(@JsonProperty("session_id") String sessionId, @JsonProperty("message") ConstructedMessage message, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.sessionId = sessionId;
        this.message = message;
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
    * Constructor session identifier
    * @return sessionId
    **/
    @JsonProperty("session_id")
    public String getSessionId() {
        return sessionId;
    }

    /**
    * @return message
    **/
    @JsonProperty("message")
    public ConstructedMessage getMessage() {
        return message;
    }

    @JsonProperty("update_type")
    @Override
    public String getType() {
        return Update.MESSAGE_CONSTRUCTED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        MessageConstructedUpdate other = (MessageConstructedUpdate) o;
        return Objects.equals(this.sessionId, other.sessionId) &&
            Objects.equals(this.message, other.message) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageConstructedUpdate{"+ super.toString()
            + " sessionId='" + sessionId + '\''
            + " message='" + message + '\''
            + '}';
    }
}
