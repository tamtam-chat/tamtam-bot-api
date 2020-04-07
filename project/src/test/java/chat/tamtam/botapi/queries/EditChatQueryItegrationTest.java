package chat.tamtam.botapi.queries;

import org.junit.Before;
import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatPatch;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;
import chat.tamtam.botapi.model.SendMessageResult;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class EditChatQueryItegrationTest extends TamTamIntegrationTest {

    Chat chat;

    @Before
    public void setUp() throws Exception {
        chat = getByTitle(getChats(client), "EditChatQueryItegrationTest");
    }

    @Test
    public void shouldChangeTitle() throws Exception {
        long chatId = chat.getChatId();
        String originalTitle = chat.getTitle();
        try {
            String newTitle = randomText(32);
            ChatPatch chatPatch = new ChatPatch().title(newTitle);
            Chat chatResult = new EditChatQuery(client, chatPatch, chatId).execute();
            assertThat(chatResult.getTitle(), is(newTitle));
        } finally {
            new EditChatQuery(client, new ChatPatch().title(originalTitle), chatId).execute();
        }
    }

    @Test
    public void shouldThrowExceptionWhenTitleIsTooLong() throws Exception {
        String title = "[1] Is inquiry no he several excited am. Detract yet delight written farther his general. " +
                "Feel and make two real miss use easy. Pain son rose more park way that. He felicity no an at " +
                "packages answered opir";

        setInvalidChatTitle(title);
    }

    @Test
    public void shouldThrowExceptionWhenTitleIsEmpty() throws Exception {
        String title = "";
        setInvalidChatTitle(title);
    }

    @Test
    public void shouldThrowExceptionWhenTitleIsEmpty2() throws Exception {
        String title = "   ";
        setInvalidChatTitle(title);
    }

    @Test
    public void shouldThrowExceptionWhenTitleIsEmpty3() throws Exception {
        String title = "  \n\t ";
        setInvalidChatTitle(title);
    }

    @Test
    public void shouldThrowExceptionWhenTitleIsEmpty4() throws Exception {
        String title =
                "\u0009\u000B\u000C\u00A0\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008" +
                        "\u2009\u200A\u202F\u205F\u3000\u200B\u200C\u200D\u2060\uFEFF";

        setInvalidChatTitle(title);
    }

    @Test
    public void shouldChangeIconByUrl() throws Exception {
        long chatId = chat.getChatId();
        PhotoAttachmentRequestPayload iconPayload = new PhotoAttachmentRequestPayload();
        iconPayload.url("https://web.tamtam.chat/-/images/auth/devices.1504333530.png");
        ChatPatch patch = new ChatPatch().icon(iconPayload);
        new EditChatQuery(client, patch, chatId).execute();
    }

    @Test
    public void shouldThrowExceptionWhenIconUrlIsEmpty() throws Exception {
        try {
            long chatId = chat.getChatId();
            PhotoAttachmentRequestPayload iconPayload = new PhotoAttachmentRequestPayload();
            iconPayload.url("   ");
            ChatPatch patch = new ChatPatch().icon(iconPayload);
            new EditChatQuery(client, patch, chatId).execute();
            fail("Should fall in catch block");
        } catch (APIException e) {
            assertThat(e.getStatusCode(), is(400));
        }
    }

    @Test
    public void shouldPinMessage() throws Exception {
        Long chatId = chat.getChatId();
        SendMessageResult msg = botAPI.sendMessage(new NewMessageBody("to be pinned", null, null)).chatId(
                chatId).execute();
        ChatPatch patch = new ChatPatch().pin(msg.getMessage().getBody().getMid());
        Chat chat = new EditChatQuery(client, patch, chatId).execute();
        assertThat(chat.getPinnedMessage(), is(msg.getMessage()));
    }

    private void setInvalidChatTitle(String title) throws Exception {
        Chat chat = this.chat;
        long chatId = chat.getChatId();
        String originalTitle = chat.getTitle();
        try {
            ChatPatch chatPatch = new ChatPatch().title(title);
            new EditChatQuery(client, chatPatch, chatId).execute();
            fail("Should fall in catch block");
        } catch (APIException e) {
            assertThat(e.getStatusCode(), is(400));
        } finally {
            new EditChatQuery(client, new ChatPatch().title(originalTitle), chatId).execute();
        }
    }
}