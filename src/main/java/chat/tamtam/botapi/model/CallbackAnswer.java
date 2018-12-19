package chat.tamtam.botapi.model;

/*-
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

import java.util.Objects;
import java.util.Arrays;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.Recipient;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * Send this object when your bot wants to react to when a button is pressed
 */
public class CallbackAnswer implements TamTamSerializable {
    @JsonProperty("recipient")
    private final Recipient recipient;

    @JsonProperty("message")
    private final NewMessageBody message;

    @JsonProperty("notification")
    private final String notification;

    @JsonCreator
    public CallbackAnswer(@JsonProperty("recipient") Recipient recipient, @Nullable @JsonProperty("message") NewMessageBody message, @Nullable @JsonProperty("notification") String notification) { 
        this.recipient = recipient;
        this.message = message;
        this.notification = notification;
    }

    /**
    * @return recipient
    **/
    public Recipient getRecipient() {
        return recipient;
    }

    /**
    * Fill this if you want to modify current message
    * @return message
    **/
    @Nullable
    public NewMessageBody getMessage() {
        return message;
    }

    /**
    * Fill this if you just want to send one-time notification to user
    * @return notification
    **/
    @Nullable
    public String getNotification() {
        return notification;
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
        return Objects.equals(this.recipient, other.recipient) &&
            Objects.equals(this.message, other.message) &&
            Objects.equals(this.notification, other.notification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipient, message, notification);
    }

    @Override
    public String toString() {
        return "CallbackAnswer{"
            + " recipient='" + recipient + '\''
            + " message='" + message + '\''
            + " notification='" + notification + '\''
            + '}';
    }
}

