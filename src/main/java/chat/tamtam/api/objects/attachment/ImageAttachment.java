package chat.tamtam.api.objects.attachment;

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
public class ImageAttachment extends Attachment {
    private static final String PHOTO_ID = "photo_id";

    private final long photoId;
    private final String url;

    public ImageAttachment(long photoId, String url) {
        super(new Payload(photoId, url));
        this.photoId = photoId;
        this.url = url;
    }

    @JsonCreator
    protected ImageAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.photoId = payload.photoId;
        this.url = payload.url;
    }

    public String getURL() {
        return url;
    }

    public long getPhotoId() {
        return photoId;
    }

    private static class Payload implements AttachmentPayload {
        @JsonProperty(PHOTO_ID)
        private final long photoId;
        @JsonProperty(URL)
        private final String url;

        @JsonCreator
        Payload(@JsonProperty(PHOTO_ID) long photoId, @JsonProperty(URL) String url) {
            this.photoId = photoId;
            this.url = url;
        }
    }
}
