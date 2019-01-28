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

import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.queries.SendMessageQuery;
import chat.tamtam.botapi.model.SendMessageResult;

public class SendMessageQuery extends TamTamQuery<SendMessageResult> {
    private final QueryParam<Long> userId = new QueryParam<Long>("user_id", this);
    private final QueryParam<Long> chatId = new QueryParam<Long>("chat_id", this);

    public SendMessageQuery(TamTamClient client, NewMessageBody newMessageBody) {
        super(client, "/messages", newMessageBody, SendMessageResult.class, Method.POST);
    }

    public SendMessageQuery userId(Long value) {
        this.userId.setValue(value);
        return this;
    }
    public SendMessageQuery chatId(Long value) {
        this.chatId.setValue(value);
        return this;
    }
}
