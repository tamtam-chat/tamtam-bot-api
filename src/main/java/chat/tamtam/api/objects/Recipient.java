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

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.TamTamSerializable;

/**
 * @author alexandrchuprin
 */
public class Recipient implements TamTamSerializable {
    private static final String CHAT_ID = "chat_id";
    private static final String USER_ID = "user_id";

    private final Long chatId;
    private final Long userId;

    @JsonCreator
    protected Recipient(@JsonProperty(CHAT_ID) Long chatId, @JsonProperty(USER_ID) Long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    public static Recipient chat(long chatId) {
        return new Recipient(chatId, null);
    }

    public static Recipient user(long userId) {
        return new Recipient(null, userId);
    }

    @Nullable
    @JsonProperty(CHAT_ID)
    public Long getChatId() {
        return chatId;
    }

    @Nullable
    @JsonProperty(USER_ID)
    public Long getUserId() {
        return userId;
    }
}
