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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * NewMessageBody
 */
public class NewMessageBody implements TamTamSerializable {

    @Nullable
    @Size(max = 4000)
    private final @Valid String text;
    @Nullable
    private final List<@Valid AttachmentRequest> attachments;
    @Nullable
    private final @Valid NewMessageLink link;
    private @Valid Boolean notify;
    @Nullable
    private @Valid TextFormat format;

    @JsonCreator
    public NewMessageBody(@Nullable @JsonProperty("text") String text, @Nullable @JsonProperty("attachments") List<AttachmentRequest> attachments, @Nullable @JsonProperty("link") NewMessageLink link) { 
        this.text = text;
        this.attachments = attachments;
        this.link = link;
    }

    /**
    * Message text
    * @return text
    **/
    @Nullable
    @JsonProperty("text")
    public String getText() {
        return text;
    }

    /**
    * Message attachments. See &#x60;AttachmentRequest&#x60; and it&#39;s inheritors for full information
    * @return attachments
    **/
    @Nullable
    @JsonProperty("attachments")
    public List<AttachmentRequest> getAttachments() {
        return attachments;
    }

    /**
    * Link to Message
    * @return link
    **/
    @Nullable
    @JsonProperty("link")
    public NewMessageLink getLink() {
        return link;
    }

    public NewMessageBody notify(Boolean notify) {
        this.setNotify(notify);
        return this;
    }

    /**
    * If false, chat participants would not be notified
    * @return notify
    **/
    @JsonProperty("notify")
    public Boolean isNotify() {
        return notify;
    }

    public void setNotify(Boolean notify) {
        this.notify = notify;
    }

    public NewMessageBody format(@Nullable TextFormat format) {
        this.setFormat(format);
        return this;
    }

    /**
    * If set, message text will be formated according to given markup
    * @return format
    **/
    @Nullable
    @JsonProperty("format")
    public TextFormat getFormat() {
        return format;
    }

    public void setFormat(@Nullable TextFormat format) {
        this.format = format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        NewMessageBody other = (NewMessageBody) o;
        return Objects.equals(this.text, other.text) &&
            Objects.equals(this.attachments, other.attachments) &&
            Objects.equals(this.link, other.link) &&
            Objects.equals(this.notify, other.notify) &&
            Objects.equals(this.format, other.format);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (attachments != null ? attachments.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (notify != null ? notify.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewMessageBody{"
            + " text='" + text + '\''
            + " attachments='" + attachments + '\''
            + " link='" + link + '\''
            + " notify='" + notify + '\''
            + " format='" + format + '\''
            + '}';
    }
}
