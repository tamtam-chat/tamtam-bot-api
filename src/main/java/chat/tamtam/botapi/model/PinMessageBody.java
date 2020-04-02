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
 * PinMessageBody
 */
public class PinMessageBody implements TamTamSerializable {

    @NotNull
    private final @Valid String messageId;
    @Nullable
    private @Valid Boolean notify;

    @JsonCreator
    public PinMessageBody(@JsonProperty("message_id") String messageId) { 
        this.messageId = messageId;
    }

    /**
    * Identifier of message to be pinned in chat
    * @return messageId
    **/
    @JsonProperty("message_id")
    public String getMessageId() {
        return messageId;
    }

    public PinMessageBody notify(@Nullable Boolean notify) {
        this.setNotify(notify);
        return this;
    }

    /**
    * If &#x60;true&#x60;, participants will be notified with system message in chat/channel
    * @return notify
    **/
    @Nullable
    @JsonProperty("notify")
    public Boolean isNotify() {
        return notify;
    }

    public void setNotify(@Nullable Boolean notify) {
        this.notify = notify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        PinMessageBody other = (PinMessageBody) o;
        return Objects.equals(this.messageId, other.messageId) &&
            Objects.equals(this.notify, other.notify);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (notify != null ? notify.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PinMessageBody{"
            + " messageId='" + messageId + '\''
            + " notify='" + notify + '\''
            + '}';
    }
}
