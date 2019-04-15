package chat.tamtam.botapi.queries;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import chat.tamtam.botapi.model.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.AttachmentNotReadyException;
import chat.tamtam.botapi.model.Attachment;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.AudioAttachment;
import chat.tamtam.botapi.model.AudioAttachmentRequest;
import chat.tamtam.botapi.model.Button;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.ContactAttachmentRequest;
import chat.tamtam.botapi.model.ContactAttachmentRequestPayload;
import chat.tamtam.botapi.model.FileAttachment;
import chat.tamtam.botapi.model.FileAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequestPayload;
import chat.tamtam.botapi.model.Intent;
import chat.tamtam.botapi.model.LinkButton;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageList;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PhotoAttachment;
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
import chat.tamtam.botapi.model.VideoAttachment;
import chat.tamtam.botapi.model.VideoAttachmentRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class SendMessageQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldSendSimpleTextMessage() throws Exception {
        NewMessageBody newMessage = new NewMessageBody("text", null, null);
        send(newMessage);
    }

    @Test
    public void shouldThrowException() throws Exception {
        NewMessageBody newMessage = new NewMessageBody(null, null, null);
        List<Chat> chats = getChats();
        Chat dialog = getByType(chats, ChatType.DIALOG);
        Chat chat = getByType(chats, ChatType.CHAT);
        Chat channel = getByType(chats, ChatType.CHANNEL);

        int exceptions = 0;
        List<Chat> list = Arrays.asList(dialog, chat, channel);
        for (Chat c : list) {
            try {
                doSend(newMessage, c.getChatId());
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
                        new CallbackButton("payload", "text"),
                        new CallbackButton("payload2", "text").intent(Intent.NEGATIVE),
                        new CallbackButton("payload2", "text").intent(Intent.POSITIVE)
                ),
                Arrays.asList(
                        new LinkButton("https://mail.ru", "link"),
                        new LinkButton("https://tt.me/beer", "link")
                ),
                Collections.singletonList(
                        new RequestContactButton("contact")
                ),
                Collections.singletonList(
                        new RequestGeoLocationButton("geo location").quick(true)
                )
        );

        InlineKeyboardAttachmentRequestPayload keyboard = new InlineKeyboardAttachmentRequestPayload(buttons);
        AttachmentRequest attach = new InlineKeyboardAttachmentRequest(keyboard);
        NewMessageBody newMessage = new NewMessageBody("keyboard", Collections.singletonList(attach), null);
        send(newMessage);
    }

    @Test
    public void shouldSendPhoto() throws Exception {
        AttachmentRequest attach = getPhotoAttachmentRequest();
        NewMessageBody newMessage = new NewMessageBody("image", Collections.singletonList(attach), null);
        send(newMessage);
    }

    @Test
    public void shouldSendPhotoAsSingleAttach() throws Exception {
        AttachmentRequest attach = getPhotoAttachmentRequest();
        NewMessageBody newMessage = new NewMessageBody(null, null, null).attachment(attach);
        send(newMessage);
    }

    @Test
    public void shouldSendPhotoByToken() throws Exception {
        List<Chat> chats = getChats();
        Chat dialog = getByType(chats, ChatType.DIALOG);
        Chat chat = getByTitle(chats, "test chat #4");
        Chat channel = getByTitle(chats, "test channel #1");

        AttachmentRequest attach = getPhotoAttachmentRequest();
        NewMessageBody newMessage = new NewMessageBody("image", Collections.singletonList(attach), null);

        SendMessageResult result = doSend(newMessage, dialog.getChatId());
        PhotoAttachment attachment = (PhotoAttachment) result.getMessage().getBody().getAttachments().get(0);
        String token = attachment.getPayload().getToken();

        newMessage = new NewMessageBody("image", Collections.singletonList(new PhotoAttachmentRequest(new PhotoAttachmentRequestPayload().token(token))), null);
        doSend(newMessage, chat.getChatId());
        doSend(newMessage, channel.getChatId());

        assertThat(result.getMessage().getBody().getAttachments(), is(not(empty())));
        assertThat(result.getMessage().getBody().getAttachments(), is(not(empty())));
    }

    @Test
    public void shouldSendGifByURL() throws Exception {
        String url = "https://media1.giphy.com/media/2RCQECf4JBfoc/giphy.gif?cid=e1bb72ff5c936527514b67642ec770cf";
        PhotoAttachmentRequestPayload payload = new PhotoAttachmentRequestPayload().url(url);
        AttachmentRequest attach = new PhotoAttachmentRequest(payload);
        NewMessageBody newMessage = new NewMessageBody("image", Collections.singletonList(attach), null);
        send(newMessage);
    }

    @Test
    public void shouldSendVideo() throws Exception {
        String uploadUrl = getUploadUrl(UploadType.VIDEO);
        File file = new File(getClass().getClassLoader().getResource("test.mp4").toURI());
        UploadedInfo uploadedInfo = uploadAPI.uploadAV(uploadUrl, file).execute();
        AttachmentRequest attach = new VideoAttachmentRequest(uploadedInfo);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(attach), null);
        send(newMessage);
    }

    @Test
    public void shouldSendVideoById() throws Exception {
        String uploadUrl = getUploadUrl(UploadType.VIDEO);
        File file = new File(getClass().getClassLoader().getResource("test.mp4").toURI());
        UploadedInfo uploadedInfo = uploadAPI.uploadAV(uploadUrl, file).execute();
        AttachmentRequest attach = new VideoAttachmentRequest(uploadedInfo);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(attach), null);
        List<Message> createdMessages = send(newMessage);
        for (Message createdMessage : createdMessages) {
            VideoAttachment attachment = (VideoAttachment) createdMessage.getBody().getAttachments().get(0);
            AttachmentRequest copyAttach = new VideoAttachmentRequest(
                    new UploadedInfo(attachment.getPayload().getId()));

            doSend(new NewMessageBody("resend with attach", Collections.singletonList(copyAttach), null),
                    createdMessage.getRecipient().getChatId());
        }
    }

    @Test
    public void shouldSendFile() throws Exception {
        UploadEndpoint uploadEndpoint = botAPI.getUploadUrl(UploadType.FILE).execute();
        File file = new File(getClass().getClassLoader().getResource("test.txt").toURI());
        UploadedFileInfo uploadedFileInfo = uploadAPI.uploadFile(uploadEndpoint.getUrl(), file).execute();
        AttachmentRequest request = new FileAttachmentRequest(uploadedFileInfo);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(request), null);
        send(newMessage);
    }

    @Test
    public void shouldSendFileReusingId() throws Exception {
        UploadEndpoint uploadEndpoint = botAPI.getUploadUrl(UploadType.FILE).execute();
        File file = new File(getClass().getClassLoader().getResource("test.txt").toURI());
        UploadedFileInfo uploadedFileInfo = uploadAPI.uploadFile(uploadEndpoint.getUrl(), file).execute();
        AttachmentRequest request = new FileAttachmentRequest(uploadedFileInfo);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(request), null);
        List<Message> createdMessages = send(newMessage);
        for (Message createdMessage : createdMessages) {
            FileAttachment attachment = (FileAttachment) createdMessage.getBody().getAttachments().get(0);
            FileAttachmentRequest copyAttach = new FileAttachmentRequest(
                    new UploadedFileInfo(attachment.getPayload().getFileId()));

            doSend(new NewMessageBody("resend with attach", Collections.singletonList(copyAttach), null),
                    createdMessage.getRecipient().getChatId());
        }
    }

    @Test
    public void shouldSendAudio() throws Exception {
        String uploadUrl = getUploadUrl(UploadType.AUDIO);
        File file = new File(getClass().getClassLoader().getResource("test.m4a").toURI());
        UploadedInfo uploadedInfo = uploadAPI.uploadAV(uploadUrl, file).execute();
        AttachmentRequest request = new AudioAttachmentRequest(uploadedInfo);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(request), null);
        send(newMessage);
    }

    @Test
    public void shouldSendAudioReusingId() throws Exception {
        String uploadUrl = getUploadUrl(UploadType.AUDIO);
        File file = new File(getClass().getClassLoader().getResource("test.m4a").toURI());
        UploadedInfo uploadedInfo = uploadAPI.uploadAV(uploadUrl, file).execute();
        AttachmentRequest request = new AudioAttachmentRequest(uploadedInfo);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(request), null);
        List<Message> createdMessages = send(newMessage);
        for (Message createdMessage : createdMessages) {
            AudioAttachment attachment = (AudioAttachment) createdMessage.getBody().getAttachments().get(0);
            AudioAttachmentRequest copyAttach = new AudioAttachmentRequest(
                    new UploadedInfo(attachment.getPayload().getId()));

            doSend(new NewMessageBody("resend with attach", Collections.singletonList(copyAttach), null),
                    createdMessage.getRecipient().getChatId());
        }
    }

    @Test
    public void shouldSendContact() throws Exception {
        UserWithPhoto me = getMe();
        ContactAttachmentRequestPayload payload = new ContactAttachmentRequestPayload(me.getName(), me.getUserId(), null, "+79991234567");
        AttachmentRequest request = new ContactAttachmentRequest(payload);
        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(request), null);
        send(newMessage);
    }

    @Test
    public void shouldSendForward() throws Exception {
        NewMessageBody messageToForward = new NewMessageBody("message to forward", null, null);
        List<Message> sent = send(messageToForward);
        for (Message message : sent) {
            String mid = message.getBody().getMid();
            send(new NewMessageBody(null, null, new NewMessageLink(MessageLinkType.FORWARD, mid)));
        }
    }

    @Test
    public void shouldSendForwardWithAttach() throws Exception {
        NewMessageBody messageToForward = new NewMessageBody("message to forward", null, null);
        List<Message> sent = send(messageToForward);
        for (Message message : sent) {
            String mid = message.getBody().getMid();
            send(new NewMessageBody(null, Collections.singletonList(getPhotoAttachmentRequest()),
                    new NewMessageLink(MessageLinkType.FORWARD, mid)));
        }
    }

    @Test
    public void shouldSendReply() throws Exception {
        for (Chat chat : getChatsForSend()) {
            NewMessageBody messageToForward = new NewMessageBody("message to reply", null, null);
            List<Message> sent = send(messageToForward, Collections.singletonList(chat));
            for (Message message : sent) {
                String mid = message.getBody().getMid();
                NewMessageLink link = new NewMessageLink(MessageLinkType.REPLY, mid);
                send(new NewMessageBody("Reply", null, link), Collections.singletonList(chat));
            }
        }
    }

    @Test
    public void shouldSendReplyWithAttach() throws Exception {
        for (Chat chat : getChatsForSend()) {
            NewMessageBody messageToForward = new NewMessageBody("message to reply", null, null);
            List<Message> sent = send(messageToForward, Collections.singletonList(chat));
            for (Message message : sent) {
                String mid = message.getBody().getMid();
                NewMessageLink link = new NewMessageLink(MessageLinkType.REPLY, mid);
                send(new NewMessageBody("Reply with attach", Collections.singletonList(getPhotoAttachmentRequest()),
                        link), Collections.singletonList(chat));
            }
        }
    }

    @NotNull
    private AttachmentRequest getPhotoAttachmentRequest() throws Exception {
        String uploadUrl = getUploadUrl(UploadType.PHOTO);
        File file = new File(getClass().getClassLoader().getResource("test.png").toURI());
        PhotoTokens photoTokens = uploadAPI.uploadImage(uploadUrl, file).execute();
        PhotoAttachmentRequestPayload payload = new PhotoAttachmentRequestPayload().photos(photoTokens.getPhotos());
        return new PhotoAttachmentRequest(payload);
    }


    private List<Chat> getChatsForSend() throws Exception {
        List<Chat> chats = getChats();
        Chat dialog = getByType(chats, ChatType.DIALOG);
        Chat chat = getByTitle(chats, "test chat #4");
        Chat channel = getByTitle(chats, "test channel #1");
        return Arrays.asList(dialog, chat, channel);
    }

    private List<Message> send(NewMessageBody newMessage) throws Exception {
        return send(newMessage, getChatsForSend());
    }

    private List<Message> send(NewMessageBody newMessage, List<Chat> toChats) throws Exception {
        List<Message> sent = new ArrayList<>();
        for (Chat c : toChats) {
            SendMessageResult sendMessageResult = doSend(newMessage, c.getChatId());
            sent.add(sendMessageResult.getMessage());
        }

        return sent;
    }

    private SendMessageResult doSend(NewMessageBody newMessage, Long chatId) throws Exception {
        do {
            try {
                SendMessageResult sendMessageResult = botAPI.sendMessage(newMessage).chatId(chatId).execute();
                assertThat(sendMessageResult, is(notNullValue()));
                MessageList messageList = botAPI.getMessages().chatId(chatId).execute();
                Message lastMessage = messageList.getMessages().get(0);
                String text = newMessage.getText();
                assertThat(lastMessage.getBody().getText(), is(text == null ? "" : text));
                assertThat(lastMessage.getBody().getMid(), is(sendMessageResult.getMessage().getBody().getMid()));

                List<AttachmentRequest> attachments = newMessage.getAttachments();
                if (attachments != null) {
                    for (int i = 0; i < attachments.size(); i++) {
                        AttachmentRequest request = attachments.get(i);
                        Attachment attachment = lastMessage.getBody().getAttachments().get(i);
                        compare(request, attachment);
                    }
                }

                NewMessageLink link = newMessage.getLink();
                if (link != null) {
                    LinkedMessage linkedMessage = lastMessage.getLink();
                    assertThat(linkedMessage, is(notNullValue()));
                    compare(linkedMessage, link);
                }

                return sendMessageResult;
            } catch (AttachmentNotReadyException e) {
                // it is ok, try again
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            }
        } while (true);
    }

    private String getUploadUrl(UploadType uploadType) throws Exception {
        String url = botAPI.getUploadUrl(uploadType).execute().getUrl();
        if (url.startsWith("http")) {
            return url;
        }

        return "http:" + url;
    }

}
