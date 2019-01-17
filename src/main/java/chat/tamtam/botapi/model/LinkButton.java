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
import chat.tamtam.botapi.model.Button;
import chat.tamtam.botapi.model.Intent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * After pressing this type of button user follows the link it contains
 */
public class LinkButton extends Button implements TamTamSerializable {
  
    private final String url;

    @JsonCreator
    public LinkButton(@JsonProperty("url") String url, @JsonProperty("text") String text, @Nullable @JsonProperty("intent") Intent intent) { 
        super(text, intent);
        this.url = url;
    }

    /**
    * @return url
    **/
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        LinkButton other = (LinkButton) o;
        return Objects.equals(this.url, other.url) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, super.hashCode());
    }

    @Override
    public String toString() {
        return "LinkButton{"+ super.toString()
            + " url='" + url + '\''
            + '}';
    }
}

