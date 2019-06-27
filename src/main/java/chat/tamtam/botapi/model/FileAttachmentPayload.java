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


/**
 * FileAttachmentPayload
 */
public class FileAttachmentPayload extends AttachmentPayload implements TamTamSerializable {

    private final Long fileId;
    private final String token;

    @JsonCreator
    public FileAttachmentPayload(@JsonProperty("fileId") Long fileId, @JsonProperty("token") String token, @JsonProperty("url") String url) { 
        super(url);
        this.fileId = fileId;
        this.token = token;
    }

    /**
    * Uploaded file unique identifier
    * @return fileId
    **/
    @JsonProperty("fileId")
    public Long getFileId() {
        return fileId;
    }

    /**
    * Use &#x60;token&#x60; along with &#x60;fileId&#x60; in case when you are trying to reuse the same attachment in other message
    * @return token
    **/
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        FileAttachmentPayload other = (FileAttachmentPayload) o;
        return Objects.equals(this.fileId, other.fileId) &&
            Objects.equals(this.token, other.token) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FileAttachmentPayload{"+ super.toString()
            + " fileId='" + fileId + '\''
            + " token='" + token + '\''
            + '}';
    }
}
