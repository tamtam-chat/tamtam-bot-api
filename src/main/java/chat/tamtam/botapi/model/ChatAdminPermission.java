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
 * Chat admin permissions
 */
public enum ChatAdminPermission implements TamTamEnum {
    
    READ_ALL_MESSAGES("read_all_messages"),
    ADD_REMOVE_MEMBERS("add_remove_members"),
    ADD_ADMINS("add_admins"),
    CHANGE_CHAT_INFO("change_chat_info"),
    PIN_MESSAGE("pin_message"),
    WRITE("write");

    private String value;

    ChatAdminPermission(String value) {
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
    public static ChatAdminPermission create(String text) {
        return TamTamEnum.create(ChatAdminPermission.class, text);
    }
}

