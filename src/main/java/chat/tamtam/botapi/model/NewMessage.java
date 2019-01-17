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
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.Recipient;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * New message. Could be &#x60;control&#x60;, &#x60;sender_action&#x60; or real &#x60;message&#x60;
 */
public class NewMessage implements TamTamSerializable {
    @JsonProperty("recipient")
    private final Recipient recipient;

    @JsonProperty("message")
    private NewMessageBody message;

    @JsonCreator
    public NewMessage(@JsonProperty("recipient") Recipient recipient) { 
        this.recipient = recipient;
    }

    /**
    * Message recipient. User or chat
    * @return recipient
    **/
    public Recipient getRecipient() {
        return recipient;
    }

    public NewMessage message(NewMessageBody message) {
        this.message = message;
        return this;
    }

    /**
    * Real message to send to chat
    * @return message
    **/
    @Nullable
    public NewMessageBody getMessage() {
        return message;
    }

    public void setMessage(NewMessageBody message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        NewMessage other = (NewMessage) o;
        return Objects.equals(this.recipient, other.recipient) &&
            Objects.equals(this.message, other.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipient, message);
    }

    @Override
    public String toString() {
        return "NewMessage{"
            + " recipient='" + recipient + '\''
            + " message='" + message + '\''
            + '}';
    }
}

