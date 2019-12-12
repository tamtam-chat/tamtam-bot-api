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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * Payload of ShareAttachmentRequest
 */
public class ShareAttachmentPayload implements TamTamSerializable {

    @Nullable
    @Size(min = 1)
    private @Valid String url;
    @Nullable
    private @Valid String token;

    public ShareAttachmentPayload url(@Nullable String url) {
        this.setUrl(url);
        return this;
    }

    /**
    * URL attached to message as media preview
    * @return url
    **/
    @Nullable
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    public ShareAttachmentPayload token(@Nullable String token) {
        this.setToken(token);
        return this;
    }

    /**
    * Attachment token
    * @return token
    **/
    @Nullable
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(@Nullable String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ShareAttachmentPayload other = (ShareAttachmentPayload) o;
        return Objects.equals(this.url, other.url) &&
            Objects.equals(this.token, other.token);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShareAttachmentPayload{"
            + " url='" + url + '\''
            + " token='" + token + '\''
            + '}';
    }
}
