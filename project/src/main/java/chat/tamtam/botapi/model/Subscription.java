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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

/**
 * Schema to describe WebHook subscription
 */
public class Subscription implements TamTamSerializable {

    private final String url;
    private final Long time;
    private final Set<String> updateTypes;
    private final String version;

    @JsonCreator
    public Subscription(@JsonProperty("url") String url, @JsonProperty("time") Long time, @Nullable @JsonProperty("update_types") Set<String> updateTypes, @Nullable @JsonProperty("version") String version) { 
        this.url = url;
        this.time = time;
        this.updateTypes = updateTypes;
        this.version = version;
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

    /**
    * Update types bot subscribed for
    * @return updateTypes
    **/
    @Nullable
    @JsonProperty("update_types")
    public Set<String> getUpdateTypes() {
        return updateTypes;
    }

    /**
    * @return version
    **/
    @Nullable
    @JsonProperty("version")
    public String getVersion() {
        return version;
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
            Objects.equals(this.time, other.time) &&
            Objects.equals(this.updateTypes, other.updateTypes) &&
            Objects.equals(this.version, other.version);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (updateTypes != null ? updateTypes.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Subscription{"
            + " url='" + url + '\''
            + " time='" + time + '\''
            + " updateTypes='" + updateTypes + '\''
            + " version='" + version + '\''
            + '}';
    }
}
