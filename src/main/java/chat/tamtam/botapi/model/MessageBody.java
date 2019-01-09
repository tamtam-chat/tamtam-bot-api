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
import chat.tamtam.botapi.model.Attachment;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * Schema representing body of message
 */
public class MessageBody implements TamTamSerializable {
    @JsonProperty("mid")
    private final String mid;

    @JsonProperty("seq")
    private final Long seq;

    @JsonProperty("text")
    private final String text;

    @JsonProperty("attachments")
    private final List<Attachment> attachments;

    @JsonProperty("reply_to")
    private String replyTo;

    @JsonCreator
    public MessageBody(@JsonProperty("mid") String mid, @JsonProperty("seq") Long seq, @Nullable @JsonProperty("text") String text, @Nullable @JsonProperty("attachments") List<Attachment> attachments) { 
        this.mid = mid;
        this.seq = seq;
        this.text = text;
        this.attachments = attachments;
    }

    /**
    * Unique identifier of message
    * @return mid
    **/
    public String getMid() {
        return mid;
    }

    /**
    * Sequence identifier of message in chat
    * @return seq
    **/
    public Long getSeq() {
        return seq;
    }

    /**
    * Message text
    * @return text
    **/
    @Nullable
    public String getText() {
        return text;
    }

    /**
    * Message attachments. Could be one of &#x60;Attachment&#x60; type. See description of this schema
    * @return attachments
    **/
    @Nullable
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public MessageBody replyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    /**
    * In case this message is repled to, it is the unique identifier of the replied message
    * @return replyTo
    **/
    @Nullable
    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        MessageBody other = (MessageBody) o;
        return Objects.equals(this.mid, other.mid) &&
            Objects.equals(this.seq, other.seq) &&
            Objects.equals(this.text, other.text) &&
            Objects.equals(this.attachments, other.attachments) &&
            Objects.equals(this.replyTo, other.replyTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid, seq, text, attachments, replyTo);
    }

    @Override
    public String toString() {
        return "MessageBody{"
            + " mid='" + mid + '\''
            + " seq='" + seq + '\''
            + " text='" + text + '\''
            + " attachments='" + attachments + '\''
            + " replyTo='" + replyTo + '\''
            + '}';
    }
}

