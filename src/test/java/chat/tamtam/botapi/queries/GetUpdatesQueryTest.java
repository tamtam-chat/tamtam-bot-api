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
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import chat.tamtam.botapi.model.BotAddedToChatUpdate;
import chat.tamtam.botapi.model.BotRemovedFromChatUpdate;
import chat.tamtam.botapi.model.BotStartedUpdate;
import chat.tamtam.botapi.model.Callback;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatTitleChangedUpdate;
import chat.tamtam.botapi.model.FailByDefaultUpdateVisitor;
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
import static org.junit.Assert.fail;
import static spark.Spark.get;

public class GetUpdatesQueryTest extends QueryTest {

    @Test
    public void getUpdatesTest() throws Exception {
        Chat randomChat = random(chats.values());
        long now = System.currentTimeMillis();

        MessageCreatedUpdate messageCreatedUpdate = new MessageCreatedUpdate(message(randomChat.getChatId()), now);
        MessageEditedUpdate messageEditedUpdate = new MessageEditedUpdate(message(randomChat.getChatId()), now);
        MessageRemovedUpdate messageRemovedUpdate = new MessageRemovedUpdate("mid." + ID_COUNTER.incrementAndGet(),
                now);
        MessageRestoredUpdate messageRestoredUpdate = new MessageRestoredUpdate("mid." + ID_COUNTER.incrementAndGet(),
                now);
        MessageCallbackUpdate messageCallbackUpdate = new MessageCallbackUpdate(
                new Callback(now, "calbackId", "payload", random(users.values())), now);
        UserAddedToChatUpdate userAddedToChatUpdate = new UserAddedToChatUpdate(ID_COUNTER.incrementAndGet(),
                ID_COUNTER.incrementAndGet(),
                ID_COUNTER.incrementAndGet(), System.currentTimeMillis());
        UserRemovedFromChatUpdate userRemovedFromChatUpdate = new UserRemovedFromChatUpdate(
                ID_COUNTER.incrementAndGet(), ID_COUNTER.incrementAndGet(),
                ID_COUNTER.incrementAndGet(), System.currentTimeMillis());
        BotAddedToChatUpdate botAddedToChatUpdate = new BotAddedToChatUpdate(ID_COUNTER.incrementAndGet(),
                ID_COUNTER.incrementAndGet(),
                System.currentTimeMillis());
        BotRemovedFromChatUpdate botRemovedFromChatUpdate = new BotRemovedFromChatUpdate(ID_COUNTER.incrementAndGet(),
                ID_COUNTER.incrementAndGet(),
                System.currentTimeMillis());
        BotStartedUpdate botStartedUpdate = new BotStartedUpdate(ID_COUNTER.incrementAndGet(),
                ID_COUNTER.incrementAndGet(), System.currentTimeMillis());
        ChatTitleChangedUpdate chatTitleChangedUpdate = new ChatTitleChangedUpdate(ID_COUNTER.incrementAndGet(),
                ID_COUNTER.incrementAndGet(), "title", System.currentTimeMillis());

        List<Update> updates = Arrays.asList(
                messageCreatedUpdate,
                messageEditedUpdate,
                messageRemovedUpdate,
                messageRestoredUpdate,
                messageCallbackUpdate,
                userAddedToChatUpdate,
                userRemovedFromChatUpdate,
                botAddedToChatUpdate,
                botRemovedFromChatUpdate,
                botStartedUpdate,
                chatTitleChangedUpdate
        );

        get("/updates", (request, response) -> new UpdateList(updates, null), this::serialize);

        Integer limit = 100;
        Integer timeout = 30;
        Long marker = null;
        UpdateList response = api.getUpdates()
                .marker(marker)
                .limit(limit)
                .timeout(timeout)
                .types(new HashSet<>(Arrays.asList(Update.MESSAGE_CREATED, Update.BOT_ADDED)))
                .execute();
        assertThat(response.getUpdates(), is(updates));

        for (Update update : updates) {
            update.visit(new FailByDefaultUpdateVisitor() {
                @Override
                public void visit(MessageCreatedUpdate model) {
                    assertThat(model, is(messageCreatedUpdate));
                }

                @Override
                public void visit(MessageCallbackUpdate model) {
                    assertThat(model, is(messageCallbackUpdate));
                }

                @Override
                public void visit(MessageEditedUpdate model) {
                    assertThat(model, is(messageEditedUpdate));
                }

                @Override
                public void visit(MessageRemovedUpdate model) {
                    assertThat(model, is(messageRemovedUpdate));
                }

                @Override
                public void visit(MessageRestoredUpdate model) {
                    assertThat(model, is(messageRestoredUpdate));
                }

                @Override
                public void visit(BotAddedToChatUpdate model) {
                    assertThat(model, is(botAddedToChatUpdate));
                }

                @Override
                public void visit(BotRemovedFromChatUpdate model) {
                    assertThat(model, is(botRemovedFromChatUpdate));
                }

                @Override
                public void visit(UserAddedToChatUpdate model) {
                    assertThat(model, is(userAddedToChatUpdate));
                }

                @Override
                public void visit(UserRemovedFromChatUpdate model) {
                    assertThat(model, is(userRemovedFromChatUpdate));
                }

                @Override
                public void visit(BotStartedUpdate model) {
                    assertThat(model, is(botStartedUpdate));
                }

                @Override
                public void visit(ChatTitleChangedUpdate model) {
                    assertThat(model, is(chatTitleChangedUpdate));
                }
            });
        }
    }
}
