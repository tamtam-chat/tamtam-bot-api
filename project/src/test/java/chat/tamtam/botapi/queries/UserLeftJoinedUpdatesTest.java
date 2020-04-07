package chat.tamtam.botapi.queries;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.FailByDefaultUpdateVisitor;
import chat.tamtam.botapi.model.User;
import chat.tamtam.botapi.model.UserAddedToChatUpdate;
import chat.tamtam.botapi.model.UserRemovedFromChatUpdate;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class UserLeftJoinedUpdatesTest extends GetUpdatesIntegrationTest {
    @Test
    public void shouldGetUpdatesInChat() throws Exception {
        test("UserLeftJoinedUpdatesTest#shouldGetUpdatesInChat");
    }

    @Test
    public void shouldGetUpdatesInPublicChat() throws Exception {
        test("UserLeftJoinedUpdatesTest#shouldGetUpdatesInPublicChat");
    }

    @Test
    public void shouldGetUpdatesInPrivateChannel() throws Exception {
        test("UserLeftJoinedUpdatesTest#shouldGetUpdatesInPrivateChannel");
    }

    @Test
    public void shouldGetUpdatesInPublicChannel() throws Exception {
        test("UserLeftJoinedUpdatesTest#shouldGetUpdatesInPublicChannel");
    }

    private void test(String chatTitle) throws Exception {
        Chat commonChat = getByTitle(getChats(), chatTitle);
        Long commonChatId = commonChat.getChatId();
        User bot3user = new User(bot3.getUserId(), bot3.getName(), bot3.getUsername(), true);

        CountDownLatch bot3added = new CountDownLatch(1);
        CountDownLatch bot3removed = new CountDownLatch(1);
        FailByDefaultUpdateVisitor bot1updates = new FailByDefaultUpdateVisitor() {
            @Override
            public void visit(UserAddedToChatUpdate model) {
                assertThat(model.getChatId(), is(commonChatId));
                assertThat(model.getInviterId(), is(nullValue()));
                assertThat(model.getUser(), is(bot3user));
                bot3added.countDown();
            }

            @Override
            public void visit(UserRemovedFromChatUpdate model) {
                assertThat(model.getChatId(), is(commonChatId));
                assertThat(model.getAdminId(), is(nullValue()));
                assertThat(model.getUser(), is(bot3user));
                bot3removed.countDown();
            }
        };

        bot1.addConsumer(bot1updates);

        try {
            bot3.joinChat(commonChat.getLink());
            await(bot3added);
        } finally {
            bot3.leaveChat(commonChatId);
            await(bot3removed);
        }
    }
}