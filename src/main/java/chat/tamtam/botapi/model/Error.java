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
 * Server returns this if there was an exception to your request
 */
public class Error implements TamTamSerializable {
    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonCreator
    public Error(@JsonProperty("code") String code, @JsonProperty("message") String message) { 
        this.code = code;
        this.message = message;
    }

    /**
    * Error code
    * @return code
    **/
    public String getCode() {
        return code;
    }

    /**
    * Human-readable description
    * @return message
    **/
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        Error other = (Error) o;
        return Objects.equals(this.code, other.code) &&
            Objects.equals(this.message, other.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    @Override
    public String toString() {
        return "Error{"
            + " code='" + code + '\''
            + " message='" + message + '\''
            + '}';
    }
}

