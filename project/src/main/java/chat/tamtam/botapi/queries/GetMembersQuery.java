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
import chat.tamtam.botapi.model.ChatMembersList;import java.util.Set;import java.util.Collection;
import static chat.tamtam.botapi.client.TamTamTransportClient.Method;

public class GetMembersQuery extends TamTamQuery<ChatMembersList> {
    public static final String PATH_TEMPLATE = "/chats/{chatId}/members";
    public final QueryParam<Collection<Long>> userIds = new CollectionQueryParam<>("user_ids", this);
    public final QueryParam<Long> marker = new QueryParam<>("marker", this);
    public final QueryParam<Integer> count = new QueryParam<>("count", this);

    public GetMembersQuery(TamTamClient client, Long chatId) {
        super(client, substitute(PATH_TEMPLATE, chatId), null, ChatMembersList.class, Method.GET);
    }

    public GetMembersQuery userIds(Set<Long> value) {
        this.userIds.setValue(value);
        return this;
    }

    public GetMembersQuery marker(Long value) {
        this.marker.setValue(value);
        return this;
    }

    public GetMembersQuery count(Integer value) {
        this.count.setValue(value);
        return this;
    }
}
