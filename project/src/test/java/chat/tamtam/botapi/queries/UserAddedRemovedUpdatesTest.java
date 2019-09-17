package chat.tamtam.botapi.queries;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import chat.tamtam.botapi.VisitedUpdatesTracer;
import chat.tamtam.botapi.model.BotAddedToChatUpdate;
import chat.tamtam.botapi.model.BotRemovedFromChatUpdate;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.FailByDefaultUpdateVisitor;
import chat.tamtam.botapi.model.NoopUpdateVisitor;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.User;
import chat.tamtam.botapi.model.UserAddedToChatUpdate;
import chat.tamtam.botapi.model.UserRemovedFromChatUpdate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class UserAddedRemovedUpdatesTest extends GetUpdatesIntegrationTest {
    @Test
    public void shouldGetUpdatesInChat() throws Exception {
        test("UserAddedRemovedUpdatesTest#shouldGetUpdatesInChat");
    }

    @Test
    public void shouldGetUpdatesInChannel() throws Exception {
        test("UserAddedRemovedUpdatesTest#shouldGetUpdatesInChannel");
    }

    private void test(String chatTitle) throws Exception {
        Chat commonChat = getByTitle(getChats(), chatTitle);
        Long commonChatId = commonChat.getChatId();
        User bot3user = new User(bot3.getUserId(), bot3.getName(), bot3.getUsername());

        CountDownLatch bot3added = new CountDownLatch(1);
        CountDownLatch bot3removed = new CountDownLatch(1);
        VisitedUpdatesTracer bot2updates = new VisitedUpdatesTracer(new NoopUpdateVisitor() {
            @Override
            public void visit(UserAddedToChatUpdate model) {
                assertThat(model.getChatId(), is(commonChatId));
                assertThat(model.getInviterId(), is(bot1.getUserId()));
                assertThat(model.getUser(), is(bot3user));
            }

            @Override
            public void visit(UserRemovedFromChatUpdate model) {
                assertThat(model.getChatId(), is(commonChatId));
                assertThat(model.getAdminId(), is(bot1.getUserId()));
                assertThat(model.getUser(), is(bot3user));
                bot3removed.countDown();
            }
        });

        Update.Visitor bot3Updates = new FailByDefaultUpdateVisitor() {
            @Override
            public void visit(BotAddedToChatUpdate model) {
                bot3added.countDown();
            }

            @Override
            public void visit(BotRemovedFromChatUpdate model) {
                bot3removed.countDown();
            }
        };

        bot1.addConsumer(new Bot1ToBot3RedirectingUpdateVisitor(bot3Updates));
        bot2.addConsumer(bot2updates);

        addUser(client, commonChatId, bot3.getUserId());
        await(bot3added);

        removeUser(client, commonChatId, bot3.getUserId());
        await(bot3removed);

        Set<String> testable = Stream.of(Update.USER_ADDED, Update.USER_REMOVED).collect(Collectors.toSet());
        assertThat(bot2updates.getVisited(), is(testable));
    }
}