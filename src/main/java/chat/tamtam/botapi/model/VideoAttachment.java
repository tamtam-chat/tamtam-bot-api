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
 * VideoAttachment
 */
public class VideoAttachment extends Attachment implements TamTamSerializable {

    @NotNull
    private final @Valid MediaAttachmentPayload payload;
    @Nullable
    private @Valid PhotoAttachmentPayload thumbnail;
    @Nullable
    private @Valid Integer width;
    @Nullable
    private @Valid Integer height;
    @Nullable
    private @Valid Integer duration;

    @JsonCreator
    public VideoAttachment(@JsonProperty("payload") MediaAttachmentPayload payload) { 
        super();
        this.payload = payload;
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
    * @return payload
    **/
    @JsonProperty("payload")
    public MediaAttachmentPayload getPayload() {
        return payload;
    }

    public VideoAttachment thumbnail(@Nullable PhotoAttachmentPayload thumbnail) {
        this.setThumbnail(thumbnail);
        return this;
    }

    /**
    * Video thumbnail
    * @return thumbnail
    **/
    @Nullable
    @JsonProperty("thumbnail")
    public PhotoAttachmentPayload getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@Nullable PhotoAttachmentPayload thumbnail) {
        this.thumbnail = thumbnail;
    }

    public VideoAttachment width(@Nullable Integer width) {
        this.setWidth(width);
        return this;
    }

    /**
    * Video width
    * @return width
    **/
    @Nullable
    @JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    public void setWidth(@Nullable Integer width) {
        this.width = width;
    }

    public VideoAttachment height(@Nullable Integer height) {
        this.setHeight(height);
        return this;
    }

    /**
    * Video height
    * @return height
    **/
    @Nullable
    @JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(@Nullable Integer height) {
        this.height = height;
    }

    public VideoAttachment duration(@Nullable Integer duration) {
        this.setDuration(duration);
        return this;
    }

    /**
    * Video duration in seconds
    * @return duration
    **/
    @Nullable
    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(@Nullable Integer duration) {
        this.duration = duration;
    }

    @JsonProperty("type")
    @Override
    public String getType() {
        return Attachment.VIDEO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        VideoAttachment other = (VideoAttachment) o;
        return Objects.equals(this.payload, other.payload) &&
            Objects.equals(this.thumbnail, other.thumbnail) &&
            Objects.equals(this.width, other.width) &&
            Objects.equals(this.height, other.height) &&
            Objects.equals(this.duration, other.duration);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        result = 31 * result + (thumbnail != null ? thumbnail.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VideoAttachment{"+ super.toString()
            + " payload='" + payload + '\''
            + " thumbnail='" + thumbnail + '\''
            + " width='" + width + '\''
            + " height='" + height + '\''
            + " duration='" + duration + '\''
            + '}';
    }
}
