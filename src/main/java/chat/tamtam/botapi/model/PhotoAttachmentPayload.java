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

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * PhotoAttachmentPayload
 */
public class PhotoAttachmentPayload implements TamTamSerializable {
    @JsonProperty("photo_id")
    private final Long photoId;

    @JsonProperty("url")
    private final String url;

    @JsonCreator
    public PhotoAttachmentPayload(@JsonProperty("photo_id") Long photoId, @JsonProperty("url") String url) { 
        this.photoId = photoId;
        this.url = url;
    }

    /**
    * Unique identifier of this image
    * @return photoId
    **/
    public Long getPhotoId() {
        return photoId;
    }

    /**
    * Image URL
    * @return url
    **/
    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        PhotoAttachmentPayload other = (PhotoAttachmentPayload) o;
        return Objects.equals(this.photoId, other.photoId) &&
            Objects.equals(this.url, other.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoId, url);
    }

    @Override
    public String toString() {
        return "PhotoAttachmentPayload{"
            + " photoId='" + photoId + '\''
            + " url='" + url + '\''
            + '}';
    }
}

