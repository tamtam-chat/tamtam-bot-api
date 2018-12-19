package chat.tamtam.botapi.model;

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

import java.util.Objects;
import java.util.Arrays;
import chat.tamtam.botapi.model.PhotoToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * This is information you will recieve as soon as an image uploaded
 */
public class PhotoTokens implements TamTamSerializable {
    @JsonProperty("photos")
    private final Map<String, PhotoToken> photos;

    @JsonCreator
    public PhotoTokens(@JsonProperty("photos") Map<String, PhotoToken> photos) { 
        this.photos = photos;
    }

    /**
    * @return photos
    **/
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
        return Objects.hash(photos);
    }

    @Override
    public String toString() {
        return "PhotoTokens{"
            + " photos='" + photos + '\''
            + '}';
    }
}

