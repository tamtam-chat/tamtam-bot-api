package chat.tamtam.botapi.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import chat.tamtam.botapi.model.ActionRequestBody;
import chat.tamtam.botapi.model.Attachment;
import chat.tamtam.botapi.model.AttachmentPayload;
import chat.tamtam.botapi.model.AudioAttachment;
import chat.tamtam.botapi.model.Button;
import chat.tamtam.botapi.model.Callback;
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
import chat.tamtam.botapi.model.InlineKeyboardAttachment;
import chat.tamtam.botapi.model.Intent;
import chat.tamtam.botapi.model.Keyboard;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageBody;
import chat.tamtam.botapi.model.MessageCallbackUpdate;
import chat.tamtam.botapi.model.MessageCreatedUpdate;
import chat.tamtam.botapi.model.MessageEditedUpdate;
import chat.tamtam.botapi.model.MessageList;
import chat.tamtam.botapi.model.MessageRemovedUpdate;
import chat.tamtam.botapi.model.MessageRestoredUpdate;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PhotoAttachment;
import chat.tamtam.botapi.model.PhotoAttachmentPayload;
import chat.tamtam.botapi.model.Recipient;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.ShareAttachment;
import chat.tamtam.botapi.model.SimpleQueryResult;
import chat.tamtam.botapi.model.Subscription;
import chat.tamtam.botapi.model.SubscriptionRequestBody;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.UpdateList;
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
    private final UserWithPhoto me = new UserWithPhoto(
            "avata_rul",
            "full_avatar_url",
            ID_COUNTER.incrementAndGet(),
            "test bot",
            "testbot"
    );

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Chat> chats = new ConcurrentHashMap<>();
    private final Map<Long, List<ChatMember>> chatMembers = new ConcurrentHashMap<>();
    private final Map<Long, List<Message>> chatMessages = new ConcurrentHashMap<>();
    private final ObjectMapper mapper;
    private final List<Subscription> subscriptions = Stream.generate(this::newSubscription)
            .limit(3)
            .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

    {
        Stream.generate(this::newUser).limit(10).forEach(u -> users.put(u.getUserId(), u));
        Stream.generate(this::newChat).limit(10).forEach(chat -> {
            chats.put(chat.getChatId(), chat);
            chatMembers.put(chat.getChatId(), Stream.generate(TamTamService::newChatMember)
                    .limit(chat.getParticipantsCount())
                    .collect(Collectors.toList()));
            chatMessages.put(chat.getChatId(), Stream.generate(() -> newMessage(chat))
                    .limit(ThreadLocalRandom.current().nextInt(1, 30))
                    .collect(Collectors.toList()));
        });
    }

    public TamTamService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Object getSubscriptions(Request request, Response response) {
        return new GetSubscriptionsResult(subscriptions);
    }

    public Object getMyInfo(Request request, Response response) {
        return me;
    }

    public Object addMembers(Request request, Response response) {
        return SUCCESSFULL;
    }

    public Object answer(Request request, Response response) {
        return SUCCESSFULL;
    }

    public Object editChat(Request request, Response response) throws Exception {
        Long chatId = Long.valueOf(request.params("chatId"));
        ChatPatch patch = mapper.readValue(request.bodyAsBytes(), ChatPatch.class);
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

    public Object getMessages(Request request, Response response) {
        long chatId = Long.parseLong(request.queryParams("chat_id"));
        return new MessageList(this.chatMessages.get(chatId));
    }

    public Object getUploadUrl(Request request, Response response) {
        UploadType uploadType = UploadType.create(request.queryParams("type"));
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
        ActionRequestBody actionRequestBody = mapper.readValue(request.body(), ActionRequestBody.class);
        return SUCCESSFULL;
    }

    public Object addSubscription(Request request, Response response) throws IOException {
        SubscriptionRequestBody subscription = mapper.readValue(request.body(), SubscriptionRequestBody.class);
        return SUCCESSFULL;
    }

    public Object removeSubscription(Request request, Response response) {
        String url = request.queryParams("url");
        if (url == null) {
            return NOT_SUCCESSFULL;
        }

        return SUCCESSFULL;
    }

    public Object sendMessage(Request request, Response response) throws IOException {
        long chatId = Long.parseLong(request.queryParams("chat_id"));
        NewMessageBody newMessage = mapper.readValue(request.body(), NewMessageBody.class);
        return new SendMessageResult(chatId, chatId, "mid." + chatId);
    }

    public Object getUpdates(Request request, Response response) throws Exception {
        Chat randomChat = random(chats.values());
        long now = System.currentTimeMillis();
        List<Update> updates = Arrays.asList(
                new MessageCreatedUpdate(newMessage(randomChat), now),
                new MessageEditedUpdate(newMessage(randomChat), now),
                new MessageRemovedUpdate("mid." + ID_COUNTER.incrementAndGet(), now),
                new MessageRestoredUpdate("mid." + ID_COUNTER.incrementAndGet(), now),
                new MessageCallbackUpdate(new Callback(now, "calbackId", "payload", random(users.values())), now)
        );

        return new UpdateList(updates, null);
    }

    private static ChatMember newChatMember() {
        return new ChatMember(ThreadLocalRandom.current().nextLong(), ThreadLocalRandom.current().nextBoolean(),
                ID_COUNTER.incrementAndGet(), "name", null, null, null);
    }

    private static OptionalInt getInt(Request request, String paramName) {
        String param = request.queryParams(paramName);
        return param == null ? OptionalInt.empty() : OptionalInt.of(Integer.parseInt(param));
    }

    private static <T> T random(Collection<T> collection) {
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

    private Message newMessage(Chat chat) {
        User sender = random(new ArrayList<>(users.values()));
        Recipient recipient = new Recipient(chat.getChatId(), chat.getType(), null);
        long id = ID_COUNTER.incrementAndGet();
        boolean hasText = ThreadLocalRandom.current().nextBoolean();
        boolean hasAttachments = !hasText || ThreadLocalRandom.current().nextBoolean();
        List<Attachment> attachments = hasAttachments
                ? Stream.generate(this::newAttachment).limit(3).collect(Collectors.toList())
                : null;

        MessageBody body = new MessageBody("mid." + id, id, hasText ? "text" + id : null, attachments);
        return new Message(sender, recipient, System.currentTimeMillis(), body);
    }

    private Attachment newAttachment() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (random.nextBoolean()) {
            return new PhotoAttachment(new PhotoAttachmentPayload(ID_COUNTER.incrementAndGet(), "url"));
        } else if (random.nextBoolean()) {
            return new VideoAttachment(new AttachmentPayload("urlvideo"));
        } else if (random.nextBoolean()) {
            return new AudioAttachment(new AttachmentPayload("urlaudio"));
        } else if (random.nextBoolean()) {
            return new FileAttachment(new AttachmentPayload("urlfile"));
        } else if (random.nextBoolean()) {
            return new ContactAttachment(new ContactAttachmentPayload("vcfinfo", null));
        } else if (random.nextBoolean()) {
            List<List<Button>> buttons = Stream.generate(
                    () -> Stream.generate(this::newButton)
                            .limit(random.nextInt(1, 3))
                            .collect(Collectors.toList()))
                    .limit(random.nextInt(1, 3))
                    .collect(Collectors.toList());
            Keyboard keyboard = new Keyboard(buttons);
            return new InlineKeyboardAttachment("callbackId" + ID_COUNTER.incrementAndGet(), keyboard);
        }

        return new ShareAttachment(new AttachmentPayload("shareurl"));
    }

    private Button newButton() {
        return new CallbackButton("payload", "text", Intent.DEFAULT);
    }

    private Chat newChat() {
        boolean isDialog = ThreadLocalRandom.current().nextBoolean();
        ChatType type = isDialog ? ChatType.DIALOG : ChatType.CHAT;
        return new Chat(ID_COUNTER.incrementAndGet(), type, ChatStatus.ACTIVE, "chat title", null, 0L,
                isDialog ? 2 : ThreadLocalRandom.current().nextInt(100));
    }
}
