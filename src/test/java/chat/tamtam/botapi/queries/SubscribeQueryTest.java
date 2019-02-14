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
import chat.tamtam.botapi.model.SimpleQueryResult;
import chat.tamtam.botapi.queries.SubscribeQuery;
import chat.tamtam.botapi.model.SubscriptionRequestBody;
import org.junit.Test;
import org.junit.Ignore;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscribeQueryTest extends QueryTest {
    
    @Test
    public void subscribeTest() throws Exception {
        SubscriptionRequestBody subscriptionRequestBody = new SubscriptionRequestBody("https://someurl.com")
                .filter("some-filter");
        SimpleQueryResult response = api.subscribe(subscriptionRequestBody).execute();
        assertThat(response.isSuccess(), is(true));
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException() throws Exception {
        api.subscribe(null).execute();
    }
}
