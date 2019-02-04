package chat.tamtam.botapi.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chat.tamtam.botapi.client.TamTamSerializer;
import chat.tamtam.botapi.exceptions.SerializationException;
import chat.tamtam.botapi.model.ActionRequestBody;
import chat.tamtam.botapi.model.Attachment;
import chat.tamtam.botapi.model.AttachmentPayload;
import chat.tamtam.botapi.model.AudioAttachment;
import chat.tamtam.botapi.model.Button;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.model.ChatMember;
import chat.tamtam.botapi.model.ChatMembersList;
import chat.tamtam.botapi.model.ChatPatch;
import chat.tamtam.botapi.model.ChatStatus;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.ContactAttachment;
import chat.tamtam.botapi.model.ContactAttachmentPayload;
import chat.tamtam.botapi.model.FileAttachment;
import chat.tamtam.botapi.model.GetSubscriptionsResult;
import chat.tamtam.botapi.model.Image;
import chat.tamtam.botapi.model.InlineKeyboardAttachment;
import chat.tamtam.botapi.model.Intent;
import chat.tamtam.botapi.model.Keyboard;
import chat.tamtam.botapi.model.LinkButton;
import chat.tamtam.botapi.model.LinkedMessage;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageBody;
import chat.tamtam.botapi.model.MessageLinkType;
import chat.tamtam.botapi.model.PhotoAttachment;
import chat.tamtam.botapi.model.PhotoAttachmentPayload;
import chat.tamtam.botapi.model.Recipient;
import chat.tamtam.botapi.model.RequestContactButton;
import chat.tamtam.botapi.model.RequestGeoLocationButton;
import chat.tamtam.botapi.model.ShareAttachment;
import chat.tamtam.botapi.model.SimpleQueryResult;
import chat.tamtam.botapi.model.StickerAttachment;
import chat.tamtam.botapi.model.Subscription;
import chat.tamtam.botapi.model.SubscriptionRequestBody;
import chat.tamtam.botapi.model.UploadEndpoint;
import chat.tamtam.botapi.model.UploadType;
import chat.tamtam.botapi.model.User;
import chat.tamtam.botapi.model.UserWithPhoto;
import chat.tamtam.botapi.model.VideoAttachment;
import spark.Request;
import spark.Response;

/**
 * @author alexandrchuprin
 */
public class TamTamService {
    public static final String ACCESS_TOKEN = "dummyaccesstoken";
    private static final SimpleQueryResult SUCCESSFULL = new SimpleQueryResult(true);
    private static final SimpleQueryResult NOT_SUCCESSFULL = new SimpleQueryResult(false);
    private static final AtomicLong ID_COUNTER = new AtomicLong();
    public static final PhotoAttachment PHOTO_ATTACHMENT = new PhotoAttachment(
            new PhotoAttachmentPayload(ID_COUNTER.incrementAndGet(), "url"));
    public static final String CHAT_ICON_URL = "iconurl";
    public static final VideoAttachment VIDEO_ATTACHMENT = new VideoAttachment(new AttachmentPayload("urlvideo"));
    public static final AudioAttachment AUDIO_ATTACHMENT = new AudioAttachment(new AttachmentPayload("urlaudio"));
    public static final FileAttachment FILE_ATTACHMENT = new FileAttachment(new AttachmentPayload("urlfile"));
    public static final ContactAttachment CONTACT_ATTACHMENT = new ContactAttachment(
            new ContactAttachmentPayload("vcfinfo", null));
    public static final CallbackButton CALLBACK_BUTTON = new CallbackButton("payload", "text", Intent.DEFAULT);
    public static final RequestContactButton REQUEST_CONTACT_BUTTON = new RequestContactButton("request contact",
            Intent.NEGATIVE);
    public static final RequestGeoLocationButton REQUEST_GEO_LOCATION_BUTTON = new RequestGeoLocationButton(
            "request location", Intent.POSITIVE);
    public static final LinkButton LINK_BUTTON = new LinkButton("https://mail.ru", "link", Intent.POSITIVE);
    public static final InlineKeyboardAttachment INLINE_KEYBOARD_ATTACHMENT = new InlineKeyboardAttachment(
            "callbackId" + ID_COUNTER.incrementAndGet(), new Keyboard(
            Arrays.asList(
                    Collections.singletonList(CALLBACK_BUTTON),
                    Arrays.asList(REQUEST_CONTACT_BUTTON, REQUEST_GEO_LOCATION_BUTTON),
                    Arrays.asList(LINK_BUTTON)
            )));
    public static final StickerAttachment STICKER_ATTACHMENT = new StickerAttachment(new AttachmentPayload(
            "stickerurl"));
    public static final ShareAttachment SHARE_ATTACHMENT = new ShareAttachment(new AttachmentPayload("shareurl"));

