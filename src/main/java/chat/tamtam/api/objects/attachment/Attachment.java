package chat.tamtam.api.objects.attachment;

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

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import chat.tamtam.api.TamTamSerializable;

/**
 * @author alexandrchuprin
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImageAttachment.class, name = "image"),
        @JsonSubTypes.Type(value = VideoAttachment.class, name = "video"),
        @JsonSubTypes.Type(value = AudioAttachment.class, name = "audio"),
        @JsonSubTypes.Type(value = ContactAttachment.class, name = "contact"),
        @JsonSubTypes.Type(value = FileAttachment.class, name = "file"),
        @JsonSubTypes.Type(value = ShareAttachment.class, name = "share"),
        @JsonSubTypes.Type(value = StickerAttachment.class, name = "sticker"),
        @JsonSubTypes.Type(value = InlineKeyboardAttachment.class, name = "inline_keyboard"),
})
public abstract class Attachment implements Serializable {
    protected static final String PAYLOAD = "payload";
    protected static final String URL = "url";

    private final AttachmentPayload payload;

    protected Attachment(AttachmentPayload payload) {
        this.payload = payload;
    }

    @JsonProperty(PAYLOAD)
    protected AttachmentPayload getPayload() {
        return payload;
    }

    protected interface AttachmentPayload extends TamTamSerializable {
    }
}
