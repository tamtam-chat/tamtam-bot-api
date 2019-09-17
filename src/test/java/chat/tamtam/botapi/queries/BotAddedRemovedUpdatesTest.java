package chat.tamtam.botapi.queries;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import chat.tamtam.botapi.VisitedUpdatesTracer;
import chat.tamtam.botapi.model.BotAddedToChatUpdate;
import chat.tamtam.botapi.model.BotRemovedFromChatUpdate;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.FailByDefaultUpdateVisitor;
import chat.tamtam.botapi.model.MessageCreatedUpdate;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.User;
import chat.tamtam.botapi.model.UserRemovedFromChatUpdate;

import static chat.tamtam.botapi.Visitors.noDuplicates;
import static chat.tamtam.botapi.Visitors.tracing;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class BotAddedRemovedUpdatesTest extends GetUpdatesIntegrationTest {
    @Test
    public void shouldGetUpdatesInChat() throws Exception {
        test("test chat #8");
    }

    @Test
    public void shouldGetUpdatesInPublicChat() throws Exception {
        test("BotAddedRemovedUpdatesTest#shouldGetUpdatesInPrivateChatWithLink");
    }

    @Test
    public void shouldGetUpdatesInChannel() throws Exception {
        test("BotAddedRemovedUpdatesTest#shouldGetUpdatesInChannel");
    }

    private void test(String chatTitle) throws Exception {
        Chat commonChat = getByTitle(getChats(), chatTitle);
        Long commonChatId = commonChat.getChatId();
        User bot1user = new User(bot1.getUserId(), bot1.getName(), bot1.getUsername());

        CountDownLatch bot2removed = new CountDownLatch(1);
        CountDownLatch bot3removed = new CountDownLatch(1);

        VisitedUpdatesTracer bot2updates = tracing(noDuplicates(new FailByDefaultUpdateVisitor() {
            @Override
            public void visit(MessageCreatedUpdate model) {
                Long senderId = model.getMessage().getSender().getUserId();
                if (senderId.equals(bot3.getUserId())) {
                    return;
                }

                super.visit(model);
            }

            @Override
            public void visit(BotAddedToChatUpdate model) {
                assertThat(model.getChatId(), is(commonChatId));
                assertThat(model.getUser().getUserId(), is(bot1.getUserId()));
                removeUser(client, commonChatId, bot2.getUserId());
            }

            @Override
            public void visit(BotRemovedFromChatUpdate model) {
                assertThat(model.getChatId(), is(commonChatId));
                assertThat(model.getUser(), is(bot1user));
                bot2removed.countDown();
            }

            @Override
            public void visit(UserRemovedFromChatUpdate model) {
                // ignoring
            }
        }));

        VisitedUpdatesTracer bot3updates = tracing(noDuplicates(new FailByDefaultUpdateVisitor() {
            @Override
            public void visit(BotAddedToChatUpdate model) {
                LOG.info("Bot {} added to chat", model.getUser().getName());
                assertThat(model.getChatId(), is(commonChatId));
                assertThat(model.getUser().getUserId(), is(bot1.getUserId()));
                removeUser(client, commonChatId, bot3.getUserId());
            }

            @Override
            public void visit(BotRemovedFromChatUpdate model) {
                assertThat(model.getChatId(), is(commonChatId));
                assertThat(model.getUser(), is(bot1user));
                bot3removed.countDown();
            }
        }));

        bot1.addConsumer(new Bot1ToBot3RedirectingUpdateVisitor(bot3updates));
        bot2.addConsumer(bot2updates);

        addUser(client, commonChatId, bot2.getUserId());
        await(bot2removed);

        addUser(client, commonChatId, bot3.getUserId());
        await(bot3removed);

        Set<String> testable = Stream.of(Update.BOT_ADDED, Update.BOT_REMOVED).collect(Collectors.toSet());
        assertThat(bot2updates.getVisited(), is(testable));
        assertThat(bot3updates.getVisited(), is(testable));
    }
}