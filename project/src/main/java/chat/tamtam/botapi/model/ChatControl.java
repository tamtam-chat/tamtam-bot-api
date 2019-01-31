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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Part of NewMessage if your want to control your chat
 */
public class ChatControl implements TamTamSerializable {

    private String title;
    private PhotoAttachmentRequest icon;
    private String leave;
    private List<ChatMember> addMembers;
    private ChatMember removeMember;

    public ChatControl title(String title) {
        this.setTitle(title);
        return this;
    }

    /**
    * Fill this if you want to change chat title
    * @return title
    **/
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ChatControl icon(PhotoAttachmentRequest icon) {
        this.setIcon(icon);
        return this;
    }

    /**
    * Fill this if you want to change chat icon
    * @return icon
    **/
    @JsonProperty("icon")
    public PhotoAttachmentRequest getIcon() {
        return icon;
    }

    public void setIcon(PhotoAttachmentRequest icon) {
        this.icon = icon;
    }

    public ChatControl leave(String leave) {
        this.setLeave(leave);
        return this;
    }

    /**
    * Fill this if you want to leave chat
    * @return leave
    **/
    @JsonProperty("leave")
    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public ChatControl addMembers(List<ChatMember> addMembers) {
        this.setAddMembers(addMembers);
        return this;
    }

    public ChatControl addAddMembersItem(ChatMember addMembersItem) {
        if (this.addMembers == null) {
            this.addMembers = new ArrayList<ChatMember>();
        }

        this.addMembers.add(addMembersItem);
        return this;
    }

    /**
    * Fill this if you want to add members to chat. Admin permissions required
    * @return addMembers
    **/
    @JsonProperty("add_members")
    public List<ChatMember> getAddMembers() {
        return addMembers;
    }

    public void setAddMembers(List<ChatMember> addMembers) {
        this.addMembers = addMembers;
    }

    public ChatControl removeMember(ChatMember removeMember) {
        this.setRemoveMember(removeMember);
        return this;
    }

    /**
    * Fill this if you want to remove members from chat. Admin permissions required
    * @return removeMember
    **/
    @JsonProperty("remove_member")
    public ChatMember getRemoveMember() {
        return removeMember;
    }

    public void setRemoveMember(ChatMember removeMember) {
        this.removeMember = removeMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ChatControl other = (ChatControl) o;
        return Objects.equals(this.title, other.title) &&
            Objects.equals(this.icon, other.icon) &&
            Objects.equals(this.leave, other.leave) &&
            Objects.equals(this.addMembers, other.addMembers) &&
            Objects.equals(this.removeMember, other.removeMember);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (leave != null ? leave.hashCode() : 0);
        result = 31 * result + (addMembers != null ? addMembers.hashCode() : 0);
        result = 31 * result + (removeMember != null ? removeMember.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatControl{"
            + " title='" + title + '\''
            + " icon='" + icon + '\''
            + " leave='" + leave + '\''
            + " addMembers='" + addMembers + '\''
            + " removeMember='" + removeMember + '\''
            + '}';
    }
}

