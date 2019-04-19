package chat.tamtam.botapi;

import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.client.impl.OkHttpTransportClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.Attachment;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.AudioAttachment;
import chat.tamtam.botapi.model.AudioAttachmentRequest;
import chat.tamtam.botapi.model.Button;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.model.ChatStatus;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.ContactAttachment;
import chat.tamtam.botapi.model.ContactAttachmentRequest;
import chat.tamtam.botapi.model.FileAttachment;
import chat.tamtam.botapi.model.FileAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachment;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequest;
import chat.tamtam.botapi.model.Intent;
import chat.tamtam.botapi.model.LinkButton;
import chat.tamtam.botapi.model.LocationAttachmentRequest;
import chat.tamtam.botapi.model.LinkedMessage;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageLinkType;
import chat.tamtam.botapi.model.NewMessageLink;
import chat.tamtam.botapi.model.PhotoAttachment;
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import chat.tamtam.botapi.model.RequestContactButton;
import chat.tamtam.botapi.model.RequestGeoLocationButton;
import chat.tamtam.botapi.model.StickerAttachmentRequest;
import chat.tamtam.botapi.model.User;
import chat.tamtam.botapi.model.UserWithPhoto;
import chat.tamtam.botapi.model.VideoAttachment;
import chat.tamtam.botapi.model.VideoAttachmentRequest;
import chat.tamtam.botapi.queries.GetMyInfoQuery;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
@Category(IntegrationTest.class)
public abstract class TamTamIntegrationTest {
    protected static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    protected static final AtomicLong ID_COUNTER = new AtomicLong();
    private static final String TOKEN_1 = getToken("TAMTAM_BOTAPI_TOKEN");
    private static final String TOKEN_2 = getToken("TAMTAM_BOTAPI_TOKEN_2");

    private final OkHttpTransportClient transportClient = new OkHttpTransportClient();
    private final JacksonSerializer serializer = new JacksonSerializer();

    protected TamTamClient client = new TamTamClient(TOKEN_1, transportClient, serializer);
    protected TamTamClient client2 = new TamTamClient(TOKEN_2, transportClient, serializer);
    protected TamTamBotAPI botAPI = new TamTamBotAPI(client);
    protected TamTamUploadAPI uploadAPI = new TamTamUploadAPI(client);

    protected User me;
    protected User bot2;

    @Before
    public void setUp() throws Exception {
        me = getMe();
        bot2 = new GetMyInfoQuery(client2).execute();
        LOG.info("Endpoint: {}", client.getEndpoint());
    }

    protected UserWithPhoto getMe() throws APIException, ClientException {
        return botAPI.getMyInfo().execute();
    }

    protected List<Chat> getChats() throws APIException, ClientException {
        ChatList chatList = botAPI.getChats().count(100).execute();
        return chatList.getChats();
    }

    protected Chat getChat(long chatId) throws APIException, ClientException {
        return botAPI.getChat(chatId).execute();
    }

