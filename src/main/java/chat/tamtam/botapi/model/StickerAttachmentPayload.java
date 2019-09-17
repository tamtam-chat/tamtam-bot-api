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


/**
 * StickerAttachmentPayload
 */
public class StickerAttachmentPayload extends AttachmentPayload implements TamTamSerializable {

    @NotNull
    private final @Valid String code;

    @JsonCreator
    public StickerAttachmentPayload(@JsonProperty("code") String code, @JsonProperty("url") String url) { 
        super(url);
        this.code = code;
    }

    /**
    * Sticker identifier
    * @return code
    **/
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        StickerAttachmentPayload other = (StickerAttachmentPayload) o;
        return Objects.equals(this.code, other.code) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StickerAttachmentPayload{"+ super.toString()
            + " code='" + code + '\''
            + '}';
    }
}
