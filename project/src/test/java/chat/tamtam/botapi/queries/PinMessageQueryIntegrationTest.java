package chat.tamtam.botapi.queries;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.BadRequestException;
import chat.tamtam.botapi.exceptions.ChatAccessForbiddenException;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PinMessageBody;
import chat.tamtam.botapi.model.SimpleQueryResult;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class PinMessageQueryIntegrationTest extends TamTamIntegrationTest {
    @Test(expected = BadRequestException.class)
    public void shouldThrowForDialog() throws Exception {
        botAPI.pinMessage(new PinMessageBody("mid123"), 123L).execute();
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowForNonExistingChat() throws Exception {
        botAPI.pinMessage(new PinMessageBody("mid123"), -123L).execute();
    }

    @Test(expected = ChatAccessForbiddenException.class)
    public void shouldThrowIfBotIsNotAdmin() throws Exception {
        long chatId = getByTitle(getChats(client2), "PinMessageQueryIntegrationTest").getChatId();
        String mid = doSend(new NewMessageBody(randomText(), null, null), chatId).getMessage().getBody().getMid();
        new PinMessageQuery(client2, new PinMessageBody(mid), chatId).execute();
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowIfMessageIdIsInvalid() throws Exception {
        long chatId = getByTitle(getChats(), "PinMessageQueryIntegrationTest").getChatId();
        botAPI.pinMessage(new PinMessageBody("mid123"), chatId).execute();
    }

    @Test
    public void shouldPin() throws Exception {
        long chatId = getByTitle(getChats(), "PinMessageQueryIntegrationTest").getChatId();
        Message message = doSend(new NewMessageBody(randomText(), null, null), chatId).getMessage();
        SimpleQueryResult result = botAPI.pinMessage(new PinMessageBody(message.getBody().getMid()), chatId).execute();
        assertThat(result.isSuccess(), is(true));

        Message pinned = botAPI.getPinnedMessage(chatId).execute().getMessage();
        assertThat(pinned, is(message));
    }
}