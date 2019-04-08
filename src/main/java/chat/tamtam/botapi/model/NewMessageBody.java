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

import org.jetbrains.annotations.Nullable;

/**
 * NewMessageBody
 */
public class NewMessageBody implements TamTamSerializable {

    private final String text;
    private final List<AttachmentRequest> attachments;
    private Boolean notify;

    @JsonCreator
    public NewMessageBody(@Nullable @JsonProperty("text") String text, @Nullable @JsonProperty("attachments") List<AttachmentRequest> attachments) { 
        this.text = text;
        this.attachments = attachments;
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
    * Message attachments. See &#x60;AttachmentRequest&#x60; and it&#39;s inheritors for full information.
    * @return attachments
    **/
    @Nullable
    @JsonProperty("attachments")
    public List<AttachmentRequest> getAttachments() {
        return attachments;
    }

    public NewMessageBody notify(Boolean notify) {
        this.setNotify(notify);
        return this;
    }

    /**
    * If false, chat participants wouldn&#39;t be notified
    * @return notify
    **/
    @JsonProperty("notify")
    public Boolean isNotify() {
        return notify;
    }

    public void setNotify(Boolean notify) {
        this.notify = notify;
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
            Objects.equals(this.notify, other.notify);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (attachments != null ? attachments.hashCode() : 0);
        result = 31 * result + (notify != null ? notify.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewMessageBody{"
            + " text='" + text + '\''
            + " attachments='" + attachments + '\''
            + " notify='" + notify + '\''
            + '}';
    }
}
