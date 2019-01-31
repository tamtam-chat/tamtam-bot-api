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

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import chat.tamtam.botapi.exceptions.RequiredParameterMissingException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class GetMessagesQueryTest extends QueryTest {

    @Test
    public void getMessagesTest() throws Exception {
        ChatList chatList = api.getChats().count(Integer.MAX_VALUE).execute();
        for (Chat chat : chatList.getChats()){
            Long chatId = chat.getChatId();
            MessageList response = api.getMessages(chatId)
                    .count(ThreadLocalRandom.current().nextInt())
                    .from(ThreadLocalRandom.current().nextLong())
                    .to(ThreadLocalRandom.current().nextLong())
                    .execute();

            assertThat(response.getMessages().size(), is(greaterThan(0)));
            for (Message message : response.getMessages()) {
                assertThat(message.getMessage().getReplyTo(), is(notNullValue()));
            }
        }
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException() throws Exception {
        api.getMessages(null).execute();
    }
}
