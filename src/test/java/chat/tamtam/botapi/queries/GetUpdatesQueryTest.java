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

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import chat.tamtam.botapi.model.BotAddedToChatUpdate;
import chat.tamtam.botapi.model.BotRemovedFromChatUpdate;
import chat.tamtam.botapi.model.Callback;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.MessageCallbackUpdate;
import chat.tamtam.botapi.model.MessageCreatedUpdate;
import chat.tamtam.botapi.model.MessageEditedUpdate;
import chat.tamtam.botapi.model.MessageRemovedUpdate;
import chat.tamtam.botapi.model.MessageRestoredUpdate;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.UpdateList;
import chat.tamtam.botapi.model.UserAddedToChatUpdate;
import chat.tamtam.botapi.model.UserRemovedFromChatUpdate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static spark.Spark.get;

public class GetUpdatesQueryTest extends QueryTest {

    @Test
    public void getUpdatesTest() throws Exception {
        Chat randomChat = random(chats.values());
        long now = System.currentTimeMillis();

        List<Update> updates = Arrays.asList(
                new MessageCreatedUpdate(newMessage(randomChat), now),
                new MessageEditedUpdate(newMessage(randomChat), now),
                new MessageRemovedUpdate("mid." + ID_COUNTER.incrementAndGet(), now),
                new MessageRestoredUpdate("mid." + ID_COUNTER.incrementAndGet(), now),
                new MessageCallbackUpdate(new Callback(now, "calbackId", "payload", random(users.values())), now),
                new UserAddedToChatUpdate(ID_COUNTER.incrementAndGet(), ID_COUNTER.incrementAndGet(),
                        ID_COUNTER.incrementAndGet(), System.currentTimeMillis()),
                new UserRemovedFromChatUpdate(ID_COUNTER.incrementAndGet(), ID_COUNTER.incrementAndGet(),
                        ID_COUNTER.incrementAndGet(), System.currentTimeMillis()),
                new BotAddedToChatUpdate(ID_COUNTER.incrementAndGet(), ID_COUNTER.incrementAndGet(),
                        System.currentTimeMillis()),
                new BotRemovedFromChatUpdate(ID_COUNTER.incrementAndGet(), ID_COUNTER.incrementAndGet(),
                        System.currentTimeMillis())
        );

        get("/updates", (request, response) -> new UpdateList(updates, null), this::serialize);

        Integer limit = 100;
        Integer timeout = 30;
        Long marker = null;
        UpdateList response = api.getUpdates().marker(marker).limit(limit).timeout(timeout).execute();
        assertThat(response.getUpdates(), is(updates));
    }
}
