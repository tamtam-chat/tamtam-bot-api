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
import chat.tamtam.botapi.model.MessageBody;
import chat.tamtam.botapi.model.Recipient;
import chat.tamtam.botapi.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * Message in chat
 */
public class Message implements TamTamSerializable {
  
    private final User sender;
    private final Recipient recipient;
    private final Long timestamp;
    private final MessageBody message;

    @JsonCreator
    public Message(@JsonProperty("sender") User sender, @JsonProperty("recipient") Recipient recipient, @JsonProperty("timestamp") Long timestamp, @JsonProperty("message") MessageBody message) { 
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.message = message;
    }

    /**
    * User that sent this message
    * @return sender
    **/
    @JsonProperty("sender")
    public User getSender() {
        return sender;
    }

    /**
    * Message recipient. Could be user or chat
    * @return recipient
    **/
    @JsonProperty("recipient")
    public Recipient getRecipient() {
        return recipient;
    }

    /**
    * Unix-time when message was created
    * @return timestamp
    **/
    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    /**
    * Body of created message. Text+attachments.
    * @return message
    **/
    @JsonProperty("message")
    public MessageBody getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        Message other = (Message) o;
        return Objects.equals(this.sender, other.sender) &&
            Objects.equals(this.recipient, other.recipient) &&
            Objects.equals(this.timestamp, other.timestamp) &&
            Objects.equals(this.message, other.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient, timestamp, message);
    }

    @Override
    public String toString() {
        return "Message{"
            + " sender='" + sender + '\''
            + " recipient='" + recipient + '\''
            + " timestamp='" + timestamp + '\''
            + " message='" + message + '\''
            + '}';
    }
}

