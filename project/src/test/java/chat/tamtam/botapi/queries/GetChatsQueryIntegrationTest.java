package chat.tamtam.botapi.queries;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

/**
 * @author alexandrchuprin
 */
public class GetChatsQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldGetAllChats() throws Exception {
        List<Chat> chats = new ArrayList<>();
        Long marker;
        do {
            ChatList chatList = botAPI.getChats().execute();
            chats.addAll(chatList.getChats());
            marker = chatList.getMarker();
        } while (marker != null);

        assertThat(chats.size(), is(greaterThan(0)));

        for (Chat chat : chats) {
            if (chat.getTitle() != null && chat.getTitle().toLowerCase().contains("public")) {
                assertThat(chat.isPublic(), is(true));
                assertThat(chat.getLink(), is(notNullValue()));
            }

            assertThat(chat.getChatId(), is(notNullValue()));
            assertThat(chat.getType(), is(notNullValue()));
            assertThat(chat.getStatus(), is(notNullValue()));
            assertThat(chat.getParticipantsCount(), is(greaterThan(0)));
        }
    }
}