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
import chat.tamtam.botapi.model.SimpleQueryResult;
import spark.Spark;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DeleteMessageQueryTest extends UnitTestBase {
    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowExceptionOnMissingparam() throws Exception {
        String messageId = null;
        DeleteMessageQuery query = new DeleteMessageQuery(client, messageId);
        query.execute();
    }

    @Test
    public void shouldExecuteQuery() throws Exception {
        Spark.delete("/messages", (req, resp) -> new SimpleQueryResult(true), this::serialize);
        String messageId = "mid.123";
        DeleteMessageQuery query = api.deleteMessage(messageId);
        SimpleQueryResult result = query.execute();
        assertThat(result.isSuccess(), is(true));
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrow() throws Exception {
        api.deleteMessage(null).execute();
    }

}
