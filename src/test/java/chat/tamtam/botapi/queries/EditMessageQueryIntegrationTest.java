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
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.ContactAttachmentRequest;
import chat.tamtam.botapi.model.ContactAttachmentRequestPayload;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageBody;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;
import chat.tamtam.botapi.model.PhotoTokens;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.SimpleQueryResult;
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
        String uploadUrl = getUploadUrl(UploadType.PHOTO);
        File file = new File(getClass().getClassLoader().getResource("test.png").toURI());
        PhotoTokens photoTokens = uploadAPI.uploadImage(uploadUrl, file).execute();
        photoAR = new PhotoAttachmentRequest(new PhotoAttachmentRequestPayload().photos(photoTokens.getPhotos()));

        String uploadUrl2 = getUploadUrl(UploadType.PHOTO);
        File file2 = new File(getClass().getClassLoader().getResource("test2.png").toURI());
        PhotoTokens photoTokens2 = uploadAPI.uploadImage(uploadUrl2, file2).execute();
        photoAR2 = new PhotoAttachmentRequest(new PhotoAttachmentRequestPayload().photos(photoTokens2.getPhotos()));

        List<Chat> allChats = getChats();
        chats = Arrays.asList(
                getByType(allChats, ChatType.DIALOG),
                getByTitle(allChats, "test chat #1"),
                getByTitle(allChats, "test channel #1")
        );
    }

    @Test
    public void shouldEditText() throws Exception {
        for (Chat chat : chats) {
            NewMessageBody newMessageBody = new NewMessageBody(randomText(), Collections.singletonList(photoAR), null);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();
            NewMessageBody editedMessageBody = new NewMessageBody("edited message text", null, null);
            String messageId = result.getMessage().getBody().getMid();
            botAPI.editMessage(editedMessageBody, messageId).execute();
            MessageBody lastMessage = getMessage(client, messageId).getBody();

            assertThat(lastMessage.getText(), is(editedMessageBody.getText()));
            compare(photoAR, lastMessage.getAttachments().get(0));
        }
    }

    @Test
    public void shouldEditAttachments() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests, null);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            List<AttachmentRequest> editAttachmentRequests = Collections.singletonList(photoAR2);
            NewMessageBody editedMessageBody = new NewMessageBody(null, editAttachmentRequests, null);
            String messageId = result.getMessage().getBody().getMid();
            botAPI.editMessage(editedMessageBody, messageId).execute();
            MessageBody lastMessage = getMessage(client, messageId).getBody();

            assertThat(lastMessage.getText(), is(text));
            compare(editAttachmentRequests, lastMessage.getAttachments());
        }
    }

    @Test
    public void shouldEditBothTextAndAttachments() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests, null);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            List<AttachmentRequest> editAttachmentRequests = Collections.singletonList(photoAR2);
            String newText = "edited " + text;
            NewMessageBody editedMessageBody = new NewMessageBody(newText, editAttachmentRequests, null);
            String messageId = result.getMessage().getBody().getMid();
            botAPI.editMessage(editedMessageBody, messageId).execute();
            MessageBody lastMessage = getMessage(client, messageId).getBody();

            assertThat(lastMessage.getText(), is(newText));
            compare(editAttachmentRequests, lastMessage.getAttachments());
        }
    }

    @Test
    public void shouldRemoveAttachmentButLeaveText() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests, null);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            NewMessageBody editedMessageBody = new NewMessageBody(null, Collections.emptyList(), null);
            String messageId = result.getMessage().getBody().getMid();
            botAPI.editMessage(editedMessageBody, messageId).execute();
            MessageBody lastMessage = getMessage(client, messageId).getBody();

            assertThat("chatType: " + chat.getType(), lastMessage.getText(), is(text));
            assertThat("chatType: " + chat.getType(), lastMessage.getAttachments(), is(nullValue()));
        }
    }

    @Test
    public void shouldRemoveTextButLeaveAttachment() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests, null);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            NewMessageBody editedMessageBody = new NewMessageBody("", null, null);
            String messageId = result.getMessage().getBody().getMid();
            botAPI.editMessage(editedMessageBody, messageId).execute();
            MessageBody lastMessage = getMessage(client, messageId).getBody();

            assertThat(lastMessage.getText().length(), is(0));
            compare(attachmentRequests, lastMessage.getAttachments());
        }
    }

    @Test
    public void cannotRemoveBothTextAndAttaches() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests, null);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            NewMessageBody editedMessageBody = new NewMessageBody("", Collections.emptyList(), null);
            SimpleQueryResult queryResult = botAPI.editMessage(editedMessageBody,
                    result.getMessage().getBody().getMid()).execute();
            assertThat(queryResult.isSuccess(), is(false));
        }
    }

    @Test
    public void cannotRemoveTextWhenThereIsNoAttaches() throws Exception {
        for (Chat chat : chats) {
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, null, null);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            NewMessageBody editedMessageBody = new NewMessageBody("", null, null);
            SimpleQueryResult queryResult = botAPI.editMessage(editedMessageBody,
                    result.getMessage().getBody().getMid()).execute();
            assertThat(queryResult.isSuccess(), is(false));
        }
    }

    @Test
    public void cannotRemoveAttachesWhenThereIsNoText() throws Exception {
        for (Chat chat : chats) {
            NewMessageBody newMessageBody = new NewMessageBody("", Collections.singletonList(photoAR), null);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();
            NewMessageBody editedMessageBody = new NewMessageBody("", Collections.emptyList(), null);
            SimpleQueryResult queryResult = botAPI.editMessage(editedMessageBody,
                    result.getMessage().getBody().getMid()).execute();
            assertThat(queryResult.isSuccess(), is(false));
        }
    }

    @Test
    public void shouldEditSingleAttachment() throws Exception {
        for (Chat chat : chats) {
            List<AttachmentRequest> attachmentRequests = Collections.singletonList(photoAR);
            String text = randomText();
            NewMessageBody newMessageBody = new NewMessageBody(text, attachmentRequests, null);
            SendMessageResult result = botAPI.sendMessage(newMessageBody).chatId(chat.getChatId()).execute();

            ContactAttachmentRequestPayload arPayload = new ContactAttachmentRequestPayload("test name",
                    bot1.getUserId(), null, "+79991234567");
            ContactAttachmentRequest contactAR = new ContactAttachmentRequest(arPayload);
            NewMessageBody editedMessageBody = new NewMessageBody(null, null, null)
                    .attachment(contactAR);

            String messageId = result.getMessage().getBody().getMid();
            botAPI.editMessage(editedMessageBody, messageId).execute();
            MessageBody lastMessage = getMessage(client, messageId).getBody();

            assertThat(lastMessage.getText(), is(text));
            compare(Collections.singletonList(contactAR), lastMessage.getAttachments());
        }
    }
}