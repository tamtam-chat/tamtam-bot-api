package chat.tamtam.botapi.queries;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatMember;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class LeaveChatQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldLeaveChat() throws Exception {
        Chat chat = getByTitle(getChats(), "LeaveChatQueryIntegrationTest#shouldLeaveChat");
        test(chat);
    }

    @Test
    public void shouldLeaveChannel() throws Exception {
        Chat chat = getByTitle(getChats(), "LeaveChatQueryIntegrationTest#shouldLeaveChannel");
        test(chat);
    }

    private void test(Chat chat) throws Exception {
        bot3.joinChat(chat.getLink());
        Thread.sleep(2000);
        Long chatId = chat.getChatId();
        Set<Long> members = getMembers(chatId);
        assertThat(members, hasItem(bot3.getUserId()));
        bot3.leaveChat(chatId);
        Thread.sleep(2000);
        assertThat(getMembers(chatId), not(hasItem(bot3.getUserId())));
    }

    private Set<Long> getMembers(Long chatId) throws Exception {
        return botAPI.getMembers(chatId).execute().getMembers().stream()
                .map(ChatMember::getUserId).collect(Collectors.toSet());
    }
}