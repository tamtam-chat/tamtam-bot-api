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
 * Server returns this if there was an exception to your request
 */
public class Error implements TamTamSerializable {

    private String error;
    private final String code;
    private final String message;

    @JsonCreator
    public Error(@JsonProperty("code") String code, @JsonProperty("message") String message) { 
        this.code = code;
        this.message = message;
    }

    /**
    * Error
    * @return error
    **/
    @JsonProperty("error")
    public String getError() {
        return error;
    }

    /**
    * Error code
    * @return code
    **/
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
    * Human-readable description
    * @return message
    **/
    @JsonProperty("message")
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
        return Objects.equals(this.error, other.error) &&
            Objects.equals(this.code, other.code) &&
            Objects.equals(this.message, other.message);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Error{"
            + " error='" + error + '\''
            + " code='" + code + '\''
            + " message='" + message + '\''
            + '}';
    }
}
