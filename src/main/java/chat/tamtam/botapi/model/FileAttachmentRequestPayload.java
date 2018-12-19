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
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * FileAttachmentRequestPayload
 */
public class FileAttachmentRequestPayload implements TamTamSerializable {
    @JsonProperty("fileId")
    private final Long fileId;

    @JsonCreator
    public FileAttachmentRequestPayload(@JsonProperty("fileId") Long fileId) { 
        this.fileId = fileId;
    }

    /**
    * @return fileId
    **/
    public Long getFileId() {
        return fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        FileAttachmentRequestPayload other = (FileAttachmentRequestPayload) o;
        return Objects.equals(this.fileId, other.fileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId);
    }

    @Override
    public String toString() {
        return "FileAttachmentRequestPayload{"
            + " fileId='" + fileId + '\''
            + '}';
    }
}

