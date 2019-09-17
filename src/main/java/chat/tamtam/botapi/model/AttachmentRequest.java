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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;


/**
 * Request to attach some data to message
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true, defaultImpl = AttachmentRequest.class, include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value = PhotoAttachmentRequest.class, name = AttachmentRequest.IMAGE),
  @JsonSubTypes.Type(value = VideoAttachmentRequest.class, name = AttachmentRequest.VIDEO),
  @JsonSubTypes.Type(value = AudioAttachmentRequest.class, name = AttachmentRequest.AUDIO),
  @JsonSubTypes.Type(value = FileAttachmentRequest.class, name = AttachmentRequest.FILE),
  @JsonSubTypes.Type(value = StickerAttachmentRequest.class, name = AttachmentRequest.STICKER),
  @JsonSubTypes.Type(value = ContactAttachmentRequest.class, name = AttachmentRequest.CONTACT),
  @JsonSubTypes.Type(value = InlineKeyboardAttachmentRequest.class, name = AttachmentRequest.INLINE_KEYBOARD),
  @JsonSubTypes.Type(value = LocationAttachmentRequest.class, name = AttachmentRequest.LOCATION),
})
@KnownInstance(ofClass = AttachmentRequest.class, discriminator = "type")
public class AttachmentRequest implements TamTamSerializable {
    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
    public static final String FILE = "file";
    public static final String STICKER = "sticker";
    public static final String CONTACT = "contact";
    public static final String INLINE_KEYBOARD = "inline_keyboard";
    public static final String LOCATION = "location";
    public static final Set<String> TYPES = new HashSet<>(Arrays.asList(
        IMAGE, 
        VIDEO, 
        AUDIO, 
        FILE, 
        STICKER, 
        CONTACT, 
        INLINE_KEYBOARD, 
        LOCATION
    ));


    public void visit(Visitor visitor) {
        visitor.visitDefault(this);
    }

    @JsonProperty("type")
    public String getType() {
        throw new UnsupportedOperationException("Model has no concrete type.");
    }

    @Override
    public String toString() {
        return "AttachmentRequest{"
            + '}';
    }

    public interface Visitor {
        void visit(PhotoAttachmentRequest model);
        void visit(VideoAttachmentRequest model);
        void visit(AudioAttachmentRequest model);
        void visit(FileAttachmentRequest model);
        void visit(StickerAttachmentRequest model);
        void visit(ContactAttachmentRequest model);
        void visit(InlineKeyboardAttachmentRequest model);
        void visit(LocationAttachmentRequest model);
        void visitDefault(AttachmentRequest model);
    }
}
