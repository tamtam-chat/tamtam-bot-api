package chat.tamtam.botapi.queries;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import chat.tamtam.botapi.IntegrationTest;
import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageBody;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;
import chat.tamtam.botapi.model.PhotoTokens;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.UploadType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
@Category(IntegrationTest.class)
public class EditMessageQueryIntegrationTest extends TamTamIntegrationTest {
    private AttachmentRequest photoAR;
    private AttachmentRequest photoAR2;
    private List<Chat> chats;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String uploadUrl = getUploadUrl();
        File file = new File(getClass().getClassLoader().getResource("test.png").toURI());
        PhotoTokens photoTokens = uploadAPI.uploadImage(uploadUrl, file).execute();
        photoAR = new PhotoAttachmentRequest(new PhotoAttachmentRequestPayload(null, photoTokens.getPhotos()));

        String uploadUrl2 = getUploadUrl();
        File file2 = new File(getClass().getClassLoader().getResource("test2.png").toURI());
        PhotoTokens photoTokens2 = uploadAPI.uploadImage(uploadUrl2, file2).execute();
        photoAR2 = new PhotoAttachmentRequest(new PhotoAttachmentRequestPayload(null, photoTokens2.getPhotos()));

        List<Chat> allChats = getChats();
        chats = Arrays.asList(
                getByType(allChats, ChatType.DIALOG),
                getByType(allChats, ChatType.CHAT),
                getByType(allChats, ChatType.CHANNEL)
                );
    }

    @Test
    public void shouldEditText() throws Exception {
        for (Chat chat : chats) {
            NewMessageBody newMessageBody = new NewMessageBody(randomText(),
                    Collections.singletonList(photoAR));
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();
            NewMessageBody editedMessageBody = new NewMessageBody("edited message text", null);
            botAPI.editMessage(editedMessageBody, result.getMessageId()).execute();
            MessageBody lastMessage = getLast(chat).getMessage();

            assertThat(lastMessage.getText(), is(editedMessageBody.getText()));
            compare(photoAR, lastMessage.getAttachments().get(0));
        }
    }

    @Test
    public void shouldEditAttachments() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            List<AttachmentRequest> editAttachmentRequests = Collections.singletonList(photoAR2);
            NewMessageBody editedMessageBody = new NewMessageBody(null, editAttachmentRequests);
            botAPI.editMessage(editedMessageBody, result.getMessageId()).execute();
            MessageBody lastMessage = getLast(chat).getMessage();

            assertThat(lastMessage.getText(), is(text));
            compare(editAttachmentRequests, lastMessage.getAttachments());
        }
    }

    @Test
    public void shouldEditBothTextAndAttachments() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            List<AttachmentRequest> editAttachmentRequests = Collections.singletonList(photoAR2);
            String newText = "edited " + text;
            NewMessageBody editedMessageBody = new NewMessageBody(newText, editAttachmentRequests);
            botAPI.editMessage(editedMessageBody, result.getMessageId()).execute();
            MessageBody lastMessage = getLast(chat).getMessage();

            assertThat(lastMessage.getText(), is(newText));
            compare(editAttachmentRequests, lastMessage.getAttachments());
        }
    }

    @Test
    public void shouldRemoveAttachmentButLeaveText() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            NewMessageBody editedMessageBody = new NewMessageBody(null, Collections.emptyList());
            botAPI.editMessage(editedMessageBody, result.getMessageId()).execute();
            MessageBody lastMessage = getLast(chat).getMessage();

            assertThat("chatType: " + chat.getType(), lastMessage.getText(), is(text));
            assertThat("chatType: " + chat.getType(), lastMessage.getAttachments(), is(nullValue()));
        }
    }

    @Test
    public void shouldRemoveTextButLeaveAttachment() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            NewMessageBody editedMessageBody = new NewMessageBody("", null);
            botAPI.editMessage(editedMessageBody, result.getMessageId()).execute();
            MessageBody lastMessage = getLast(chat).getMessage();

            assertThat(lastMessage.getText().length(), is(0));
            compare(attachmentRequests, lastMessage.getAttachments());
        }
    }

    @Test(expected = APIException.class)
    public void cannotRemoveBothTextAndAttaches() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            NewMessageBody editedMessageBody = new NewMessageBody("", Collections.emptyList());
            botAPI.editMessage(editedMessageBody, result.getMessageId()).execute();
        }
    }

    @Test(expected = APIException.class)
    public void cannotRemoveTextWhenThereIsNoAttaches() throws Exception {
        for (Chat chat : chats) {
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, null);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            NewMessageBody editedMessageBody = new NewMessageBody("", null);
            botAPI.editMessage(editedMessageBody, result.getMessageId()).execute();
        }
    }

    @Test(expected = APIException.class)
    public void cannotRemoveAttachesWhenThereIsNoText() throws Exception {
        for (Chat chat : chats) {
            NewMessageBody newMessageBody = new NewMessageBody("", Collections.singletonList(photoAR));
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();
            NewMessageBody editedMessageBody = new NewMessageBody("", Collections.emptyList());
            botAPI.editMessage(editedMessageBody, result.getMessageId()).execute();
        }
    }

    private String getUploadUrl() throws Exception {
        String url = botAPI.getUploadUrl(UploadType.PHOTO).execute().getUrl();
        if (url.startsWith("http")) {
            return url;
        }

        return "http:" + url;
    }

    private Message getLast(Chat chat) throws Exception {
        return botAPI.getMessages(chat.getChatId()).count(1).execute().getMessages().get(0);
    }
}