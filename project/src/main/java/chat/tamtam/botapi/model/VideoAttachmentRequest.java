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
 * Request to attach video to message
 */
public class VideoAttachmentRequest extends AttachmentRequest implements TamTamSerializable {

    private final UploadedInfo payload;

    @JsonCreator
    public VideoAttachmentRequest(@JsonProperty("payload") UploadedInfo payload) { 
        super();
        this.payload = payload;
    }

    /**
    * @return payload
    **/
    @JsonProperty("payload")
    public UploadedInfo getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        VideoAttachmentRequest other = (VideoAttachmentRequest) o;
        return Objects.equals(this.payload, other.payload);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VideoAttachmentRequest{"+ super.toString()
            + " payload='" + payload + '\''
            + '}';
    }
}

