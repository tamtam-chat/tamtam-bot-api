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
 * Bot will get this update when chat has been created as soon as first user clicked chat button
 */
public class MessageChatCreatedUpdate extends Update implements TamTamSerializable {

    @NotNull
    private final @Valid Chat chat;
    @NotNull
    private final @Valid String messageId;
    @Nullable
    private @Valid String startPayload;

    @JsonCreator
    public MessageChatCreatedUpdate(@JsonProperty("chat") Chat chat, @JsonProperty("message_id") String messageId, @JsonProperty("timestamp") Long timestamp) { 
        super(timestamp);
        this.chat = chat;
        this.messageId = messageId;
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
    * Created chat
    * @return chat
    **/
    @JsonProperty("chat")
    public Chat getChat() {
        return chat;
    }

    /**
    * Message identifier where the button has been clicked
    * @return messageId
    **/
    @JsonProperty("message_id")
    public String getMessageId() {
        return messageId;
    }

    public MessageChatCreatedUpdate startPayload(@Nullable String startPayload) {
        this.setStartPayload(startPayload);
        return this;
    }

    /**
    * Payload from chat button
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

    @JsonProperty("update_type")
    @Override
    public String getType() {
        return Update.MESSAGE_CHAT_CREATED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        MessageChatCreatedUpdate other = (MessageChatCreatedUpdate) o;
        return Objects.equals(this.chat, other.chat) &&
            Objects.equals(this.messageId, other.messageId) &&
            Objects.equals(this.startPayload, other.startPayload) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (chat != null ? chat.hashCode() : 0);
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (startPayload != null ? startPayload.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageChatCreatedUpdate{"+ super.toString()
            + " chat='" + chat + '\''
            + " messageId='" + messageId + '\''
            + " startPayload='" + startPayload + '\''
            + '}';
    }
}
