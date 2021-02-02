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
import chat.tamtam.botapi.model.ActionRequestBody;
import chat.tamtam.botapi.model.SenderAction;
import chat.tamtam.botapi.model.SimpleQueryResult;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class SendActionQueryTest extends UnitTestBase {
    
    @Test
    public void sendActionTest() throws Exception {
        ActionRequestBody actionRequestBody = new ActionRequestBody(SenderAction.TYPING_ON);
        Long chatId = 1L;
        SimpleQueryResult response = api.sendAction(actionRequestBody, chatId).execute();
        assertThat(response.isSuccess(), is(true));
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException() throws Exception {
        api.sendAction(null, 1L).execute();
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException2() throws Exception {
        api.sendAction(new ActionRequestBody(SenderAction.TYPING_ON), null).execute();
    }
}
