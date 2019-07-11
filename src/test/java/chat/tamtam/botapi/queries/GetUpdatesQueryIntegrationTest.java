package chat.tamtam.botapi.queries;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.xml.stream.util.StreamReaderDelegate;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatStatus;
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
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void shouldGetUpdates() throws Exception {
        Chat commonChat = getByTitle(getChats(), "test chat #6");
        Long commonChatId = commonChat.getChatId();
        List<String> sentMessages = new CopyOnWriteArrayList<>();
        List<String> receivedMessages = new CopyOnWriteArrayList<>();
        CountDownLatch sendFinished = new CountDownLatch(1);

        // consume all pending updates to make sure queue is empty before test
        flush();

        Function<Long, Long> getUpdates = (marker) -> {
            LOG.info("Marker: " + marker);
            try {
                UpdateList updateList = new GetUpdatesQuery(client)
                        .timeout(10)
                        .types(new HashSet<>(Arrays.asList(Update.MESSAGE_CREATED, Update.MESSAGE_REMOVED)))
                        .marker(marker)
                        .execute();

                for (Update update : updateList.getUpdates()) {
                    update.visit(new FailByDefaultUpdateVisitor() {
                        @Override
                        public void visit(MessageCreatedUpdate model) {
                            Message message = model.getMessage();
                            MessageBody body = message.getBody();
                            LOG.info("Got update: " + body.getMid() + ", text: " + body.getText());
                            receivedMessages.add(body.getMid());
                        }
                    });
                }

                return updateList.getMarker();
            } catch (APIException | ClientException e) {
                throw new RuntimeException(e);
            }
        };

        Thread producer = new Thread(() -> {
            try {
                int count = 20;
                while (count-- > 0) {
                    if (ThreadLocalRandom.current().nextBoolean() && !sentMessages.isEmpty()) {
                        NewMessageBody body = new NewMessageBody("edited message", null, null);
                        String messageId = sentMessages.get(ThreadLocalRandom.current().nextInt(sentMessages.size()));
                        EditMessageQuery editMessageQuery = new EditMessageQuery(client2, body, messageId);
                        editMessageQuery.execute();
                        LOG.info("Message {} edited", messageId);
                        continue;
                    }

                    String text = "text " + ID_COUNTER.incrementAndGet();
                    NewMessageBody newMessage = new NewMessageBody(text, null, null);
                    SendMessageResult sendMessageResult = new SendMessageQuery(client2, newMessage)
                            .chatId(commonChatId)
                            .execute();

                    String messageId = sendMessageResult.getMessage().getBody().getMid();
                    sentMessages.add(messageId);
                    LOG.info("Message {} sent: {}", messageId, text);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                sendFinished.countDown();
            }
        });

        AtomicBoolean consumerStopped = new AtomicBoolean();
        Thread consumer = new Thread(() -> {
            Long marker = null;
            while (!consumerStopped.get()) {
                marker = getUpdates.apply(marker);
            }

            getUpdates.apply(marker);
        });


        producer.start();
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        consumer.start();

        sendFinished.await();
        consumerStopped.set(true);
        consumer.join();

        assertThat(receivedMessages, is(sentMessages));
    }

    private void flush() throws APIException, ClientException {
        Long marker = new GetUpdatesQuery(client).execute().getMarker();
        new GetUpdatesQuery(client).marker(marker).timeout(2).execute();
    }
}