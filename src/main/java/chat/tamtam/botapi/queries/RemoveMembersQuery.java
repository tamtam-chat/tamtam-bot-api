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

import java.util.List;
import chat.tamtam.botapi.queries.RemoveMembersQuery;
import chat.tamtam.botapi.model.UserIdsList;

public class RemoveMembersQuery extends TamTamQuery<UserIdsList> {
    private final QueryParam<List<Long>> userIds = new QueryParam<List<Long>>("user_ids", this).required();

    public RemoveMembersQuery(TamTamClient client, Long chatId, List<Long> userIds) {
        super(client, substitute("/chats/{chatId}/members", chatId), null, UserIdsList.class, Method.DELETE);
        this.userIds.setValue(userIds);
    }

}
