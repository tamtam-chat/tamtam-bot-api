package chat.tamtam.botapi.queries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageList;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.SendMessageResult;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class GetMessagesQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldGetMessagesByIds() throws Exception {
        String text = "GetMessagesQueryIntegrationTest " + randomText();
        String text2 = "GetMessagesQueryIntegrationTest " + randomText();
        List<Chat> chats = getChats();
        Chat dialog = getByType(chats, ChatType.DIALOG);
        Chat chat = getByTitle(chats, "test chat #1");
        SendMessageResult sendMessageResult = botAPI.sendMessage(new NewMessageBody(text, null, null)).chatId(
                dialog.getChatId()).execute();
        SendMessageResult sendMessageResult2 = botAPI.sendMessage(new NewMessageBody(text2, null, null)).chatId(
                chat.getChatId()).execute();
        Set<String> ids = new HashSet<>();
        ids.add(sendMessageResult.getMessage().getBody().getMid());
        ids.add(sendMessageResult2.getMessage().getBody().getMid());

        MessageList messageList = new GetMessagesQuery(client).messageIds(ids).execute();
        assertThat(messageList.getMessages().stream().map(m -> m.getBody().getText()).collect(Collectors.toSet()),
                hasItems(text, text2));
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
    public void shouldGetAllMessagesInDialog() throws Exception {
        List<Chat> chats = getChats();
        Chat dialog = getByType(chats, ChatType.DIALOG);

        List<String> posted = new ArrayList<>();
        long start = now();
        for (int i = 0; i < 30; i++) {
            String text = randomText();
            new SendMessageQuery(client, new NewMessageBody(text, null, null)).chatId(dialog.getChatId()).execute();
            posted.add(text);
        }

        long from = now();
        List<String> fetched = new ArrayList<>();
        do {
            MessageList messageList = new GetMessagesQuery(client).chatId(dialog.getChatId()).count(10).from(from).to(start).execute();
            List<Message> messages = messageList.getMessages();
            if (messages.isEmpty()) {
                break;
            }

            from = messages.get(messages.size() - 1).getTimestamp() - 1;
            for (Message message : messages) {
                fetched.add(message.getBody().getText());
            }
        } while (from > start);

        Collections.reverse(posted);
        assertThat(fetched, is(posted));
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionOnInvalidMessageId() throws Exception {
        MessageList messageList = new GetMessagesQuery(client).messageIds(
                Collections.singleton("mid.nonexistingmessageid123456789012")).execute();

        assertThat(messageList.getMessages(), is(empty()));
    }
}