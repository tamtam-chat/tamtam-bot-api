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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * Generic schema representing message attachment
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true, defaultImpl = Attachment.class)
@JsonSubTypes({
  @JsonSubTypes.Type(value = PhotoAttachment.class, name = "image"),
  @JsonSubTypes.Type(value = VideoAttachment.class, name = "video"),
  @JsonSubTypes.Type(value = AudioAttachment.class, name = "audio"),
  @JsonSubTypes.Type(value = FileAttachment.class, name = "file"),
  @JsonSubTypes.Type(value = StickerAttachment.class, name = "sticker"),
  @JsonSubTypes.Type(value = ContactAttachment.class, name = "contact"),
  @JsonSubTypes.Type(value = InlineKeyboardAttachment.class, name = "inline_keyboard"),
  @JsonSubTypes.Type(value = ShareAttachment.class, name = "share"),
})
public class Attachment implements TamTamSerializable {


    public void visit(Visitor visitor) {
        visitor.visitDefault(this);
    }

    @Override
    public String toString() {
        return "Attachment{"
            + '}';
    }

    public interface Visitor {
        void visit(PhotoAttachment model);
        void visit(VideoAttachment model);
        void visit(AudioAttachment model);
        void visit(FileAttachment model);
        void visit(StickerAttachment model);
        void visit(ContactAttachment model);
        void visit(InlineKeyboardAttachment model);
        void visit(ShareAttachment model);
        void visitDefault(Attachment model);
    }
}

