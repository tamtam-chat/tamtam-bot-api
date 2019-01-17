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
 * Schema to describe WebHook subscription
 */
public class Subscription implements TamTamSerializable {
  
    private final String url;
    private final Long time;

    @JsonCreator
    public Subscription(@JsonProperty("url") String url, @JsonProperty("time") Long time) { 
        this.url = url;
        this.time = time;
    }

    /**
    * WebHook URL
    * @return url
    **/
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
    * Unix-time when subscription was created
    * @return time
    **/
    @JsonProperty("time")
    public Long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        Subscription other = (Subscription) o;
        return Objects.equals(this.url, other.url) &&
            Objects.equals(this.time, other.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, time);
    }

    @Override
    public String toString() {
        return "Subscription{"
            + " url='" + url + '\''
            + " time='" + time + '\''
            + '}';
    }
}

