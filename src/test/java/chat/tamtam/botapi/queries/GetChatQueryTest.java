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

import org.junit.Test;

import chat.tamtam.botapi.exceptions.RequiredParameterMissingException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.server.TamTamService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetChatQueryTest extends UnitTestBase {

    @Test
    public void getChatTest() throws Exception {
        Long chatId = randomChat().getChatId();
        Chat chat = api.getChat(chatId).execute();
        assertThat(chat.getChatId(), is(chatId));
        assertThat(chat.getIcon().getUrl(), is(TamTamService.CHAT_ICON_URL));
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException() throws Exception {
        api.getChat(null).execute();
    }
}
