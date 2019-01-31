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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;

/**
 * Send this object when your bot wants to react to when a button is pressed
 */
public class CallbackAnswer implements TamTamSerializable {

    private Long userId;
    private NewMessageBody message;
    private String notification;

    public CallbackAnswer userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    /**
    * @return userId
    **/
    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CallbackAnswer message(@Nullable NewMessageBody message) {
        this.setMessage(message);
        return this;
    }

    /**
    * Fill this if you want to modify current message
    * @return message
    **/
    @Nullable
    @JsonProperty("message")
    public NewMessageBody getMessage() {
        return message;
    }

    public void setMessage(@Nullable NewMessageBody message) {
        this.message = message;
    }

    public CallbackAnswer notification(@Nullable String notification) {
        this.setNotification(notification);
        return this;
    }

    /**
    * Fill this if you just want to send one-time notification to user
    * @return notification
    **/
    @Nullable
    @JsonProperty("notification")
    public String getNotification() {
        return notification;
    }

    public void setNotification(@Nullable String notification) {
        this.notification = notification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        CallbackAnswer other = (CallbackAnswer) o;
        return Objects.equals(this.userId, other.userId) &&
            Objects.equals(this.message, other.message) &&
            Objects.equals(this.notification, other.notification);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (notification != null ? notification.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CallbackAnswer{"
            + " userId='" + userId + '\''
            + " message='" + message + '\''
            + " notification='" + notification + '\''
            + '}';
    }
}

