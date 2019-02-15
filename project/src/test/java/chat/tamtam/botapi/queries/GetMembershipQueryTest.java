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

import chat.tamtam.botapi.model.ChatMember;
import chat.tamtam.botapi.queries.GetMembershipQuery;
import spark.Spark;

import org.junit.Test;
import org.junit.Ignore;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetMembershipQueryTest extends QueryTest {
    @Test
    public void getMembershipTest() throws Exception {
        Long chatId = random(chats.values()).getChatId();
        Spark.get("/chats/:chatId/members/me", (req, resp) -> chatMembers.get(chatId).get(0), this::serialize);
        GetMembershipQuery query = new GetMembershipQuery(client, chatId);
        ChatMember response = query.execute();
        assertThat(response, is(chatMembers.get(chatId).get(0)));
    }
    
}
