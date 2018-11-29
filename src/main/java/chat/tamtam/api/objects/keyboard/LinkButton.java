package chat.tamtam.api.objects.keyboard;

/*-
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class LinkButton extends Button {
    protected static final String URL = "url";

    private final String url;

    public LinkButton(String text, String url) {
        this(text, Intent.DEFAULT, url);
    }

    public LinkButton(String text, Intent intent, String url) {
        super(Type.LINK, text, intent);
        this.url = url;
    }

    @JsonProperty(URL)
    public String getUrl() {
        return url;
    }

    @JsonCreator
    protected static LinkButton create(@JsonProperty(TEXT) String text,
                                       @JsonProperty(value = INTENT) Intent intent,
                                       @JsonProperty(URL) String url) {
        return new LinkButton(text, intent == null ? Intent.DEFAULT : intent, url);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LinkButton{");
        sb.append("url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkButton)) return false;

        LinkButton that = (LinkButton) o;

        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
