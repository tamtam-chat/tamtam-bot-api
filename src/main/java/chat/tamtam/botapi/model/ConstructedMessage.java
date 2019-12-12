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
 * Message constructed i in chat
 */
public class ConstructedMessage implements TamTamSerializable {

    @NotNull
    private final @Valid User sender;
    @NotNull
    private final @Valid Long timestamp;
    @Nullable
    private @Valid LinkedMessage link;
    @NotNull
    private final @Valid MessageBody body;

    @JsonCreator
    public ConstructedMessage(@JsonProperty("sender") User sender, @JsonProperty("timestamp") Long timestamp, @JsonProperty("body") MessageBody body) { 
        this.sender = sender;
        this.timestamp = timestamp;
        this.body = body;
    }

    /**
    * User who sent this message
    * @return sender
    **/
    @JsonProperty("sender")
    public User getSender() {
        return sender;
    }

    /**
    * Unix-time when message has been created
    * @return timestamp
    **/
    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    public ConstructedMessage link(@Nullable LinkedMessage link) {
        this.setLink(link);
        return this;
    }

    /**
    * Any linked message: forward or reply
    * @return link
    **/
    @Nullable
    @JsonProperty("link")
    public LinkedMessage getLink() {
        return link;
    }

    public void setLink(@Nullable LinkedMessage link) {
        this.link = link;
    }

    /**
    * Body of created message. Text + attachments
    * @return body
    **/
    @JsonProperty("body")
    public MessageBody getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ConstructedMessage other = (ConstructedMessage) o;
        return Objects.equals(this.sender, other.sender) &&
            Objects.equals(this.timestamp, other.timestamp) &&
            Objects.equals(this.link, other.link) &&
            Objects.equals(this.body, other.body);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConstructedMessage{"
            + " sender='" + sender + '\''
            + " timestamp='" + timestamp + '\''
            + " link='" + link + '\''
            + " body='" + body + '\''
            + '}';
    }
}
