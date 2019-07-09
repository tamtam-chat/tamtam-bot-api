package chat.tamtam.botapi.queries;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.BotAddedToChatUpdate;
import chat.tamtam.botapi.model.BotInfo;
import chat.tamtam.botapi.model.BotRemovedFromChatUpdate;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatPatch;
import chat.tamtam.botapi.model.ChatTitleChangedUpdate;
import chat.tamtam.botapi.model.FailByDefaultUpdateVisitor;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.UpdateList;
import chat.tamtam.botapi.model.User;
import chat.tamtam.botapi.model.UserAddedToChatUpdate;
import chat.tamtam.botapi.model.UserIdsList;
import chat.tamtam.botapi.model.UserRemovedFromChatUpdate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class GetUpdatesQueryIntegrationTest2 extends TamTamIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void shouldGetUpdates() throws Exception {
        String originalChatTitle = "test chat #8";
        Chat commonChat = getByTitle(getChats(), originalChatTitle);
        Long commonChatId = commonChat.getChatId();
        List<String> sentMessages = new CopyOnWriteArrayList<>();
        List<String> receivedMessages = new CopyOnWriteArrayList<>();
        String chatTitle = randomText(16);
        User bot1User = new User(me.getUserId(), me.getName(), me.getUsername());
        BotInfo bot3 = new GetMyInfoQuery(client3).execute();
        User bot3user = new User(bot3.getUserId(), bot3.getName(), bot3.getUsername());

        // consume all pending updates to make sure queue is empty before test
        flush(client);
        flush(client2);

        Function<Long, Long> getUpdates = (marker) -> {
            LOG.info("Marker: " + marker);
            try {
                UpdateList updateList = new GetUpdatesQuery(client2).timeout(10).marker(marker).execute();
                for (Update update : updateList.getUpdates()) {
                    update.visit(new FailByDefaultUpdateVisitor() {
                        @Override
                        public void visit(BotAddedToChatUpdate model) {
                            assertThat(model.getChatId(), is(commonChatId));
                            assertThat(model.getUser(), is(bot1User));
                        }

                        @Override
                        public void visit(BotRemovedFromChatUpdate model) {
                            assertThat(model.getChatId(), is(commonChatId));
                            assertThat(model.getUser(), is(bot1User));
                        }

                        @Override
                        public void visit(UserAddedToChatUpdate model) {
                            assertThat(model.getChatId(), is(commonChatId));
                            assertThat(model.getUser(), is(bot3user));
                        }

                        @Override
                        public void visit(UserRemovedFromChatUpdate model) {
                            assertThat(model.getChatId(), is(commonChatId));
                            assertThat(model.getUser(), is(bot3user));
                        }

                        @Override
                        public void visit(ChatTitleChangedUpdate model) {
                            assertThat(model.getChatId(), is(commonChatId));
                            assertThat(model.getTitle(), is(chatTitle));
                            assertThat(model.getUser(), is(bot1User));
                        }
                    });
                }

                return updateList.getMarker();
            } catch (APIException | ClientException e) {
                fail();
                throw new RuntimeException(e);
            }
        };


        AtomicBoolean consumerStopped = new AtomicBoolean();

        FutureTask<Long> consumer = new FutureTask<>(() -> {
            Long marker = null;
            while (!consumerStopped.get()) {
                marker = getUpdates.apply(marker);
            }

            getUpdates.apply(marker);
            return marker;
        });

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        try {
            addUser(client, commonChatId, bot2.getUserId());
            changeTitle(client, commonChatId, chatTitle);
            addUser(client, commonChatId, bot3.getUserId());
            removeUser(client, commonChatId, bot3.getUserId());
        } finally {
            removeUser(client, commonChatId, bot2.getUserId());
            changeTitle(client, commonChatId, originalChatTitle);
        }

        consumerStopped.set(true);
        consumer.get();
        consumerThread.join();

        assertThat(receivedMessages, is(sentMessages));
    }

    private void removeUser(TamTamClient client, Long commonChatId, Long userId) throws Exception {
        new RemoveMemberQuery(client, commonChatId, userId).execute();
    }

    private void changeTitle(TamTamClient client, Long chatId, String chatTitle) throws Exception {
        new EditChatQuery(client, new ChatPatch().title(chatTitle), chatId).execute();
    }

    private void addUser(TamTamClient client, Long chatId, Long userId) throws Exception {
        new AddMembersQuery(client, new UserIdsList(Collections.singletonList(userId)), chatId).execute();
    }

    private void flush(TamTamClient client) throws APIException, ClientException {
        Long marker = new GetUpdatesQuery(this.client).timeout(2).execute().getMarker();
        new GetUpdatesQuery(client).marker(marker).timeout(2).execute();
    }
}