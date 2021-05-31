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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Represents link in text
 */
public class LinkMarkup extends MarkupElement implements TamTamSerializable {

    @NotNull
    @Size(min = 1, max = 2048)
    private final @Valid String url;

    @JsonCreator
    public LinkMarkup(@JsonProperty("url") String url, @JsonProperty("from") Integer from, @JsonProperty("length") Integer length) { 
        super(from, length);
        this.url = url;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T map(Mapper<T> mapper) {
        return mapper.map(this);
    }

    /**
    * Link&#39;s URL
    * @return url
    **/
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("type")
    @Override
    public String getType() {
        return MarkupElement.LINK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        LinkMarkup other = (LinkMarkup) o;
        return Objects.equals(this.url, other.url) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LinkMarkup{"+ super.toString()
            + " url='" + url + '\''
            + '}';
    }
}
