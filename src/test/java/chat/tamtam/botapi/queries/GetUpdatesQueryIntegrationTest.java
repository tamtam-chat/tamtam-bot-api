package chat.tamtam.botapi.queries;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.FailByDefaultUpdateVisitor;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageBody;
import chat.tamtam.botapi.model.MessageCreatedUpdate;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.UpdateList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class GetUpdatesQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void name() throws Exception {
        Set<Long> bot1chats = getChats().stream().map(Chat::getChatId).collect(Collectors.toSet());
        Set<Long> bot2chats = new GetChatsQuery(client2).execute().getChats().stream().map(Chat::getChatId).collect(Collectors.toSet());
        bot1chats.retainAll(bot2chats);
        Long commonChatId = bot1chats.iterator().next();
        Set<String> sentMessages = ConcurrentHashMap.newKeySet();
        Set<String> receivedMessages = ConcurrentHashMap.newKeySet();
        CountDownLatch sendFinished = new CountDownLatch(1);

        Runnable getUpdates = () -> {
            try {
                UpdateList updateList = new GetUpdatesQuery(client)
                        .timeout(10)
                        .types(new HashSet<>(Arrays.asList(Update.MESSAGE_CREATED, Update.MESSAGE_REMOVED)))
                        .execute();

                for (Update update : updateList.getUpdates()) {
                    update.visit(new FailByDefaultUpdateVisitor() {
                        @Override
                        public void visit(MessageCreatedUpdate model) {
                            Message message = model.getMessage();
                            MessageBody body = message.getMessage();
                            receivedMessages.add(body.getMid());
                        }
                    });
                }
            } catch (APIException | ClientException e) {
                throw new RuntimeException(e);
            }
        };

        Thread producer = new Thread(() -> {
            try {
                int count = 20;
                while (count-- > 0) {
                    if (ThreadLocalRandom.current().nextBoolean() && !sentMessages.isEmpty()) {
                        NewMessageBody body = new NewMessageBody("edited message", null);
                        EditMessageQuery editMessageQuery = new EditMessageQuery(client2, body, sentMessages.iterator().next());
                        editMessageQuery.execute();
                        continue;
                    }

                    NewMessageBody newMessage = new NewMessageBody("text " + ID_COUNTER.incrementAndGet(), null);
                    SendMessageResult sendMessageResult = new SendMessageQuery(client2, newMessage)
                            .chatId(commonChatId)
                            .execute();

                    sentMessages.add(sendMessageResult.getMessageId());
                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                sendFinished.countDown();
            }
        });

        Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                getUpdates.run();
            }
        });


        producer.start();
        consumer.start();
        sendFinished.await();
        consumer.interrupt();
        getUpdates.run();

        assertThat(receivedMessages, is(sentMessages));
    }
}