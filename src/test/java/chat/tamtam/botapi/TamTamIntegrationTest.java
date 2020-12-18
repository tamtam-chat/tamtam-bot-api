package chat.tamtam.botapi;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.client.impl.OkHttpTransportClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.AttachmentNotReadyException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.Attachment;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.AudioAttachment;
import chat.tamtam.botapi.model.AudioAttachmentRequest;
import chat.tamtam.botapi.model.BotInfo;
import chat.tamtam.botapi.model.Button;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatButton;
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
import chat.tamtam.botapi.model.LinkedMessage;
import chat.tamtam.botapi.model.LocationAttachment;
import chat.tamtam.botapi.model.LocationAttachmentRequest;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageLinkType;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.NewMessageLink;
import chat.tamtam.botapi.model.PhotoAttachment;
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;
import chat.tamtam.botapi.model.PhotoTokens;
import chat.tamtam.botapi.model.RequestContactButton;
import chat.tamtam.botapi.model.RequestGeoLocationButton;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.ShareAttachment;
import chat.tamtam.botapi.model.ShareAttachmentRequest;
import chat.tamtam.botapi.model.StickerAttachment;
import chat.tamtam.botapi.model.StickerAttachmentRequest;
import chat.tamtam.botapi.model.UploadType;
import chat.tamtam.botapi.model.UserIdsList;
import chat.tamtam.botapi.model.VideoAttachment;
import chat.tamtam.botapi.model.VideoAttachmentRequest;
import chat.tamtam.botapi.queries.AddMembersQuery;
import chat.tamtam.botapi.queries.GetChatQuery;
import chat.tamtam.botapi.queries.GetChatsQuery;
import chat.tamtam.botapi.queries.GetMessagesQuery;
import chat.tamtam.botapi.queries.RemoveMemberQuery;
import chat.tamtam.botapi.queries.SendMessageQuery;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
@Category(IntegrationTest.class)
public abstract class TamTamIntegrationTest {
    protected static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    protected static final AtomicLong ID_COUNTER = new AtomicLong();
    private static final AtomicBoolean ONCE = new AtomicBoolean();
    private static final boolean IS_TRAVIS = Boolean.parseBoolean(System.getenv("CI"));
    private static final String TOKEN_1 = getToken("TAMTAM_BOTAPI_TOKEN");
    private static final String TOKEN_2 = getToken("TAMTAM_BOTAPI_TOKEN_2");
    private static final String TOKEN_3 = getToken("TAMTAM_BOTAPI_TOKEN_3");
    private static final ConcurrentHashMap<String, List<Chat>> CHATS_BY_CLIENT = new ConcurrentHashMap<>();

    private final OkHttpTransportClient transportClient = new OkHttpTransportClient();
    protected final JacksonSerializer serializer = new JacksonSerializer();

    protected TamTamClient client = new TamTamClient(TOKEN_1, transportClient, serializer);
    protected TamTamClient client2 = new TamTamClient(TOKEN_2, transportClient, serializer);
    protected TamTamClient client3 = new TamTamClient(TOKEN_3, transportClient, serializer);
    protected TamTamBotAPI botAPI = new TamTamBotAPI(client);
    protected TamTamUploadAPI uploadAPI = new TamTamUploadAPI(client);

    protected TestBot bot1;
    protected TestBot bot2;
    protected TestBot3 bot3;

    @BeforeClass
    public static void beforeClass() {
        info("Endpoint: {}", System.getenv("TAMTAM_BOTAPI_ENDPOINT"));
    }

    @Before
    public void setUp() throws Exception {
        bot1 = new TestBot(client, IS_TRAVIS);
        bot2 = new TestBot(client2, IS_TRAVIS);
        bot3 = new TestBot3(client3, client, IS_TRAVIS);
        if (!ONCE.getAndSet(true)) {
            info("Bot 1: {}", bot1);
            info("Bot 2: {}", bot2);
            info("Bot 3: {}", bot3);
        }
    }

    protected BotInfo getBot1() throws APIException, ClientException {
        return botAPI.getMyInfo().execute();
    }

    protected List<Chat> getChats() throws APIException, ClientException {
        return getChats(client);
    }

    protected List<Chat> getChats(TamTamClient client) {
        return CHATS_BY_CLIENT.computeIfAbsent(client.getAccessToken(), (k) -> {
            Long marker;
            List<Chat> chats = new ArrayList<>();
            do {
                ChatList chatList;
                try {
                    chatList = new GetChatsQuery(client).count(100).execute();
                } catch (APIException | ClientException e) {
                    throw new RuntimeException(e);
                }

                chats.addAll(chatList.getChats());
                marker = chatList.getMarker();
            } while (marker != null);

            return chats;
        });
    }

