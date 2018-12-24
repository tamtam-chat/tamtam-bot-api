package chat.tamtam.botapi.model;

/*-
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

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * Generic schema representing message attachment
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = PhotoAttachment.class, name = "image"),
  @JsonSubTypes.Type(value = VideoAttachment.class, name = "video"),
  @JsonSubTypes.Type(value = AudioAttachment.class, name = "audio"),
  @JsonSubTypes.Type(value = FileAttachment.class, name = "file"),
  @JsonSubTypes.Type(value = StickerAttachment.class, name = "sticker"),
  @JsonSubTypes.Type(value = ContactAttachment.class, name = "contact"),
  @JsonSubTypes.Type(value = InlineKeyboardAttachment.class, name = "inline_keyboard"),
})

public class Attachment implements TamTamSerializable {
    @JsonProperty("type")
    private String type;


    /**
    * @return type
    **/
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        Attachment other = (Attachment) o;
        return Objects.equals(this.type, other.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "Attachment{"
            + " type='" + type + '\''
            + '}';
    }
}

