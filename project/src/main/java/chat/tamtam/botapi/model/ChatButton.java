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
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * Button that creates new chat as soon as the first user clicked on it. Bot will be added to chat participants as administrator. Message author will be owner of the chat.
 */
public class ChatButton extends Button implements TamTamSerializable {

    @Nullable
    @Size(max = 200)
    private @Valid String chatTitle;
    @Nullable
    @Size(max = 400)
    private @Valid String chatDescription;
    @Nullable
    @Size(max = 512)
    private @Valid String startPayload;
    @Nullable
    private @Valid Integer uuid;

    @JsonCreator
    public ChatButton(@JsonProperty("text") String text) { 
        super(text);
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    public ChatButton chatTitle(@Nullable String chatTitle) {
        this.setChatTitle(chatTitle);
        return this;
    }

    /**
    * Title of chat to be created
    * @return chatTitle
    **/
    @Nullable
    @JsonProperty("chat_title")
    public String getChatTitle() {
        return chatTitle;
    }

    public void setChatTitle(@Nullable String chatTitle) {
        this.chatTitle = chatTitle;
    }

    public ChatButton chatDescription(@Nullable String chatDescription) {
        this.setChatDescription(chatDescription);
        return this;
    }

    /**
    * Chat description
    * @return chatDescription
    **/
    @Nullable
    @JsonProperty("chat_description")
    public String getChatDescription() {
        return chatDescription;
    }

    public void setChatDescription(@Nullable String chatDescription) {
        this.chatDescription = chatDescription;
    }

    public ChatButton startPayload(@Nullable String startPayload) {
        this.setStartPayload(startPayload);
        return this;
    }

    /**
    * Start payload will be sent to bot as soon as chat created
    * @return startPayload
    **/
    @Nullable
    @JsonProperty("start_payload")
    public String getStartPayload() {
        return startPayload;
    }

    public void setStartPayload(@Nullable String startPayload) {
        this.startPayload = startPayload;
    }

    public ChatButton uuid(@Nullable Integer uuid) {
        this.setUuid(uuid);
        return this;
    }

    /**
    * Unique button identifier across all chat buttons in keyboard. If &#x60;uuid&#x60; changed, new chat will be created on the next click. Server will generate it at the time when button initially posted. Reuse it when you edit the message.&#39;
    * @return uuid
    **/
    @Nullable
    @JsonProperty("uuid")
    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(@Nullable Integer uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("type")
    @Override
    public String getType() {
        return Button.CHAT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ChatButton other = (ChatButton) o;
        return Objects.equals(this.chatTitle, other.chatTitle) &&
            Objects.equals(this.chatDescription, other.chatDescription) &&
            Objects.equals(this.startPayload, other.startPayload) &&
            Objects.equals(this.uuid, other.uuid) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (chatTitle != null ? chatTitle.hashCode() : 0);
        result = 31 * result + (chatDescription != null ? chatDescription.hashCode() : 0);
        result = 31 * result + (startPayload != null ? startPayload.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatButton{"+ super.toString()
            + " chatTitle='" + chatTitle + '\''
            + " chatDescription='" + chatDescription + '\''
            + " startPayload='" + startPayload + '\''
            + " uuid='" + uuid + '\''
            + '}';
    }
}
