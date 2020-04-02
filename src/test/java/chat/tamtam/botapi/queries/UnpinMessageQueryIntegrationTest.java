package chat.tamtam.botapi.queries;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.BadRequestException;
import chat.tamtam.botapi.exceptions.ChatAccessForbiddenException;
import chat.tamtam.botapi.exceptions.NotFoundException;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PinMessageBody;
import chat.tamtam.botapi.model.SimpleQueryResult;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class UnpinMessageQueryIntegrationTest extends TamTamIntegrationTest {
    @Test(expected = BadRequestException.class)
    public void shouldThrowForDialog() throws Exception {
        botAPI.unpinMessage(123L).execute();
    }

    @Test(expected = ChatAccessForbiddenException.class)
    public void shouldThrowForNonExistingChat() throws Exception {
        botAPI.unpinMessage(-123L).execute();
    }

    @Test(expected = ChatAccessForbiddenException.class)
    public void shouldThrowIfBotIsNotAdmin() throws Exception {
        long chatId = getByTitle(getChats(client2), "PinMessageQueryIntegrationTest").getChatId();
        String mid = doSend(new NewMessageBody(randomText(), null, null), chatId).getMessage().getBody().getMid();
        botAPI.pinMessage(new PinMessageBody(mid), chatId).execute();
        new UnpinMessageQuery(client2, chatId).execute();
    }

    @Test
    public void shouldUnpin() throws Exception {
        long chatId = getByTitle(getChats(client2), "PinMessageQueryIntegrationTest").getChatId();
        String mid = doSend(new NewMessageBody(randomText(), null, null), chatId).getMessage().getBody().getMid();
        botAPI.pinMessage(new PinMessageBody(mid), chatId).execute();
        botAPI.unpinMessage(chatId).execute();
        assertThat(botAPI.getPinnedMessage(chatId).execute().getMessage(), is(nullValue()));

        SimpleQueryResult secondUnpin = botAPI.unpinMessage(chatId).execute();
        assertThat(secondUnpin.isSuccess(), is(false));
    }
}