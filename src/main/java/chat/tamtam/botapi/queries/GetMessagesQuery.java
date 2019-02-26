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

import chat.tamtam.botapi.model.MessageList;

public class GetMessagesQuery extends TamTamQuery<MessageList> { 
    private final QueryParam<Long> chatId = new QueryParam<Long>("chat_id", this).required();
    private final QueryParam<Long> from = new QueryParam<>("from", this);
    private final QueryParam<Long> to = new QueryParam<>("to", this);
    private final QueryParam<Integer> count = new QueryParam<>("count", this);

    public GetMessagesQuery(TamTamClient client, Long chatId) {
        super(client, "/messages", null, MessageList.class, Method.GET);
        this.chatId.setValue(chatId);
    }

    public GetMessagesQuery from(Long value) {
        this.from.setValue(value);
        return this;
    }
    public GetMessagesQuery to(Long value) {
        this.to.setValue(value);
        return this;
    }
    public GetMessagesQuery count(Integer value) {
        this.count.setValue(value);
        return this;
    }
}
