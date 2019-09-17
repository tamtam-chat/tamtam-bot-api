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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Request to set up WebHook subscription
 */
public class SubscriptionRequestBody implements TamTamSerializable {

    @NotNull
    private final @Valid String url;
    private Set<@Valid String> updateTypes;
    private @Valid String version;

    @JsonCreator
    public SubscriptionRequestBody(@JsonProperty("url") String url) { 
        this.url = url;
    }

    /**
    * URL of HTTP(S)-endpoint of your bot. Must starts with http(s)://
    * @return url
    **/
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    public SubscriptionRequestBody updateTypes(Set<String> updateTypes) {
        this.setUpdateTypes(updateTypes);
        return this;
    }

    /**
    * List of update types your bot want to receive. See &#x60;Update&#x60; object for a complete list of types
    * @return updateTypes
    **/
    @JsonProperty("update_types")
    public Set<String> getUpdateTypes() {
        return updateTypes;
    }

    public void setUpdateTypes(Set<String> updateTypes) {
        this.updateTypes = updateTypes;
    }

    public SubscriptionRequestBody version(String version) {
        this.setVersion(version);
        return this;
    }

    /**
    * Version of API. Affects model representation
    * @return version
    **/
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        SubscriptionRequestBody other = (SubscriptionRequestBody) o;
        return Objects.equals(this.url, other.url) &&
            Objects.equals(this.updateTypes, other.updateTypes) &&
            Objects.equals(this.version, other.version);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (updateTypes != null ? updateTypes.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SubscriptionRequestBody{"
            + " url='" + url + '\''
            + " updateTypes='" + updateTypes + '\''
            + " version='" + version + '\''
            + '}';
    }
}
