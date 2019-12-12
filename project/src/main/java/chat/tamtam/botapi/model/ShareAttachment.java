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

import org.jetbrains.annotations.Nullable;

/**
 * ShareAttachment
 */
public class ShareAttachment extends Attachment implements TamTamSerializable {

    @NotNull
    private final @Valid ShareAttachmentPayload payload;
    @Nullable
    private @Valid String title;
    @Nullable
    private @Valid String description;
    @Nullable
    private @Valid String imageUrl;

    @JsonCreator
    public ShareAttachment(@JsonProperty("payload") ShareAttachmentPayload payload) { 
        super();
        this.payload = payload;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    /**
    * @return payload
    **/
    @JsonProperty("payload")
    public ShareAttachmentPayload getPayload() {
        return payload;
    }

    public ShareAttachment title(@Nullable String title) {
        this.setTitle(title);
        return this;
    }

    /**
    * Link preview title
    * @return title
    **/
    @Nullable
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public ShareAttachment description(@Nullable String description) {
        this.setDescription(description);
        return this;
    }

    /**
    * Link preview description
    * @return description
    **/
    @Nullable
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public ShareAttachment imageUrl(@Nullable String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    /**
    * Link preview image
    * @return imageUrl
    **/
    @Nullable
    @JsonProperty("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("type")
    @Override
    public String getType() {
        return Attachment.SHARE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ShareAttachment other = (ShareAttachment) o;
        return Objects.equals(this.payload, other.payload) &&
            Objects.equals(this.title, other.title) &&
            Objects.equals(this.description, other.description) &&
            Objects.equals(this.imageUrl, other.imageUrl);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShareAttachment{"+ super.toString()
            + " payload='" + payload + '\''
            + " title='" + title + '\''
            + " description='" + description + '\''
            + " imageUrl='" + imageUrl + '\''
            + '}';
    }
}
