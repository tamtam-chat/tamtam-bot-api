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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * Request to attach image. All fields are mutually exclusive
 */
public class PhotoAttachmentRequestPayload implements TamTamSerializable {

    @Nullable
    @Size(min = 1)
    private String url;
    @Nullable
    private String token;
    @Nullable
    private Map<String, PhotoToken> photos;

    public PhotoAttachmentRequestPayload url(@Nullable String url) {
        this.setUrl(url);
        return this;
    }

    /**
    * Any external image URL you want to attach
    * @return url
    **/
    @Nullable
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    public PhotoAttachmentRequestPayload token(@Nullable String token) {
        this.setToken(token);
        return this;
    }

    /**
    * Token of any existing attachment
    * @return token
    **/
    @Nullable
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(@Nullable String token) {
        this.token = token;
    }

    public PhotoAttachmentRequestPayload photos(@Nullable Map<String, PhotoToken> photos) {
        this.setPhotos(photos);
        return this;
    }

    public PhotoAttachmentRequestPayload putPhotosItem(String key, PhotoToken photosItem) {
        if (this.photos == null) {
            this.photos = new HashMap<String, PhotoToken>();
        }

        this.photos.put(key, photosItem);
        return this;
    }

    /**
    * Tokens were obtained after uploading images
    * @return photos
    **/
    @Nullable
    @JsonProperty("photos")
    public Map<String, PhotoToken> getPhotos() {
        return photos;
    }

    public void setPhotos(@Nullable Map<String, PhotoToken> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        PhotoAttachmentRequestPayload other = (PhotoAttachmentRequestPayload) o;
        return Objects.equals(this.url, other.url) &&
            Objects.equals(this.token, other.token) &&
            Objects.equals(this.photos, other.photos);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhotoAttachmentRequestPayload{"
            + " url='" + url + '\''
            + " token='" + token + '\''
            + " photos='" + photos + '\''
            + '}';
    }
}