    protected final UserWithPhoto me = new UserWithPhoto(
            "avata_rul",
            "full_avatar_url",
            ID_COUNTER.incrementAndGet(),
            "test bot",
            "testbot"
    );

    protected final Map<Long, User> users = new ConcurrentHashMap<>();
    protected final Map<Long, Chat> chats = new ConcurrentHashMap<>();
    protected final Map<Long, List<ChatMember>> chatMembers = new ConcurrentHashMap<>();
    protected final Map<Long, List<Message>> chatMessages = new ConcurrentHashMap<>();
    protected final TamTamSerializer serializer;
    protected final List<Subscription> subscriptions = Stream.generate(this::newSubscription)
            .limit(3)
            .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

    {
        Stream.generate(this::newUser).limit(10).forEach(u -> users.put(u.getUserId(), u));
        Stream.generate(this::newChat).limit(10).forEach(chat -> {
            chats.put(chat.getChatId(), chat);
            chatMembers.put(chat.getChatId(), Stream.generate(TamTamService::newChatMember)
                    .limit(chat.getParticipantsCount())
                    .collect(Collectors.toList()));
        });
    }

    public TamTamService(TamTamSerializer serializer) {
        this.serializer = serializer;
    }

    public Object getSubscriptions(Request request, Response response) {
        return new GetSubscriptionsResult(subscriptions);
    }

    public Object addMembers(Request request, Response response) {
        return SUCCESSFULL;
    }

    public Object answer(Request request, Response response) {
        return SUCCESSFULL;
    }

    public Object editChat(Request request, Response response) throws Exception {
        Long chatId = Long.valueOf(request.params("chatId"));
        ChatPatch patch = serializer.deserialize(request.body(), ChatPatch.class);
        Chat chat = chats.get(chatId);
        return new Chat(chatId, chat.getType(), chat.getStatus(),
                patch.getTitle() == null ? chat.getTitle() : patch.getTitle(), null, chat.getLastEventTime(),
                chat.getParticipantsCount());
    }

    public Object editMessage(Request request, Response response) {
        return SUCCESSFULL;
    }

    public Object getChat(Request request, Response response) {
        Long chatId = Long.valueOf(request.params("chatId"));
        return chats.get(chatId);
    }

    public Object getChats(Request request, Response response) {
        ArrayList<Chat> chatsList = new ArrayList<>(this.chats.values());
        int count = Math.min(Integer.parseInt(request.queryParams("count")), chatsList.size());
        List<Chat> chats = chatsList.subList(0, count);
        return new ChatList(chats, null);
    }

    public Object getMembers(Request request, Response response) {
        Long chatId = Long.valueOf(request.params("chatId"));
        String marker = request.queryParams("marker");
        Integer count = getInt(request, "count").orElse(5);
        List<ChatMember> chatMembers = this.chatMembers.get(chatId);
        Long from = marker == null ? 0L : Long.valueOf(marker);
        Long to = Math.min(chatMembers.size(), from + count);
        List<ChatMember> result = chatMembers.subList(from.intValue(), to.intValue());
        return new ChatMembersList(result, to == chatMembers.size() ? null : to);
    }

