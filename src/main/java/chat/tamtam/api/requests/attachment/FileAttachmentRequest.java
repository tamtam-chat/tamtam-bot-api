package chat.tamtam.api.requests.attachment;

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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class FileAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    public FileAttachmentRequest(long fileId) {
        this(new Payload(fileId));
    }

    @JsonCreator
    FileAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    public long getFileId() {
        return payload.fileId;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    public static class Payload implements AttachmentRequestPayload {
        protected static final String FILE_ID = "fileId";

        @JsonProperty(FILE_ID)
        private final long fileId;

        @JsonCreator
        public Payload(@JsonProperty(FILE_ID) long fileId) {
            this.fileId = fileId;
        }
    }
}
