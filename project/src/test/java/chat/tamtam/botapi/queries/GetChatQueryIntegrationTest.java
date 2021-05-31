package chat.tamtam.botapi.queries;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

/**
 * @author alexandrchuprin
 */
public class GetChatQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldGetChatAsUser() throws Exception {
        List<Long> chatIds = getChats().stream()
                .filter(c -> c.getType() != ChatType.DIALOG)
                .filter(c -> c.getTitle().contains("bot is not admin"))
                .map(Chat::getChatId)
                .collect(Collectors.toList());

        for (Long chatId : chatIds) {
            Chat chat = new GetChatQuery(client, chatId).execute();
            assertThat("chat + " + chat, chat.getOwnerId(), is(nullValue()));
            assertThat("chat + " + chat, chat.getParticipants(), is(nullValue()));
        }
    }

    @Test
    public void shouldGetChatAsAdmin() throws Exception {
        List<Long> chatIds = getChats().stream()
                .filter(c -> c.getType() != ChatType.DIALOG)
                .filter(c -> c.getTitle().contains("bot is admin"))
                .map(Chat::getChatId)
                .collect(Collectors.toList());

        for (Long chatId : chatIds) {
            Chat chat = new GetChatQuery(client, chatId).execute();
            assertThat(chat.getOwnerId(), is(notNullValue()));
            assertThat(chat.getParticipants().size(), is(greaterThan(0)));
        }
    }

}