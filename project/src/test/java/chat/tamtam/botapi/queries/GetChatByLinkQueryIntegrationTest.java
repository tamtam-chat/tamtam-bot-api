package chat.tamtam.botapi.queries;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author alexandrchuprin
 */
public class GetChatByLinkQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldReturn404() throws Exception {
        try {
            botAPI.getChatByLink("nonExistingLink" + System.currentTimeMillis()).execute();
        } catch (APIException e) {
            assertThat(e.getStatusCode(), is(404));
        }
    }

    @Test
    public void shouldReturnDialogWithUser() throws Exception {
        Chat chat = botAPI.getChatByLink("primebot").execute();
        assertThat(chat.getType(), is(ChatType.DIALOG));
        assertThat(chat.getDialogWithUser().getUsername().toLowerCase(), is("primebot"));
    }

    @Test
    public void shouldReturnChat() throws Exception {
        Chat chat = botAPI.getChatByLink("helpchat").execute();
        assertThat(chat.getType(), is(ChatType.CHAT));
        assertThat(chat.isPublic(), is(true));
        assertThat(chat.getLink(), is("https://tt.me/helpchat"));
    }

    @Test
    public void shouldReturnChat2() throws Exception {
        Chat chat = botAPI.getChatByLink("@helpchat").execute();
        assertThat(chat.getType(), is(ChatType.CHAT));
        assertThat(chat.isPublic(), is(true));
        assertThat(chat.getLink(), is("https://tt.me/helpchat"));
    }

    @Test
    public void shouldReturnChannel() throws Exception {
        Chat chat = botAPI.getChatByLink("botapichannel").execute();
        assertThat(chat.getType(), is(ChatType.CHANNEL));
        assertThat(chat.isPublic(), is(true));
        assertThat(chat.getLink(), is("https://tt.me/botapichannel"));
    }
}