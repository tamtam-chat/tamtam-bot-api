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
public class AudioAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    public AudioAttachmentRequest(long audioId) {
        this(new Payload());
        payload.audioId = audioId;
    }

    @JsonCreator
    AudioAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    public long getAudioId() {
        return payload.audioId;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    static class Payload implements AttachmentRequestPayload {
        @JsonProperty("id")
        private long audioId;
    }
}
