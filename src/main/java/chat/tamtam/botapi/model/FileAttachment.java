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
import javax.validation.constraints.NotNull;


/**
 * FileAttachment
 */
public class FileAttachment extends Attachment implements TamTamSerializable {

    @NotNull
    private final MediaAttachmentPayload payload;
    @NotNull
    private final String filename;
    @NotNull
    private final Long size;

    @JsonCreator
    public FileAttachment(@JsonProperty("payload") MediaAttachmentPayload payload, @JsonProperty("filename") String filename, @JsonProperty("size") Long size) { 
        super();
        this.payload = payload;
        this.filename = filename;
        this.size = size;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    /**
    * @return payload
    **/
    @JsonProperty("payload")
    public MediaAttachmentPayload getPayload() {
        return payload;
    }

    /**
    * Uploaded file name
    * @return filename
    **/
    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    /**
    * File size in bytes
    * @return size
    **/
    @JsonProperty("size")
    public Long getSize() {
        return size;
    }

    @JsonProperty("type")
    @Override
    public String getType() {
        return Attachment.FILE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        FileAttachment other = (FileAttachment) o;
        return Objects.equals(this.payload, other.payload) &&
            Objects.equals(this.filename, other.filename) &&
            Objects.equals(this.size, other.size);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FileAttachment{"+ super.toString()
            + " payload='" + payload + '\''
            + " filename='" + filename + '\''
            + " size='" + size + '\''
            + '}';
    }
}
