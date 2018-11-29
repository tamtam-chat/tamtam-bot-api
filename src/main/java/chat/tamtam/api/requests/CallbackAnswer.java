package chat.tamtam.api.requests;

/*-
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

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.TamTamSerializable;
import chat.tamtam.api.objects.Recipient;

/**
 * @author alexandrchuprin
 */
public class CallbackAnswer implements TamTamSerializable {
    private static final String MESSAGE = "message";
    private static final String NOTIFICATION = "notification";
    protected static final String RECIPIENT = "recipient";

    private final Recipient recipient;
    private final String notification;
    private final NewMessage.MessageBody message;

    @JsonCreator
    public CallbackAnswer(@JsonProperty(RECIPIENT) Recipient recipient, @JsonProperty(NOTIFICATION) String notification,
                          @JsonProperty(MESSAGE) NewMessage.MessageBody message) {
        this.recipient = recipient;
        this.notification = notification;
        this.message = message;
    }

    @Nullable
    @JsonProperty(NOTIFICATION)
    public String getNotification() {
        return notification;
    }

    @Nullable
    @JsonProperty(MESSAGE)
    public NewMessage.MessageBody getMessage() {
        return message;
    }

    @Nullable
    @JsonProperty(RECIPIENT)
    public Recipient getRecipient() {
        return recipient;
    }
}
