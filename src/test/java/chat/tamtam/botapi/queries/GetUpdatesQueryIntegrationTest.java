package chat.tamtam.botapi.queries;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.FailByDefaultUpdateVisitor;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageBody;
import chat.tamtam.botapi.model.MessageCreatedUpdate;
import chat.tamtam.botapi.model.MessageEditedUpdate;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.SendMessageResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author alexandrchuprin
 */
public class GetUpdatesQueryIntegrationTest extends TamTamIntegrationTest {

    @Test
    public void shouldGetUpdates() throws Exception {
        Chat commonChat = getByTitle(getChats(), "test chat #6");
        Long commonChatId = commonChat.getChatId();
        List<Message> sentMessages = new CopyOnWriteArrayList<>();
        List<Message> receivedMessages = new CopyOnWriteArrayList<>();
        List<Message> editedMessages = new CopyOnWriteArrayList<>();
        CountDownLatch sendFinished = new CountDownLatch(1);

        AutoCloseable bot1consumer = bot1.addConsumer(commonChatId, new FailByDefaultUpdateVisitor(bot1) {
            @Override
            public void visit(MessageCreatedUpdate model) {
                Message message1 = model.getMessage();
                MessageBody body1 = message1.getBody();
                info("Got update: " + body1.getMid() + ", text: " + body1.getText());
                receivedMessages.add(message1);
            }

            @Override
            public void visit(MessageEditedUpdate model) {
                editedMessages.add(model.getMessage());
            }
        });

        Thread producer = new Thread(() -> {
            try {
                int count = 20;
                while (count-- > 0) {
                    if (ThreadLocalRandom.current().nextBoolean() && !sentMessages.isEmpty()) {
                        NewMessageBody body = new NewMessageBody("edited message", null, null);
                        Message message = sentMessages.get(ThreadLocalRandom.current().nextInt(sentMessages.size()));
                        String messageId = message.getBody().getMid();
                        EditMessageQuery editMessageQuery = new EditMessageQuery(client2, body, messageId);
                        editMessageQuery.execute();
                        info("Message {} edited", messageId);
                        continue;
                    }

                    String text = "text " + ID_COUNTER.incrementAndGet();
                    NewMessageBody newMessage = new NewMessageBody(text, null, null);
                    SendMessageResult sendMessageResult = new SendMessageQuery(client2, newMessage)
                            .chatId(commonChatId)
                            .execute();

                    String messageId = sendMessageResult.getMessage().getBody().getMid();
                    sentMessages.add(sendMessageResult.getMessage());
                    info("Message {} sent: {}", messageId, text);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                sendFinished.countDown();
            }
        });

        producer.start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));

        sendFinished.await();
        bot1consumer.close();

        assertThat(receivedMessages, is(sentMessages));
    }
}