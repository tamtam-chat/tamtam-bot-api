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
 * ChatPatch
 */
public class ChatPatch implements TamTamSerializable {

    private PhotoAttachmentRequestPayload icon;
    private String title;

    public ChatPatch icon(@Nullable PhotoAttachmentRequestPayload icon) {
        this.setIcon(icon);
        return this;
    }

    /**
    * @return icon
    **/
    @Nullable
    @JsonProperty("icon")
    public PhotoAttachmentRequestPayload getIcon() {
        return icon;
    }

    public void setIcon(@Nullable PhotoAttachmentRequestPayload icon) {
        this.icon = icon;
    }

    public ChatPatch title(@Nullable String title) {
        this.setTitle(title);
        return this;
    }

    /**
    * @return title
    **/
    @Nullable
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ChatPatch other = (ChatPatch) o;
        return Objects.equals(this.icon, other.icon) &&
            Objects.equals(this.title, other.title);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatPatch{"
            + " icon='" + icon + '\''
            + " title='" + title + '\''
            + '}';
    }
}
