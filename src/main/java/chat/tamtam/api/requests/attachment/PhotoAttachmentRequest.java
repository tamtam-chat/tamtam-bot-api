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

import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class PhotoAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    public PhotoAttachmentRequest(String url) {
        this(new Payload());
        payload.sourceUrl = url;
    }

    @JsonCreator
    PhotoAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    @Nullable
    public String getPhotoToken() {
        if (payload.photos == null) {
            return null;
        }

        return payload.photos.values()
                .stream()
                .map(m -> m.get("token"))
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public String getSourceUrl() {
        return payload.sourceUrl;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    public static class Payload implements AttachmentRequestPayload {
        @JsonProperty("photos")
        @Nullable
        Map<String, Map<String, String>> photos;

        @Nullable
        @JsonProperty("url")
        String sourceUrl;
    }
}
