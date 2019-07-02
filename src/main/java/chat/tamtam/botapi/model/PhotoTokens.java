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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.constraints.NotNull;


/**
 * This is information you will recieve as soon as an image uploaded
 */
public class PhotoTokens implements TamTamSerializable {

    @NotNull
    private final Map<String, PhotoToken> photos;

    @JsonCreator
    public PhotoTokens(@JsonProperty("photos") Map<String, PhotoToken> photos) { 
        this.photos = photos;
    }

    /**
    * @return photos
    **/
    @JsonProperty("photos")
    public Map<String, PhotoToken> getPhotos() {
        return photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        PhotoTokens other = (PhotoTokens) o;
        return Objects.equals(this.photos, other.photos);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhotoTokens{"
            + " photos='" + photos + '\''
            + '}';
    }
}
