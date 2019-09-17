package chat.tamtam.botapi.queries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageList;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.SendMessageResult;

import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class GetMessagesQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldGetMessagesByIds() throws Exception {
        List<Chat> chats = getChats();
        Chat dialog = getByType(chats, ChatType.DIALOG);
        Chat chat = getByTitle(chats, "test chat #1");

        List<String> texts = Stream.generate(this::randomText).limit(10).collect(Collectors.toList());
        Set<String> ids = texts.stream()
                .map(t -> new NewMessageBody(t, null, null))
                .map(nmb -> sendMessage(nmb, ThreadLocalRandom.current().nextBoolean() ? chat.getChatId() : dialog.getChatId()))
                .map(smr -> smr.getMessage().getBody().getMid())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        MessageList messageList = new GetMessagesQuery(client).messageIds(ids).execute();
        assertThat(messageList.getMessages().stream().map(m -> m.getBody().getText()).collect(Collectors.toList()),
                is(texts));
    }

    private SendMessageResult sendMessage(NewMessageBody nmb, Long chatId) {
        try {
            return botAPI.sendMessage(nmb).chatId(chatId).execute();
        } catch (APIException | ClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldGetMessagesByChatId() throws Exception {
        int count = 5;
        List<Chat> chats = getChats();
        Chat chat = getByTitle(chats, "test chat #5");
        Set<NewMessageBody> newMessages = Stream.generate(() -> new NewMessageBody(randomText(), null, null))
                .limit(count)
                .collect(Collectors.toSet());

        for (NewMessageBody newMessage : newMessages) {
            botAPI.sendMessage(newMessage).chatId(chat.getChatId()).execute();
        }

        List<Message> messages = new GetMessagesQuery(client)
                .chatId(chat.getChatId())
                .count(count).execute().getMessages();

        assertThat(messages.stream().map(m -> m.getBody().getText()).collect(Collectors.toSet()),
                is(newMessages.stream().map(NewMessageBody::getText).collect(Collectors.toSet())));
    }

    @Test
    public void shouldReturnMessageStat() throws Exception {
        List<Chat> chats = getChats();
        Chat chat = getByTitle(chats, "test channel #4");
        new SendMessageQuery(client2, new NewMessageBody(randomText(), null, null)).chatId(chat.getChatId()).execute();

        new GetMessagesQuery(client).chatId(chat.getChatId()).execute();
        MessageList messageList = new GetMessagesQuery(client2).chatId(chat.getChatId()).execute();
        assertThat(messageList.getMessages().get(0).getStat().getViews(), is(greaterThan(0)));
    }

    @Test
    public void shouldReturnNoStatInDialogs() throws Exception {
        List<Chat> chats = getChats();
        Chat chat = getByType(chats, ChatType.DIALOG);
        new SendMessageQuery(client, new NewMessageBody(randomText(), null, null)).chatId(chat.getChatId()).execute();
        MessageList messageList = new GetMessagesQuery(client).chatId(chat.getChatId()).execute();
        assertThat(messageList.getMessages().get(0).getStat(), is(nullValue()));
    }

    @Test
    public void shouldGetAllMessagesInDialog() throws Exception {
        List<Chat> chats = getChats();
        Chat dialog = getBy(chats, c -> c.getType() == ChatType.DIALOG && c.getChatId() != (bot1.getUserId() ^ bot3.getUserId()));

        List<String> posted = new ArrayList<>();
        long start = now();
        Long dialogChatId = dialog.getChatId();
        for (int i = 0; i < 30; i++) {
            String text = randomText();
            new SendMessageQuery(client, new NewMessageBody(text, null, null)).chatId(dialogChatId).execute();
            posted.add(0, text);
            sleep(1);
        }

        sleep(1000);
        long from = now();
        List<String> fetched = new ArrayList<>();
        do {
            MessageList messageList = new GetMessagesQuery(client).chatId(dialogChatId).count(10).from(from).to(start).execute();
            List<Message> messages = messageList.getMessages();
            if (messages.isEmpty()) {
                break;
            }

            from = messages.get(messages.size() - 1).getTimestamp() - 1;
            for (Message message : messages) {
                fetched.add(message.getBody().getText());
            }
        } while (from > start);

        assertThat(fetched, is(posted));
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionOnInvalidMessageId() throws Exception {
        MessageList messageList = new GetMessagesQuery(client).messageIds(
                Collections.singleton("mid.nonexistingmessageid123456789012")).execute();

        assertThat(messageList.getMessages(), is(empty()));
    }
}