package chat.tamtam.api.objects;

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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.TamTamSerializable;
import chat.tamtam.api.objects.attachment.Attachment;

/**
 * @author alexandrchuprin
 */
public class Message implements TamTamSerializable {
    private static final String SENDER = "sender";
    private static final String RECIPIENT = "recipient";
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";
    private static final String MID = "mid";
    private static final String SEQ = "seq";
    private static final String TEXT = "text";
    private static final String ATTACHMENTS = "attachments";
    private static final String REPLY_TO = "reply_to";

    private final User sender;
    private final Recipient recipient;
    private final TTMessagePayload message;
    private final long timestamp;

    public Message(User sender, Recipient recipient, long timestamp, String messageId, long seq, String text,
                   List<Attachment> attachments, String replyTo) {
        this(sender, recipient, new TTMessagePayload(messageId, seq, text, attachments, replyTo), timestamp);
    }

    @JsonCreator
    private Message(@JsonProperty(SENDER) User sender, @JsonProperty(RECIPIENT) Recipient recipient,
                    @JsonProperty(MESSAGE) TTMessagePayload message, @JsonProperty(TIMESTAMP) long timestamp) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.timestamp = timestamp;
    }

    @JsonProperty(SENDER)
    public User getSender() {
        return sender;
    }

    @JsonProperty(RECIPIENT)
    public Recipient getRecipient() {
        return recipient;
    }

    @JsonProperty(MESSAGE)
    protected TTMessagePayload getMessage() {
        return message;
    }

    @JsonProperty(TIMESTAMP)
    public long getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return message.text;
    }

    public List<Attachment> getAttachments() {
        return message.attachments;
    }

    public String getMessageId() {
        return message.messageId;
    }

    private static class TTMessagePayload implements TamTamSerializable {
        @JsonProperty(MID)
        private final String messageId;
        @JsonProperty(SEQ)
        private final long seq;
        @JsonProperty(TEXT)
        private final String text;
        @JsonProperty(ATTACHMENTS)
        private final List<Attachment> attachments;
        @JsonProperty(REPLY_TO)
        private final String replyTo;

        @JsonCreator
        private TTMessagePayload(@JsonProperty(MID) String messageId, @JsonProperty(SEQ) long seq,
                                 @JsonProperty(TEXT) String text,
                                 @JsonProperty(ATTACHMENTS) List<Attachment> attachments,
                                 @JsonProperty(REPLY_TO) String replyTo) {
            this.messageId = messageId;
            this.seq = seq;
            this.text = text;
            this.attachments = attachments;
            this.replyTo = replyTo;
        }
    }
}
