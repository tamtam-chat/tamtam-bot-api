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
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * BotPatch
 */
public class BotPatch implements TamTamSerializable {

    @Nullable
    @Size(min = 1, max = 64)
    private @Valid String name;
    @Nullable
    @Size(min = 1, max = 16000)
    private @Valid String description;
    @Nullable
    @Size(max = 32)
    private List<@Valid BotCommand> commands;
    @Nullable
    private @Valid PhotoAttachmentRequestPayload photo;

    public BotPatch name(@Nullable String name) {
        this.setName(name);
        return this;
    }

    /**
    * Visible name of bot
    * @return name
    **/
    @Nullable
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public BotPatch description(@Nullable String description) {
        this.setDescription(description);
        return this;
    }

    /**
    * Bot description up to 16k characters long
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

    public BotPatch commands(@Nullable List<BotCommand> commands) {
        this.setCommands(commands);
        return this;
    }

    /**
    * Commands supported by bot. Pass empty list if you want to remove commands
    * @return commands
    **/
    @Nullable
    @JsonProperty("commands")
    public List<BotCommand> getCommands() {
        return commands;
    }

    public void setCommands(@Nullable List<BotCommand> commands) {
        this.commands = commands;
    }

    public BotPatch photo(@Nullable PhotoAttachmentRequestPayload photo) {
        this.setPhoto(photo);
        return this;
    }

    /**
    * Request to set bot photo
    * @return photo
    **/
    @Nullable
    @JsonProperty("photo")
    public PhotoAttachmentRequestPayload getPhoto() {
        return photo;
    }

    public void setPhoto(@Nullable PhotoAttachmentRequestPayload photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        BotPatch other = (BotPatch) o;
        return Objects.equals(this.name, other.name) &&
            Objects.equals(this.description, other.description) &&
            Objects.equals(this.commands, other.commands) &&
            Objects.equals(this.photo, other.photo);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (commands != null ? commands.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BotPatch{"
            + " name='" + name + '\''
            + " description='" + description + '\''
            + " commands='" + commands + '\''
            + " photo='" + photo + '\''
            + '}';
    }
}
