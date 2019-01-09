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
 * Request to set up WebHook subscription
 */
public class SubscriptionRequestBody implements TamTamSerializable {
    @JsonProperty("url")
    private final String url;

    @JsonProperty("filter")
    private String filter;

    @JsonCreator
    public SubscriptionRequestBody(@JsonProperty("url") String url) { 
        this.url = url;
    }

    /**
    * URL of HTTP(S)-endpoint of your bot
    * @return url
    **/
    public String getUrl() {
        return url;
    }

    public SubscriptionRequestBody filter(String filter) {
        this.filter = filter;
        return this;
    }

    /**
    * @return filter
    **/
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
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
            Objects.equals(this.filter, other.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, filter);
    }

    @Override
    public String toString() {
        return "SubscriptionRequestBody{"
            + " url='" + url + '\''
            + " filter='" + filter + '\''
            + '}';
    }
}

