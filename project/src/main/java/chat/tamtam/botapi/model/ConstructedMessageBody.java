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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * ConstructedMessageBody
 */
public class ConstructedMessageBody implements TamTamSerializable {

    @Nullable
    @Size(max = 4000)
    private @Valid String text;
    @Nullable
    private List<@Valid AttachmentRequest> attachments;
    @Nullable
    private List<@Valid MarkupElement> markup;
    @Nullable
    private @Valid TextFormat format;

    public ConstructedMessageBody text(@Nullable String text) {
        this.setText(text);
        return this;
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

    public void setText(@Nullable String text) {
        this.text = text;
    }

    public ConstructedMessageBody attachments(@Nullable List<AttachmentRequest> attachments) {
        this.setAttachments(attachments);
        return this;
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

    public void setAttachments(@Nullable List<AttachmentRequest> attachments) {
        this.attachments = attachments;
    }

    public ConstructedMessageBody markup(@Nullable List<MarkupElement> markup) {
        this.setMarkup(markup);
        return this;
    }

    /**
    * Text markup
    * @return markup
    **/
    @Nullable
    @JsonProperty("markup")
    public List<MarkupElement> getMarkup() {
        return markup;
    }

    public void setMarkup(@Nullable List<MarkupElement> markup) {
        this.markup = markup;
    }

    public ConstructedMessageBody format(@Nullable TextFormat format) {
        this.setFormat(format);
        return this;
    }

    /**
    * Message text format. If set, 
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

        ConstructedMessageBody other = (ConstructedMessageBody) o;
        return Objects.equals(this.text, other.text) &&
            Objects.equals(this.attachments, other.attachments) &&
            Objects.equals(this.markup, other.markup) &&
            Objects.equals(this.format, other.format);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (attachments != null ? attachments.hashCode() : 0);
        result = 31 * result + (markup != null ? markup.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConstructedMessageBody{"
            + " text='" + text + '\''
            + " attachments='" + attachments + '\''
            + " markup='" + markup + '\''
            + " format='" + format + '\''
            + '}';
    }
}
