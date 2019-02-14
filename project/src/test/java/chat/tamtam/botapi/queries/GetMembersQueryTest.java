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

import chat.tamtam.botapi.exceptions.RequiredParameterMissingException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.model.ChatMember;
import chat.tamtam.botapi.model.ChatMembersList;
import chat.tamtam.botapi.queries.GetMembersQuery;
import org.junit.Test;
import org.junit.Ignore;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetMembersQueryTest extends QueryTest {
    
    @Test
    public void getMembersTest() throws Exception {
        ChatList chatList = api.getChats().count(1).execute();
        Chat chat = chatList.getChats().get(0);
        Long chatId = chat.getChatId();
        Long marker = null;
        Integer count = 5;
        List<ChatMember> members = new ArrayList<>();
        do {
            ChatMembersList response = api.getMembers(chatId).count(count).marker(marker).execute();
            marker = response.getMarker();
            members.addAll(response.getMembers());
        } while (marker != null);

        assertThat(members.size(), is(chat.getParticipantsCount()));
        for (ChatMember member : members) {
            assertThat(member.isOwner(), is(notNullValue()));
            assertThat(member.getLastAccessTime(), is(notNullValue()));
        }
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException() throws Exception {
        api.getMembers(null).execute();
    }
}
