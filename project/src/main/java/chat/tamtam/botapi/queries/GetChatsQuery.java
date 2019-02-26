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

package chat.tamtam.botapi.queries;

import chat.tamtam.botapi.client.TamTamClient;

import chat.tamtam.botapi.model.ChatList;

public class GetChatsQuery extends TamTamQuery<ChatList> { 
    private final QueryParam<Integer> count = new QueryParam<>("count", this);
    private final QueryParam<Long> marker = new QueryParam<>("marker", this);

    public GetChatsQuery(TamTamClient client) {
        super(client, "/chats", null, ChatList.class, Method.GET);
    }

    public GetChatsQuery count(Integer value) {
        this.count.setValue(value);
        return this;
    }
    public GetChatsQuery marker(Long value) {
        this.marker.setValue(value);
        return this;
    }
}
