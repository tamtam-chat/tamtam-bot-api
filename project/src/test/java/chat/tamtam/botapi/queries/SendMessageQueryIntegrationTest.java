package chat.tamtam.botapi.queries;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.AttachmentNotReadyException;
import chat.tamtam.botapi.model.Attachment;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.AudioAttachmentRequest;
import chat.tamtam.botapi.model.Button;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.ContactAttachmentRequest;
import chat.tamtam.botapi.model.ContactAttachmentRequestPayload;
import chat.tamtam.botapi.model.FileAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequestPayload;
import chat.tamtam.botapi.model.Intent;
import chat.tamtam.botapi.model.LinkButton;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageList;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;
import chat.tamtam.botapi.model.PhotoTokens;
import chat.tamtam.botapi.model.RequestContactButton;
import chat.tamtam.botapi.model.RequestGeoLocationButton;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.UploadEndpoint;
import chat.tamtam.botapi.model.UploadType;
import chat.tamtam.botapi.model.UploadedFileInfo;
import chat.tamtam.botapi.model.UploadedInfo;
import chat.tamtam.botapi.model.UserWithPhoto;
import chat.tamtam.botapi.model.VideoAttachmentRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class SendMessageQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldSendSimpleTextMessage() throws Exception {
        NewMessageBody newMessage = new NewMessageBody("text", null);
        send(newMessage);
    }

    @Test
    public void shouldThrowException() throws Exception {
        NewMessageBody newMessage = new NewMessageBody(null, null);
        List<Chat> chats = getChats();
        Chat dialog = getByType(chats, ChatType.DIALOG);
        Chat chat = getByType(chats, ChatType.CHAT);
        Chat channel = getByType(chats, ChatType.CHANNEL);

        int exceptions = 0;
        List<Chat> list = Arrays.asList(dialog, chat, channel);
        for (Chat c : list) {
            try {
                doSend(newMessage, c);
            } catch (Exception e) {
                exceptions++;
            }
        }

        if (exceptions != list.size()) {
            fail();
        }
    }

    @Test
    public void shouldSendKeyboard() throws Exception {
        List<List<Button>> buttons = Arrays.asList(
                Arrays.asList(
                        new CallbackButton("payload", "text", Intent.DEFAULT),
                        new CallbackButton("payload2", "text", Intent.NEGATIVE),
                        new CallbackButton("payload2", "text", Intent.POSITIVE)
                ),
                Arrays.asList(
                        new LinkButton("https://mail.ru", "link", Intent.DEFAULT),
                        new LinkButton("https://tt.me/beer", "link", Intent.DEFAULT)
                ),
                Collections.singletonList(
                        new RequestContactButton("contact", Intent.DEFAULT)
                ),
                Collections.singletonList(
                        new RequestGeoLocationButton("geo location", Intent.DEFAULT).quick(true)
                )
        );

        InlineKeyboardAttachmentRequestPayload keyboard = new InlineKeyboardAttachmentRequestPayload(buttons);
        AttachmentRequest attach = new InlineKeyboardAttachmentRequest(keyboard);
        NewMessageBody newMessage = new NewMessageBody("keyboard", Collections.singletonList(attach));
        send(newMessage);
    }

    @Test
    public void shouldSendPhoto() throws Exception {
        UploadEndpoint uploadEndpoint = botAPI.getUploadUrl(UploadType.PHOTO).execute();
        File file = new File(getClass().getClassLoader().getResource("test.png").toURI());
        PhotoTokens photoTokens = uploadAPI.uploadImage(uploadEndpoint.getUrl(), file).execute();
        PhotoAttachmentRequestPayload payload = new PhotoAttachmentRequestPayload(null, photoTokens.getPhotos());
        AttachmentRequest attach = new PhotoAttachmentRequest(payload);
        NewMessageBody newMessage = new NewMessageBody("image", Collections.singletonList(attach));
        send(newMessage);
    }

    @Test
    public void shouldSendGifByURL() throws Exception {
        String url = "https://media1.giphy.com/media/2RCQECf4JBfoc/giphy.gif?cid=e1bb72ff5c936527514b67642ec770cf";
        PhotoAttachmentRequestPayload payload = new PhotoAttachmentRequestPayload(url, null);
        AttachmentRequest attach = new PhotoAttachmentRequest(payload);
        NewMessageBody newMessage = new NewMessageBody("image", Collections.singletonList(attach));
        send(newMessage);
    }

    @Test
    public void shouldSendVideo() throws Exception {
        UploadEndpoint uploadEndpoint = botAPI.getUploadUrl(UploadType.VIDEO).execute();
        File file = new File(getClass().getClassLoader().getResource("test.mp4").toURI());
        UploadedInfo uploadedInfo = uploadAPI.uploadAV(uploadEndpoint.getUrl(), file).execute();
        AttachmentRequest attach = new VideoAttachmentRequest(uploadedInfo);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(attach));
        send(newMessage);
    }

    @Test
    public void shouldSendFile() throws Exception {
        UploadEndpoint uploadEndpoint = botAPI.getUploadUrl(UploadType.FILE).execute();
        File file = new File(getClass().getClassLoader().getResource("test.txt").toURI());
        UploadedFileInfo uploadedFileInfo = uploadAPI.uploadFile(uploadEndpoint.getUrl(), file).execute();
        AttachmentRequest request = new FileAttachmentRequest(uploadedFileInfo);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(request));
        send(newMessage);
    }

    @Test
    public void shouldSendAudio() throws Exception {
        UploadEndpoint uploadEndpoint = botAPI.getUploadUrl(UploadType.AUDIO).execute();
        File file = new File(getClass().getClassLoader().getResource("test.m4a").toURI());
        UploadedInfo uploadedInfo = uploadAPI.uploadAV(uploadEndpoint.getUrl(), file).execute();
        AttachmentRequest request = new AudioAttachmentRequest(uploadedInfo);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(request));
        send(newMessage);
    }

    @Test
    public void shouldSendContact() throws Exception {
        UserWithPhoto me = getMe();
        ContactAttachmentRequestPayload payload = new ContactAttachmentRequestPayload(me.getName(), me.getUserId(), null, "+79991234567");
        AttachmentRequest request = new ContactAttachmentRequest(payload);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(request));
        send(newMessage);
    }

    private void send(NewMessageBody newMessage) throws Exception {
        List<Chat> chats = getChatsCanSend();
        Chat dialog = getByType(chats, ChatType.DIALOG);
        Chat chat = getByType(chats, ChatType.CHAT);
        Chat channel = getByType(chats, ChatType.CHANNEL);

        for (Chat c : Arrays.asList(dialog, chat, channel)) {
            if (c.getType() == ChatType.CHANNEL && !c.getTitle().contains("bot is admin")) {
                continue;
            }

            Long chatId = c.getChatId();
            SendMessageResult sendMessageResult = doSend(newMessage, c);
            assertThat(sendMessageResult, is(notNullValue()));

            if (c.getType() == ChatType.CHAT) {
                continue;
            }

            MessageList messageList = botAPI.getMessages(chatId).execute();
            Message lastMessage = messageList.getMessages().get(0);
            String text = newMessage.getText();
            assertThat(lastMessage.getMessage().getText(), is(text == null ? "" : text));
            assertThat(lastMessage.getMessage().getMid(), is(sendMessageResult.getMessageId()));

            List<AttachmentRequest> attachments = newMessage.getAttachments();
            if (attachments != null) {
                for (int i = 0; i < attachments.size(); i++) {
                    AttachmentRequest request = attachments.get(i);
                    Attachment attachment = lastMessage.getMessage().getAttachments().get(i);
                    compare(request, attachment);
                }
            }
        }
    }

    private SendMessageResult doSend(NewMessageBody newMessage, Chat chat) throws Exception {
        do {
            try {
                LOG.info("Sending message to chat: " + chat);
                return botAPI.sendMessage(newMessage).chatId(chat.getChatId()).execute();
            } catch (AttachmentNotReadyException e) {
                // it is ok, try again
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            }
        } while (true);
    }
}