    protected Chat getChat(long chatId) throws APIException, ClientException {
        return getChat(client, chatId);
    }

    protected Chat getChat(TamTamClient client, long chatId) throws APIException, ClientException {
        return new GetChatQuery(client, chatId).execute();
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

    protected List<Message> send(NewMessageBody newMessage, List<Chat> toChats) throws Exception {
        List<Message> sent = new ArrayList<>();
        for (Chat c : toChats) {
            SendMessageResult sendMessageResult = doSend(newMessage, c.getChatId());
            sent.add(sendMessageResult.getMessage());
        }

        return sent;
    }

    protected SendMessageResult doSend(NewMessageBody newMessage, Long chatId) throws Exception {
        return doSend(client, newMessage, chatId);
    }

    protected SendMessageResult doSend(TamTamClient client, NewMessageBody newMessage, Long chatId) throws Exception {
        do {
            try {
                SendMessageResult sendMessageResult = new SendMessageQuery(client, newMessage).chatId(chatId).execute();
                assertThat(sendMessageResult, is(notNullValue()));
                String messageId = sendMessageResult.getMessage().getBody().getMid();
                Message lastMessage = getMessage(client, messageId);
                compare(client, messageId, newMessage, lastMessage);
                return sendMessageResult;
            } catch (AttachmentNotReadyException e) {
                // it is ok, try again
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        } while (true);
    }

    protected void compare(TamTamClient client, String messageId, NewMessageBody newMessage,
                           Message lastMessage) throws Exception {
        String text = newMessage.getText();
        assertThat(lastMessage.getBody().getMid(), is(messageId));
        if (text != null) {
            assertThat(lastMessage.getBody().getText(), is(text));
        }

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
            compare(client, linkedMessage, link);
        }

        Chat chat = getChat(client, lastMessage.getRecipient().getChatId());
        if (chat.getType() == ChatType.CHANNEL) {
            assertThat(lastMessage.getRecipient().getChatType(), is(ChatType.CHANNEL));
        }

        if (chat.isPublic()) {
            assertThat(lastMessage.getUrl().length(), is(greaterThan(0)));
        } else {
            assertThat(lastMessage.getUrl(), is(nullValue()));
        }
    }

    protected Chat getByType(List<Chat> chats, ChatType type) throws Exception {
        return chats.stream()
                .filter(c -> c.getType() == type)
                .filter(c -> c.getStatus() == ChatStatus.ACTIVE)
                .findFirst()
                .orElseThrow(notFound(type.getValue()));
    }

    protected Chat getBy(List<Chat> chats, Predicate<Chat> filter) throws Exception {
        return chats.stream()
                .filter(filter)
                .filter(c -> c.getStatus() == ChatStatus.ACTIVE)
                .findFirst()
                .orElseThrow(notFound(filter.toString()));
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
                compare(model, (LocationAttachment) attachment);
            }

            @Override
            public void visit(ShareAttachmentRequest model) {
                compare(model, ((ShareAttachment) attachment));
            }

            @Override
            public void visit(FileAttachmentRequest model) {
                compare(model, ((FileAttachment) attachment));
            }

            @Override
            public void visit(StickerAttachmentRequest model) {
                compare(model, (StickerAttachment) attachment);
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

    protected List<Chat> getChatsForSend() throws Exception {
        List<Chat> chats = getChats();
        Chat dialog = getByType(chats, ChatType.DIALOG);
        Chat chat = getByTitle(chats, "test chat #4");
        Chat channel = getByTitle(chats, "test channel #1");
        return Arrays.asList(dialog, chat, channel);
    }

    protected void removeUser(TamTamClient client, Long commonChatId, Long userId) {
        try {
            new RemoveMemberQuery(client, commonChatId, userId).execute();
        } catch (APIException | ClientException e) {
            fail(e.getMessage());
        }
    }

    protected void addUser(TamTamClient client, Long chatId, Long userId) throws Exception {
        new AddMembersQuery(client, new UserIdsList(Collections.singletonList(userId)), chatId).execute();
    }

    protected Message getMessage(TamTamClient client, String messageId) throws APIException, ClientException {
        return new GetMessagesQuery(client)
                .messageIds(Collections.singleton(messageId))
                .execute()
                .getMessages()
                .get(0);
    }

    protected void await(CountDownLatch latch) throws InterruptedException {
        await(latch, 10);
    }

    protected void await(CountDownLatch latch, int seconds) throws InterruptedException {
        if (!latch.await(seconds, TimeUnit.SECONDS)) {
            fail();
        }
    }

    protected static void info(String text, Object... objects) {
        if (!IS_TRAVIS) {
            LOG.info(text, objects);
        }
    }

    private static void compare(StickerAttachmentRequest model, StickerAttachment attachment) {
        assertThat(model.getPayload().getCode(), is(attachment.getPayload().getCode()));
        assertThat(attachment.getPayload().getUrl(), is(notNullValue()));
        assertThat(attachment.getWidth(), is(greaterThan(0)));
        assertThat(attachment.getHeight(), is(greaterThan(0)));
    }

    private static void compare(TamTamClient client, LinkedMessage linkedMessage,
                                NewMessageLink link) throws APIException, ClientException {
        assertThat(linkedMessage.getType(), is(link.getType()));

        if (link.getType() == MessageLinkType.REPLY) {
            Message message = new GetMessagesQuery(client).messageIds(
                    Collections.singleton(link.getMid())).execute().getMessages().get(0);
            assertThat(linkedMessage.getMessage().getSeq(), is(message.getBody().getSeq()));
            assertThat(linkedMessage.getMessage().getText(), is(message.getBody().getText()));
            assertThat(linkedMessage.getMessage().getAttachments(), is(message.getBody().getAttachments()));
            return;
        }

        assertThat(linkedMessage.getMessage().getMid(), is(link.getMid()));
    }

    protected static long now() {
        return System.currentTimeMillis();
    }

    @NotNull
    protected PhotoAttachmentRequest getPhotoAttachmentRequest() throws Exception {
        String uploadUrl = getUploadUrl(UploadType.IMAGE);
        File file = new File(getClass().getClassLoader().getResource("test.png").toURI());
        PhotoTokens photoTokens = uploadAPI.uploadImage(uploadUrl, file).execute();
        PhotoAttachmentRequestPayload payload = new PhotoAttachmentRequestPayload().photos(photoTokens.getPhotos());
        return new PhotoAttachmentRequest(payload);
    }

    protected String getUploadUrl(UploadType uploadType) throws Exception {
        String url = botAPI.getUploadUrl(uploadType).execute().getUrl();
        if (url.startsWith("http")) {
            return url;
        }

        return "http:" + url;
    }

    protected String randomText() {
        return getClass().getSimpleName() + "\n" + UUID.randomUUID().toString();
    }

    protected static String randomText(int length) {
        String alphabet = "qwertyuiopasdfghjklzxcvbnm";
        return Stream.generate(() -> alphabet.charAt(ThreadLocalRandom.current().nextInt(alphabet.length())))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
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
            public void visit(ChatButton model) {
                ChatButton cb = (ChatButton) expectedButton;
                assertThat(model.getChatDescription(), is(cb.getChatDescription()));
                assertThat(model.getChatTitle(), is(cb.getChatTitle()));
                assertThat(model.getStartPayload(), is(cb.getStartPayload()));
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
        compareTokens(attachment.getPayload().getToken(), request.getPayload().getToken());
    }

    private static void compare(FileAttachmentRequest request, FileAttachment attachment) {
        assertThat(attachment.getPayload().getUrl(), is(notNullValue()));
        compareTokens(attachment.getPayload().getToken(), request.getPayload().getToken());
    }

    private static void compare(AudioAttachmentRequest request, AudioAttachment attachment) {
        assertThat(attachment.getPayload().getUrl(), is(notNullValue()));
        compareTokens(attachment.getPayload().getToken(), request.getPayload().getToken());
    }

    private static void compare(ContactAttachmentRequest request, ContactAttachment attachment) {
        if (request.getPayload().getVcfInfo() != null) {
            assertThat(attachment.getPayload().getVcfInfo(), is(request.getPayload().getVcfInfo()));
        }
    }

    private static void compare(ShareAttachmentRequest request, ShareAttachment attachment) {
        assertThat(attachment.getPayload().getUrl(), not(isEmptyString()));
        assertThat(attachment.getPayload().getToken(), not(isEmptyString()));
        assertThat(attachment.getTitle(), not(isEmptyString()));
        if (request.getPayload().getUrl() != null) {
            assertThat(request.getPayload().getUrl(), is(attachment.getPayload().getUrl()));
        }
    }

    private static void compare(LocationAttachmentRequest request, LocationAttachment attachment) {
        assertThat(request.getLatitude(), is(attachment.getLatitude()));
        assertThat(request.getLongitude(), is(attachment.getLongitude()));
    }

    private static void compareTokens(String token1, String token2) {
        assertThat(token1.substring(0, 21), is(token2.substring(0, 21)));
    }
}
