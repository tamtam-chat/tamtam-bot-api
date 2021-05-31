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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * BotInfo
 */
public class BotInfo extends UserWithPhoto implements TamTamSerializable {

    @Nullable
    @Size(max = 32)
    private List<@Valid BotCommand> commands;

    @JsonCreator
    public BotInfo(@JsonProperty("user_id") Long userId, @JsonProperty("name") String name, @Nullable @JsonProperty("username") String username, @JsonProperty("is_bot") Boolean isBot, @JsonProperty("last_activity_time") Long lastActivityTime) { 
        super(userId, name, username, isBot, lastActivityTime);
    }

    public BotInfo commands(@Nullable List<BotCommand> commands) {
        this.setCommands(commands);
        return this;
    }

    /**
    * Commands supported by bot
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        BotInfo other = (BotInfo) o;
        return Objects.equals(this.commands, other.commands) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (commands != null ? commands.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BotInfo{"+ super.toString()
            + " commands='" + commands + '\''
            + '}';
    }
}
