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


import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Different actions to send to chat members
 */
public enum SenderAction implements TamTamEnum {
    
    TYPING_ON("typing_on"),
    TYPING_OFF("typing_off"),
    SENDING_PHOTO("sending_photo"),
    SENDING_VIDEO("sending_video"),
    SENDING_AUDIO("sending_audio"),
    MARK_SEEN("mark_seen");

    private String value;

    SenderAction(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static SenderAction create(String text) {
        return TamTamEnum.create(SenderAction.class, text);
    }
}

