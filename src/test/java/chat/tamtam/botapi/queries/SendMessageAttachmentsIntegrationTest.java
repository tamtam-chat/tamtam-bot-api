package chat.tamtam.botapi.queries;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.Button;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.FileAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequestPayload;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.RequestGeoLocationButton;
import chat.tamtam.botapi.model.UploadEndpoint;
import chat.tamtam.botapi.model.UploadType;
import chat.tamtam.botapi.model.UploadedInfo;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class SendMessageAttachmentsIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shoudThrowExceptionWhenSendManyInlineKeyboards() throws Exception {
        List<List<Button>> buttons = Collections.singletonList(
                Collections.singletonList(new RequestGeoLocationButton("text")));
        InlineKeyboardAttachmentRequest keyboard = new InlineKeyboardAttachmentRequest(
                new InlineKeyboardAttachmentRequestPayload(buttons));
        List<AttachmentRequest> attaches = Arrays.asList(keyboard, keyboard);
        NewMessageBody newMessage = new NewMessageBody(null, attaches, null);
        sendAndVerify(newMessage);
    }

    @Test
    public void shoudThrowExceptionWhenSendManyFiles() throws Exception {
        AttachmentRequest file = new FileAttachmentRequest(new UploadedInfo().token("sometoken"));
        List<AttachmentRequest> attaches = Arrays.asList(file, file);
        NewMessageBody newMessage = new NewMessageBody(null, attaches, null);
        sendAndVerify(newMessage);
    }

    @Test
    public void shoudThrowExceptionWhenSendManyFilesWithKeyboard() throws Exception {
        AttachmentRequest file = new FileAttachmentRequest(new UploadedInfo().token("sometoken"));
        List<List<Button>> buttons = Collections.singletonList(
                Collections.singletonList(new RequestGeoLocationButton("text")));
        InlineKeyboardAttachmentRequest keyboard = new InlineKeyboardAttachmentRequest(
                new InlineKeyboardAttachmentRequestPayload(buttons));
        List<AttachmentRequest> attaches = Arrays.asList(file, file, keyboard);
        NewMessageBody newMessage = new NewMessageBody(null, attaches, null);
        sendAndVerify(newMessage);
    }

    @Test
    public void shoudSendOneFileWithKeyboard() throws Exception {
        UploadEndpoint uploadEndpoint = botAPI.getUploadUrl(UploadType.FILE).execute();
        String fileName = "test.txt";
        File file = new File(getClass().getClassLoader().getResource(fileName).toURI());
        UploadedInfo uploadedFileInfo = uploadAPI.uploadFile(uploadEndpoint.getUrl(), file).execute();
        AttachmentRequest fileAttachmentRequest = new FileAttachmentRequest(uploadedFileInfo);
        List<List<Button>> buttons = Collections.singletonList(
                Collections.singletonList(new RequestGeoLocationButton("text").quick(true)));
        InlineKeyboardAttachmentRequest keyboard = new InlineKeyboardAttachmentRequest(
                new InlineKeyboardAttachmentRequestPayload(buttons));
        List<AttachmentRequest> attaches = Arrays.asList(fileAttachmentRequest, keyboard);
        NewMessageBody newMessage = new NewMessageBody(null, attaches, null);
        send(newMessage, getChatsForSend());
    }

    private void sendAndVerify(NewMessageBody newMessageBody) throws Exception {
        int exceptions = 0;
        List<Chat> chats = getChatsCanSend();
        for (Chat chat : chats) {
            try {
                doSend(newMessageBody, chat.getChatId());
                fail(chat.getType() + " should fail");
            } catch (APIException e) {
                exceptions++;
            }
        }

        assertThat(exceptions, is(chats.size()));
    }
}
