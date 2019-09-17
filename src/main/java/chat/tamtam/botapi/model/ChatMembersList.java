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

import org.jetbrains.annotations.Nullable;

/**
 * ChatMembersList
 */
public class ChatMembersList implements TamTamSerializable {

    @NotNull
    private final List<@Valid ChatMember> members;
    @Nullable
    private final @Valid Long marker;

    @JsonCreator
    public ChatMembersList(@JsonProperty("members") List<ChatMember> members, @Nullable @JsonProperty("marker") Long marker) { 
        this.members = members;
        this.marker = marker;
    }

    /**
    * Participants in chat with time of last activity. Visible only for chat admins
    * @return members
    **/
    @JsonProperty("members")
    public List<ChatMember> getMembers() {
        return members;
    }

    /**
    * Pointer to the next data page
    * @return marker
    **/
    @Nullable
    @JsonProperty("marker")
    public Long getMarker() {
        return marker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ChatMembersList other = (ChatMembersList) o;
        return Objects.equals(this.members, other.members) &&
            Objects.equals(this.marker, other.marker);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (members != null ? members.hashCode() : 0);
        result = 31 * result + (marker != null ? marker.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatMembersList{"
            + " members='" + members + '\''
            + " marker='" + marker + '\''
            + '}';
    }
}
