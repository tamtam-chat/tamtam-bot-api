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
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * ChatPatch
 */
public class ChatPatch implements TamTamSerializable {
    @JsonProperty("icon")
    private PhotoAttachmentRequest icon;

    @JsonProperty("title")
    private String title;


    /**
    * @return icon
    **/
    public PhotoAttachmentRequest getIcon() {
        return icon;
    }

    public ChatPatch title(String title) {
        this.title = title;
        return this;
    }

    /**
    * @return title
    **/
    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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
        return Objects.hash(icon, title);
    }

    @Override
    public String toString() {
        return "ChatPatch{"
            + " icon='" + icon + '\''
            + " title='" + title + '\''
            + '}';
    }
}