    public Object getUploadUrl(Request request, Response response) {
        String type = request.queryParams("type");
        UploadType uploadType = UploadType.create(type);
        return new UploadEndpoint("http://url" + uploadType.name() + ".com");
    }

    public Object leaveChat(Request request, Response response) {
        Long chatId = Long.valueOf(request.params("chatId"));
        return SUCCESSFULL;
    }

    public Object removeMembers(Request request, Response response) throws Exception {
        Long chatId = Long.valueOf(request.params("chatId"));
        Long userId = Long.valueOf(request.queryParams("user_id"));
        return SUCCESSFULL;
    }

    public Object sendAction(Request request, Response response) throws Exception {
        Long chatId = Long.valueOf(request.params("chatId"));
        ActionRequestBody actionRequestBody = serializer.deserialize(request.body(), ActionRequestBody.class);
        return SUCCESSFULL;
    }

    public Object addSubscription(Request request, Response response) throws Exception {
        SubscriptionRequestBody subscription = serializer.deserialize(request.body(), SubscriptionRequestBody.class);
        return SUCCESSFULL;
    }

    public Object removeSubscription(Request request, Response response) {
        String url = request.queryParams("url");
        if (url == null) {
            return NOT_SUCCESSFULL;
        }

        return SUCCESSFULL;
    }

    public String serialize(Object o) throws SerializationException {
        return new String(serializer.serialize(o));
    }

    protected Message message(Long chatId) {
        User sender = random(new ArrayList<>(users.values()));
        Recipient recipient = new Recipient(chatId, ChatType.CHAT, null);
        long id = ID_COUNTER.incrementAndGet();
        boolean hasText = ThreadLocalRandom.current().nextBoolean();
        List<Attachment> attachments = Arrays.asList(
                PHOTO_ATTACHMENT,
                VIDEO_ATTACHMENT,
                AUDIO_ATTACHMENT,
                FILE_ATTACHMENT,
                STICKER_ATTACHMENT,
                SHARE_ATTACHMENT,
                INLINE_KEYBOARD_ATTACHMENT,
                CONTACT_ATTACHMENT
        );

        MessageBody body = new MessageBody("mid." + id, id, hasText ? "text" + id : null, attachments);
        Message message = new Message(sender, recipient, System.currentTimeMillis(), body);
        message.link(new LinkedMessage(MessageLinkType.FORWARD, sender, id, body));
        body.replyTo("replyTo");

        return message;
    }

    private static ChatMember newChatMember() {
        return new ChatMember(ThreadLocalRandom.current().nextLong(), ThreadLocalRandom.current().nextBoolean(),
                ID_COUNTER.incrementAndGet(), "name", null, null, null);
    }

    private static OptionalInt getInt(Request request, String paramName) {
        String param = request.queryParams(paramName);
        return param == null ? OptionalInt.empty() : OptionalInt.of(Integer.parseInt(param));
    }

    protected static <T> T random(Collection<T> collection) {
        List<T> list = new ArrayList<>(collection);
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    private Subscription newSubscription() {
        return new Subscription("http://url" + ID_COUNTER.incrementAndGet() + ".com", System.currentTimeMillis());
    }

    private User newUser() {
        long userId = ID_COUNTER.incrementAndGet();
        return new User(userId, "user-" + userId, "username" + userId);
    }

    private Chat newChat() {
        boolean isDialog = ThreadLocalRandom.current().nextBoolean();
        ChatType type = isDialog ? ChatType.DIALOG : ChatType.CHAT;
        Image icon = new Image(CHAT_ICON_URL);
        Chat chat = new Chat(ID_COUNTER.incrementAndGet(), type, ChatStatus.ACTIVE, "chat title", icon, 0L,
                isDialog ? 2 : ThreadLocalRandom.current().nextInt(100));

        Map<String, Long> participants = new HashMap<>();
        return chat.ownerId(ID_COUNTER.incrementAndGet())
                .putParticipantsItem(String.valueOf(ID_COUNTER.incrementAndGet()), System.currentTimeMillis())
                .participants(participants);
    }
}
