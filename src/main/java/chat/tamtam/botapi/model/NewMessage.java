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

import org.jetbrains.annotations.Nullable;

/**
 * New message. Could be &#x60;control&#x60;, &#x60;sender_action&#x60; or real &#x60;message&#x60;
 */
public class NewMessage implements TamTamSerializable {

    private final Recipient recipient;
    private SenderAction senderAction;
    private ChatControl chatControl;
    private NewMessageBody message;

    @JsonCreator
    public NewMessage(@JsonProperty("recipient") Recipient recipient) { 
        this.recipient = recipient;
    }

    /**
    * Message recipient. User or chat
    * @return recipient
    **/
    @JsonProperty("recipient")
    public Recipient getRecipient() {
        return recipient;
    }

    public NewMessage senderAction(@Nullable SenderAction senderAction) {
        this.setSenderAction(senderAction);
        return this;
    }

    /**
    * Action to send to chat. For example: &#x60;typing&#x60; or &#x60;sending photo&#x60;. See &#x60;SenderAction&#x60; for full information
    * @return senderAction
    **/
    @Nullable
    @JsonProperty("sender_action")
    public SenderAction getSenderAction() {
        return senderAction;
    }

    public void setSenderAction(@Nullable SenderAction senderAction) {
        this.senderAction = senderAction;
    }

    public NewMessage chatControl(@Nullable ChatControl chatControl) {
        this.setChatControl(chatControl);
        return this;
    }

    /**
    * Control message to chat. For example: to set icon or title of chat. See &#x60;ChatControl&#x60; description for full information
    * @return chatControl
    **/
    @Nullable
    @JsonProperty("chat_control")
    public ChatControl getChatControl() {
        return chatControl;
    }

    public void setChatControl(@Nullable ChatControl chatControl) {
        this.chatControl = chatControl;
    }

    public NewMessage message(@Nullable NewMessageBody message) {
        this.setMessage(message);
        return this;
    }

    /**
    * Real message to send to chat
    * @return message
    **/
    @Nullable
    @JsonProperty("message")
    public NewMessageBody getMessage() {
        return message;
    }

    public void setMessage(@Nullable NewMessageBody message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        NewMessage other = (NewMessage) o;
        return Objects.equals(this.recipient, other.recipient) &&
            Objects.equals(this.senderAction, other.senderAction) &&
            Objects.equals(this.chatControl, other.chatControl) &&
            Objects.equals(this.message, other.message);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (senderAction != null ? senderAction.hashCode() : 0);
        result = 31 * result + (chatControl != null ? chatControl.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewMessage{"
            + " recipient='" + recipient + '\''
            + " senderAction='" + senderAction + '\''
            + " chatControl='" + chatControl + '\''
            + " message='" + message + '\''
            + '}';
    }
}

