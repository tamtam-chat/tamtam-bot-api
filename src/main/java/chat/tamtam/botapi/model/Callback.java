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
 * Object sent to bot when user presses button
 */
public class Callback implements TamTamSerializable {
    @JsonProperty("timestamp")
    private final Long timestamp;

    @JsonProperty("callback_id")
    private final String callbackId;

    @JsonProperty("payload")
    private final String payload;

    @JsonProperty("user")
    private final User user;

    @JsonCreator
    public Callback(@JsonProperty("timestamp") Long timestamp, @JsonProperty("callback_id") String callbackId, @JsonProperty("payload") String payload, @JsonProperty("user") User user) { 
        this.timestamp = timestamp;
        this.callbackId = callbackId;
        this.payload = payload;
        this.user = user;
    }

    /**
    * Unix-time when user pressed the button
    * @return timestamp
    **/
    public Long getTimestamp() {
        return timestamp;
    }

    /**
    * Current keyboard identifier
    * @return callbackId
    **/
    public String getCallbackId() {
        return callbackId;
    }

    /**
    * Button payload
    * @return payload
    **/
    public String getPayload() {
        return payload;
    }

    /**
    * User pressed the button
    * @return user
    **/
    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        Callback other = (Callback) o;
        return Objects.equals(this.timestamp, other.timestamp) &&
            Objects.equals(this.callbackId, other.callbackId) &&
            Objects.equals(this.payload, other.payload) &&
            Objects.equals(this.user, other.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, callbackId, payload, user);
    }

    @Override
    public String toString() {
        return "Callback{"
            + " timestamp='" + timestamp + '\''
            + " callbackId='" + callbackId + '\''
            + " payload='" + payload + '\''
            + " user='" + user + '\''
            + '}';
    }
}

