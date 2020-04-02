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
 * Message in chat
 */
public class Message implements TamTamSerializable {

    private @Valid User sender;
    @NotNull
    private final @Valid Recipient recipient;
    @NotNull
    private final @Valid Long timestamp;
    @Nullable
    private @Valid LinkedMessage link;
    @NotNull
    private final @Valid MessageBody body;
    @Nullable
    private @Valid MessageStat stat;
    @Nullable
    private @Valid String url;
    @Nullable
    private @Valid User constructor;

    @JsonCreator
    public Message(@JsonProperty("recipient") Recipient recipient, @JsonProperty("timestamp") Long timestamp, @JsonProperty("body") MessageBody body) { 
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.body = body;
    }

    public Message sender(User sender) {
        this.setSender(sender);
        return this;
    }

    /**
    * User who sent this message. Can be &#x60;null&#x60; if message has been posted on behalf of a channel
    * @return sender
    **/
    @JsonProperty("sender")
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
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

    public Message link(@Nullable LinkedMessage link) {
        this.setLink(link);
        return this;
    }

    /**
    * Forwarded or replied message
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
    * Body of created message. Text + attachments. Could be null if message contains only forwarded message
    * @return body
    **/
    @JsonProperty("body")
    public MessageBody getBody() {
        return body;
    }

    public Message stat(@Nullable MessageStat stat) {
        this.setStat(stat);
        return this;
    }

    /**
    * Message statistics. Available only for channels in [GET:/messages](#operation/getMessages) context
    * @return stat
    **/
    @Nullable
    @JsonProperty("stat")
    public MessageStat getStat() {
        return stat;
    }

    public void setStat(@Nullable MessageStat stat) {
        this.stat = stat;
    }

    public Message url(@Nullable String url) {
        this.setUrl(url);
        return this;
    }

    /**
    * Message public URL. Can be &#x60;null&#x60; for dialogs or non-public chats/channels
    * @return url
    **/
    @Nullable
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    public Message constructor(@Nullable User constructor) {
        this.setConstructor(constructor);
        return this;
    }

    /**
    * Bot-constructor created this message
    * @return constructor
    **/
    @Nullable
    @JsonProperty("constructor")
    public User getConstructor() {
        return constructor;
    }

    public void setConstructor(@Nullable User constructor) {
        this.constructor = constructor;
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
            Objects.equals(this.link, other.link) &&
            Objects.equals(this.body, other.body) &&
            Objects.equals(this.stat, other.stat) &&
            Objects.equals(this.url, other.url) &&
            Objects.equals(this.constructor, other.constructor);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (stat != null ? stat.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (constructor != null ? constructor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{"
            + " sender='" + sender + '\''
            + " recipient='" + recipient + '\''
            + " timestamp='" + timestamp + '\''
            + " link='" + link + '\''
            + " body='" + body + '\''
            + " stat='" + stat + '\''
            + " url='" + url + '\''
            + " constructor='" + constructor + '\''
            + '}';
    }
}
