package chat.tamtam.botapi.queries;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.BadRequestException;
import chat.tamtam.botapi.exceptions.ChatAccessForbiddenException;
import chat.tamtam.botapi.exceptions.NotFoundException;

/**
 * @author alexandrchuprin
 */
public class GetPinnedMessageQueryIntegrationTest extends TamTamIntegrationTest {
    @Test(expected = BadRequestException.class)
    public void shouldThrowForDialog() throws Exception {
        botAPI.getPinnedMessage(123L).execute();
    }

    @Test(expected = ChatAccessForbiddenException.class)
    public void shouldThrowForNonExistingChat() throws Exception {
        botAPI.getPinnedMessage(-123L).execute();
    }

    @Test(expected = ChatAccessForbiddenException.class)
    public void shouldThrowIfBotIsNotAdmin() throws Exception {
        long chatId = getByTitle(getChats(client2), "PinMessageQueryIntegrationTest").getChatId();
        new GetPinnedMessageQuery(client2, chatId).execute();
    }
}