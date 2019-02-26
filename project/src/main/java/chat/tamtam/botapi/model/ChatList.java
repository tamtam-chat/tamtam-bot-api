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

import org.jetbrains.annotations.Nullable;

/**
 * ChatList
 */
public class ChatList implements TamTamSerializable {

    private final List<Chat> chats;
    private final Long marker;

    @JsonCreator
    public ChatList(@JsonProperty("chats") List<Chat> chats, @Nullable @JsonProperty("marker") Long marker) { 
        this.chats = chats;
        this.marker = marker;
    }

    /**
    * List of requested chats
    * @return chats
    **/
    @JsonProperty("chats")
    public List<Chat> getChats() {
        return chats;
    }

    /**
    * Reference to the next page of requested chats
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

        ChatList other = (ChatList) o;
        return Objects.equals(this.chats, other.chats) &&
            Objects.equals(this.marker, other.marker);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (chats != null ? chats.hashCode() : 0);
        result = 31 * result + (marker != null ? marker.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatList{"
            + " chats='" + chats + '\''
            + " marker='" + marker + '\''
            + '}';
    }
}
