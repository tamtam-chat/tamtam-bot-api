package chat.tamtam.botapi;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.AttachmentNotReadyException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.Attachment;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.model.FileAttachmentRequest;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageList;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;
import chat.tamtam.botapi.model.PhotoToken;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.UploadType;
import chat.tamtam.botapi.model.UploadedFileInfo;
import chat.tamtam.botapi.model.UploadedInfo;
import chat.tamtam.botapi.model.VideoAttachmentRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class TamTamBotAPITest extends TamTamIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Chat chat;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        List<Chat> allChats = new ArrayList<>();
        Long marker = null;
        do {
            ChatList chats = botAPI.getChats().marker(marker).count(2).execute();
            allChats.addAll(chats.getChats());
            marker = chats.getMarker();
        } while (marker != null);

        assertThat(allChats, is(not(empty())));
        chat = allChats.get(ThreadLocalRandom.current().nextInt(allChats.size()));
    }

    @Test
    public void should_Send_Text_Message_To_Chat() throws Exception {
        Long chatId = chat.getChatId();
        sendMessageAndCheck(chatId, null);
    }

    @Test
    public void should_Send_Message_With_Photo_To_Chat() throws Exception {
        Long chatId = chat.getChatId();
        String uploadUrl = fixUrl(botAPI.getUploadUrl(UploadType.PHOTO).execute().getUrl());
        InputStream logo = getClass().getClassLoader().getResourceAsStream("test.png");
        Map<String, PhotoToken> tokens = uploadAPI.uploadImage(uploadUrl, "test.png", logo).execute().getPhotos();
        PhotoAttachmentRequestPayload photoAttachPayload = new PhotoAttachmentRequestPayload(null, tokens);
        AttachmentRequest photoAttachment = new PhotoAttachmentRequest(photoAttachPayload);
        sendMessageAndCheck(chatId, Collections.singletonList(photoAttachment));
    }

    @Test
    public void should_Send_Message_With_Video_To_Chat() throws Exception {
        Long chatId = chat.getChatId();
        String uploadUrl = fixUrl(botAPI.getUploadUrl(UploadType.VIDEO).execute().getUrl());
        String fileName = "test.mp4";
        InputStream video = getClass().getClassLoader().getResourceAsStream(fileName);
        UploadedInfo payload = uploadAPI.uploadAV(uploadUrl, fileName, video).execute();
        AttachmentRequest videoAttach = new VideoAttachmentRequest(payload);
        sendMessageAndCheck(chatId, Collections.singletonList(videoAttach));
    }

    @Test
    public void should_Send_Message_With_File_To_Chat() throws Exception {
        Long chatId = chat.getChatId();
        String uploadUrl = fixUrl(botAPI.getUploadUrl(UploadType.FILE).execute().getUrl());
        String fileName = "test.txt";
        InputStream video = getClass().getClassLoader().getResourceAsStream(fileName);
        UploadedFileInfo payload = uploadAPI.uploadFile(uploadUrl, fileName, video).execute();
        AttachmentRequest fileAttach = new FileAttachmentRequest(payload);
        sendMessageAndCheck(chatId, Collections.singletonList(fileAttach));
    }

    @Test
    public void should_Upload_Image_By_Url() throws Exception {
        Long chatId = chat.getChatId();
        PhotoAttachmentRequestPayload payload = new PhotoAttachmentRequestPayload("https://chart.googleapis.com/chart?cht=p&chd=t:60,40&chs=600x400&chl=Hello%7CWorld", null);
        AttachmentRequest photoAttach = new PhotoAttachmentRequest(payload);
        sendMessageAndCheck(chatId, Collections.singletonList(photoAttach));
    }

    private static String fixUrl(String url) {
        if (url.startsWith("http")) {
            return url;
        }

        if (url.startsWith("//")) {
            return "http:" + url;
        }

        return "http://" + url;
    }

    private void sendMessageAndCheck(Long chatId, List<AttachmentRequest> attachments) throws Exception {
        LOG.info("Sending message to chat: " + chatId);
        Message latestMessage = getLatestMessage(chatId);
        NewMessageBody newMessage = new NewMessageBody("message created at " + LocalDateTime.now(), attachments);
        SendMessageResult sendMessageResult = null;
        do {
            try {
                sendMessageResult = botAPI.sendMessage(newMessage).chatId(chatId).execute();
                assertThat(sendMessageResult, is(notNullValue()));
            } catch (AttachmentNotReadyException e) {
                LOG.info("Uploaded attachment is not processed yet. Waiting…");
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            }
        } while (sendMessageResult == null);

        Message sentMessage = getLatestMessage(chatId);
        assertThat(sendMessageResult.getMessageId(), is(not(latestMessage.getMessage().getMid())));
        assertThat(sendMessageResult.getMessageId(), is(sentMessage.getMessage().getMid()));

        if (attachments != null) {
            List<Attachment> sentAttachments = sentMessage.getMessage().getAttachments();
            assertThat(sentAttachments.size(), is(attachments.size()));
        }
    }

    @Nullable
    private Message getLatestMessage(Long chatId) throws APIException, ClientException {
        MessageList latestMessagesResult = botAPI.getMessages(chatId).count(2).execute();
        List<Message> latestMessages = latestMessagesResult.getMessages();
        Message latestMessage = null;
        if (!latestMessages.isEmpty()) {
            latestMessage = latestMessages.get(0);
        }

        return latestMessage;
    }
}