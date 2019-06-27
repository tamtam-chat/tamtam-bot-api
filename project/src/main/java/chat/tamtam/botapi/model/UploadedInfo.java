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
 * This is information you will recieve as soon as audio/video is uploaded
 */
public class UploadedInfo implements TamTamSerializable {

    private final Long id;
    private String token;

    @JsonCreator
    public UploadedInfo(@JsonProperty("id") Long id) { 
        this.id = id;
    }

    /**
    * Uploaded file unique identifier
    * @return id
    **/
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public UploadedInfo token(String token) {
        this.setToken(token);
        return this;
    }

    /**
    * Token is **required** in case you are trying to attach a media uploaded by another user
    * @return token
    **/
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        UploadedInfo other = (UploadedInfo) o;
        return Objects.equals(this.id, other.id) &&
            Objects.equals(this.token, other.token);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UploadedInfo{"
            + " id='" + id + '\''
            + " token='" + token + '\''
            + '}';
    }
}