    protected List<Chat> getChatsCanSend() throws APIException, ClientException {
        ChatList chatList = botAPI.getChats().count(10).execute();
        return chatList.getChats().stream()
                .filter(c -> {
                    if (c.getType() == ChatType.CHANNEL) {
                        return c.getTitle().contains("bot is admin");
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    protected static <T> T random(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    protected Chat getByType(List<Chat> chats, ChatType type) throws Exception {
        return chats.stream()
                .filter(c -> c.getType() == type)
                .filter(c -> c.getStatus() == ChatStatus.ACTIVE)
                .findFirst()
                .orElseThrow(notFound(type.getValue()));
    }

    protected Chat getByTitle(List<Chat> chats, String title) throws Exception {
        return chats.stream()
                .filter(c -> title.equals(c.getTitle()))
                .filter(c -> c.getStatus() == ChatStatus.ACTIVE)
                .findFirst()
                .orElseThrow(notFound(title));
    }

    protected static void compare(List<AttachmentRequest> attachmentRequests, List<Attachment> attachments) {
        Iterator<Attachment> attachmentIterator = attachments.iterator();
        for (AttachmentRequest request : attachmentRequests) {
            compare(request, attachmentIterator.next());
        }
    }

    protected static void compare(AttachmentRequest attachmentRequest, Attachment attachment) {
        attachmentRequest.visit(new AttachmentRequest.Visitor() {
            @Override
            public void visit(PhotoAttachmentRequest model) {
                compare(model, ((PhotoAttachment) attachment));
            }

            @Override
            public void visit(VideoAttachmentRequest model) {
                compare(model, ((VideoAttachment) attachment));
            }

            @Override
            public void visit(AudioAttachmentRequest model) {
                compare(model, ((AudioAttachment) attachment));
            }

            @Override
            public void visit(InlineKeyboardAttachmentRequest model) {
                compare(model, (InlineKeyboardAttachment) attachment);
            }

            @Override
            public void visit(LocationAttachmentRequest model) {

            }

            @Override
            public void visit(FileAttachmentRequest model) {
                compare(model, ((FileAttachment) attachment));
            }

            @Override
            public void visit(StickerAttachmentRequest model) {

            }

            @Override
            public void visit(ContactAttachmentRequest model) {
                compare(model, ((ContactAttachment) attachment));
            }

            @Override
            public void visitDefault(AttachmentRequest model) {

            }
        });
    }

    protected static void compare(LinkedMessage linkedMessage, NewMessageLink link) {
        if (link.getType() == MessageLinkType.REPLY) {
            assertThat(getMessageId(linkedMessage.getMessage().getMid()),
                    is(getMessageId(link.getMid())));
        } else {
            assertThat(linkedMessage.getMessage().getMid(), is(link.getMid()));
        }
        assertThat(linkedMessage.getType(), is(link.getType()));
    }

    private static String getMessageId(String mid) {
        return mid.substring("mid.".length() + 16);
    }

    protected Message getLast(Chat chat) throws Exception {
        return botAPI.getMessages().chatId(chat.getChatId()).count(1).execute().getMessages().get(0);
    }

    protected static String randomText() {
        return UUID.randomUUID().toString();
    }

    private static String getToken(String envVar) {
        String tokenEnv = System.getenv(envVar);
        if (tokenEnv != null) {
            return tokenEnv;
        }

        throw new NullPointerException("No token provided. Please set " + envVar + " environment variable.");
    }

    private static Supplier<Exception> notFound(String entity) {
        return () -> new RuntimeException(entity + " not found");
    }

    private static void compare(InlineKeyboardAttachmentRequest request, InlineKeyboardAttachment attachment) {
        List<List<Button>> expected = request.getPayload().getButtons();
        List<List<Button>> actual = attachment.getPayload().getButtons();
        for (int i = 0; i < actual.size(); i++) {
            for (int j = 0; j < actual.get(i).size(); j++) {
                Button actualButton = actual.get(i).get(j);
                Button expectedButton = expected.get(i).get(j);
                compare(actualButton, expectedButton);
            }
        }
    }

    private static void compare(Button actualButton, Button expectedButton) {
        actualButton.visit(new Button.Visitor() {
            @Override
            public void visit(CallbackButton model) {
                CallbackButton expectedCallbackButton = (CallbackButton) expectedButton;
                assertThat(model.getPayload(), is(expectedCallbackButton.getPayload()));
                assertThat(model.getText(), is(expectedCallbackButton.getText()));
                if (expectedCallbackButton.getIntent() != null) {
                    assertThat(model.getIntent(), is(expectedCallbackButton.getIntent()));
                } else {
                    assertThat(model.getIntent(), is(Intent.DEFAULT));
                }
            }

            @Override
            public void visit(LinkButton model) {
                assertThat(model, is(expectedButton));
            }

            @Override
            public void visit(RequestGeoLocationButton model) {
                assertThat(model, is(expectedButton));
            }

            @Override
            public void visit(RequestContactButton model) {
                assertThat(model, is(expectedButton));
            }

            @Override
            public void visitDefault(Button model) {
                assertThat(model, is(expectedButton));
            }
        });
    }

    private static void compare(PhotoAttachmentRequest request, PhotoAttachment attachment) {
        assertThat(attachment.getPayload().getPhotoId(), is(notNullValue()));
    }

    private static void compare(VideoAttachmentRequest request, VideoAttachment attachment) {
        assertThat(attachment.getPayload().getUrl(), is(notNullValue()));
        assertThat(attachment.getPayload().getId(), is(request.getPayload().getId()));
    }

    private static void compare(FileAttachmentRequest request, FileAttachment attachment) {
        assertThat(attachment.getPayload().getUrl(), is(notNullValue()));
        assertThat(attachment.getPayload().getFileId(), is(request.getPayload().getFileId()));
    }

    private static void compare(AudioAttachmentRequest request, AudioAttachment attachment) {
        assertThat(attachment.getPayload().getUrl(), is(notNullValue()));
        assertThat(attachment.getPayload().getId(), is(request.getPayload().getId()));
    }

    private static void compare(ContactAttachmentRequest request, ContactAttachment attachment) {
        assertThat(attachment.getPayload().getVcfInfo(), is(attachment.getPayload().getVcfInfo()));
    }

}
