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
import javax.validation.constraints.NotNull;


/**
 * This is information you will recieve as soon as audio/video is uploaded
 */
public class UploadedInfo implements TamTamSerializable {

    @NotNull
    private final String token;

    @JsonCreator
    public UploadedInfo(@JsonProperty("token") String token) { 
        this.token = token;
    }

    /**
    * Token is unique uploaded media identfier
    * @return token
    **/
    @JsonProperty("token")
    public String getToken() {
        return token;
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
        return Objects.equals(this.token, other.token);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UploadedInfo{"
            + " token='" + token + '\''
            + '}';
    }
}
