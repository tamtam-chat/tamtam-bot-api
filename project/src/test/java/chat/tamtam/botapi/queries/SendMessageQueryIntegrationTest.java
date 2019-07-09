package chat.tamtam.botapi.queries;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.AudioAttachment;
import chat.tamtam.botapi.model.AudioAttachmentRequest;
import chat.tamtam.botapi.model.Button;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.ContactAttachmentRequest;
import chat.tamtam.botapi.model.ContactAttachmentRequestPayload;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequestPayload;
import chat.tamtam.botapi.model.Intent;
import chat.tamtam.botapi.model.LinkButton;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageLinkType;
import chat.tamtam.botapi.model.MessageList;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.NewMessageLink;
import chat.tamtam.botapi.model.PhotoAttachment;
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;
import chat.tamtam.botapi.model.RequestContactButton;
import chat.tamtam.botapi.model.RequestGeoLocationButton;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.StickerAttachment;
import chat.tamtam.botapi.model.StickerAttachmentRequest;
import chat.tamtam.botapi.model.StickerAttachmentRequestPayload;
import chat.tamtam.botapi.model.UploadType;
import chat.tamtam.botapi.model.UploadedInfo;
import chat.tamtam.botapi.model.UserWithPhoto;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
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
    public void shouldThrowExceptionWhenSendButtonsOnly() throws Exception {
        AttachmentRequest keyboard = new InlineKeyboardAttachmentRequest(new InlineKeyboardAttachmentRequestPayload(
                Collections.singletonList(Collections.singletonList(new CallbackButton("test", "ok")))
        ));

        NewMessageBody newMessage = new NewMessageBody(null, Collections.singletonList(keyboard), null);
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
    public void shouldThrowExceptionWhenTextIsTooLong() throws Exception {
        NewMessageBody newMessage = new NewMessageBody(randomText(4001), null, null);
        List<Chat> chats = getChats();
        Chat dialog = getByType(chats, ChatType.DIALOG);
        Chat chat = getByTitle(chats, "test chat #1");
        Chat channel = getByTitle(chats, "test channel #1");

        int exceptions = 0;
        List<Chat> list = Arrays.asList(dialog, chat, channel);
        for (Chat c : list) {
            try {
                doSend(newMessage, c.getChatId());
            } catch (Exception e) {
                exceptions++;
                LOG.error(e.getMessage(), e);
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
                    new UploadedInfo().token(attachment.getPayload().getToken()));

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

    @Test
    public void shouldContainsSenderAndRecipientInDialog() throws Exception {
        Chat dialog = getByType(getChats(), ChatType.DIALOG);
        NewMessageBody newMessage = new NewMessageBody(randomText(), null, null);
        SendMessageResult result = doSend(newMessage, dialog.getChatId());
        assertThat(result.getMessage().getSender().getUserId(), is(me.getUserId()));
        assertThat(result.getMessage().getRecipient().getChatId(), is(dialog.getChatId()));
        assertThat(result.getMessage().getRecipient().getUserId(), is(not(me.getUserId())));
    }

    @Test
    public void shouldNOTContainsRecipientUserIdInChat() throws Exception {
        Chat chat = getByTitle(getChats(), "test chat #4");
        NewMessageBody newMessage = new NewMessageBody(randomText(), null, null);
        SendMessageResult result = doSend(newMessage, chat.getChatId());
        assertThat(result.getMessage().getSender().getUserId(), is(me.getUserId()));
        assertThat(result.getMessage().getRecipient().getChatId(), is(chat.getChatId()));
        assertThat(result.getMessage().getRecipient().getUserId(), is(nullValue()));
    }

    @Test
    public void shoulReturnSenderInChannelIfSigned() throws Exception {
        Chat channel = getByTitle(getChats(), "test channel #3");
        NewMessageBody newMessage = new NewMessageBody(randomText(), null, null);
        SendMessageResult result = doSend(newMessage, channel.getChatId());
        assertThat(result.getMessage().getSender().getUserId(), is(me.getUserId()));
        assertThat(result.getMessage().getSender().getName(), is(me.getName()));
    }

    @Test
    public void should_NOT_ReturnSenderInChannelIf_NOT_Signed() throws Exception {
        Chat channel = getByTitle(getChats(), "test channel #1");
        NewMessageBody newMessage = new NewMessageBody(randomText(), null, null);
        SendMessageResult result = doSend(newMessage, channel.getChatId());
        assertThat(result.getMessage().getSender(), is(nullValue()));
    }

    @Test
    public void shouldSendSticker() throws Exception {
        Chat chat = getByTitle(getChats(client), "SendMessageQueryIntegrationTest#shouldSendSticker");
        MessageList messageList = new GetMessagesQuery(client).chatId(chat.getChatId()).count(1).execute();
        Message message = messageList.getMessages().get(0);
        StickerAttachment stickerAttach = (StickerAttachment) message.getBody().getAttachments().get(0);
        AttachmentRequest stickerAR = new StickerAttachmentRequest(new StickerAttachmentRequestPayload(stickerAttach.getPayload().getCode()));
        send(new NewMessageBody(null, Collections.singletonList(stickerAR), null));
    }

    @Test
    public void shouldSendWithSpecialCharacter() throws Exception {
        String text = randomText(3999) + "<";
        NewMessageBody newMessage = new NewMessageBody(text, null, null);
        Chat dialog = getByType(getChats(), ChatType.DIALOG);
        SendMessageResult result = doSend(newMessage, dialog.getChatId());
        assertThat(result.getMessage().getBody().getText(), is(text));
    }

    private List<Message> send(NewMessageBody newMessage) throws Exception {
        return send(newMessage, getChatsForSend());
    }
}
