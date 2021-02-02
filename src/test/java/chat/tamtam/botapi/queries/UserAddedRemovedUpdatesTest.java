package chat.tamtam.botapi.queries;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import chat.tamtam.botapi.VisitedUpdatesTracer;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.NoopUpdateVisitor;
import chat.tamtam.botapi.model.User;
import chat.tamtam.botapi.model.UserAddedToChatUpdate;
import chat.tamtam.botapi.model.UserRemovedFromChatUpdate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
        User bot3user = new User(bot3.getUserId(), bot3.getName(), bot3.getUsername(), true);

        CountDownLatch bot3added = new CountDownLatch(1);
        CountDownLatch bot3removed = new CountDownLatch(1);
        VisitedUpdatesTracer bot2updates = new VisitedUpdatesTracer(new NoopUpdateVisitor() {
            @Override
            public void visit(UserAddedToChatUpdate model) {
                assertThat(model.getChatId(), is(commonChatId));
                assertThat(model.getInviterId(), is(bot1.getUserId()));
                assertThat(model.getUser(), is(bot3user));
                bot3added.countDown();
            }

            @Override
            public void visit(UserRemovedFromChatUpdate model) {
                assertThat(model.getChatId(), is(commonChatId));
                assertThat(model.getAdminId(), is(bot1.getUserId()));
                assertThat(model.getUser(), is(bot3user));
                bot3removed.countDown();
            }
        });

        try (AutoCloseable ignored = bot2.addConsumer(commonChatId, bot2updates);) {
            try {
                addUser(client, commonChatId, bot3.getUserId());
                await(bot3added);
            } finally {
                removeUser(client, commonChatId, bot3.getUserId());
                await(bot3removed);
            }
        }
    }
}