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
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * BotCommand
 */
public class BotCommand implements TamTamSerializable {

    @Size(min = 1, max = 64)
    private final String name;
    @Nullable
    @Size(min = 1, max = 128)
    private String description;

    @JsonCreator
    public BotCommand(@JsonProperty("name") String name) { 
        this.name = name;
    }

    /**
    * Command name
    * @return name
    **/
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public BotCommand description(@Nullable String description) {
        this.setDescription(description);
        return this;
    }

    /**
    * Optional command description
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        BotCommand other = (BotCommand) o;
        return Objects.equals(this.name, other.name) &&
            Objects.equals(this.description, other.description);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BotCommand{"
            + " name='" + name + '\''
            + " description='" + description + '\''
            + '}';
    }
}
