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
import chat.tamtam.botapi.model.CallbackAnswer;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.SimpleQueryResult;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AnswerOnCallbackQueryTest extends UnitTestBase {

    @Test
    public void answerOnCallbackTest() throws Exception {
        String callbackId = "somecallbackId";
        CallbackAnswer messageCallbackAnswer = new CallbackAnswer();
        NewMessageBody newMessageBody = new NewMessageBody("some text", null, null);
        messageCallbackAnswer.message(newMessageBody);
        SimpleQueryResult response = api.answerOnCallback(messageCallbackAnswer, callbackId).execute();
        assertThat(response.isSuccess(), is(true));

        CallbackAnswer callbackAnswer = new CallbackAnswer();
        callbackAnswer.notification("some notification");
        SimpleQueryResult response2 = api.answerOnCallback(callbackAnswer, callbackId).execute();
        assertThat(response2.isSuccess(), is(true));
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException() throws Exception {
        api.answerOnCallback(null, "notnull").execute();
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException2() throws Exception {
        api.answerOnCallback(new CallbackAnswer().notification("notif"), null).execute();
    }
}
