package chat.tamtam.botapi.queries;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.BotStartedUpdate;
import chat.tamtam.botapi.model.Callback;
import chat.tamtam.botapi.model.CallbackAnswer;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ContactAttachmentRequest;
import chat.tamtam.botapi.model.ContactAttachmentRequestPayload;
import chat.tamtam.botapi.model.FailByDefaultUpdateVisitor;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequestPayload;
import chat.tamtam.botapi.model.MessageBody;
import chat.tamtam.botapi.model.MessageCallbackUpdate;
import chat.tamtam.botapi.model.MessageCreatedUpdate;
import chat.tamtam.botapi.model.MessageEditedUpdate;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.SendMessageResult;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class AnswerOnCallbackQueryIntegrationTest extends TamTamIntegrationTest {
    private List<Chat> chats;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        bot1.start();
        List<Chat> allChats = getChats();
        chats = Arrays.asList(
                getByTitle(allChats, "test chat #1"),
                getByTitle(allChats, "test channel #1"),
                getChat(bot1.getUserId() ^ bot3.getUserId())
        );
    }

    @After
    public void tearDown() throws Exception {
        bot1.stop();
    }

    @Test
    public void shouldEditMessageOnAnswer() throws Exception {
        bot3.startAnotherBot(bot1.getUserId(), null);

        ArrayBlockingQueue<Callback> callbacks = new ArrayBlockingQueue<>(chats.size());
        CountDownLatch done = new CountDownLatch(1);
        FailByDefaultUpdateVisitor consumer = new FailByDefaultUpdateVisitor() {
            @Override
            public void visit(MessageCallbackUpdate model) {
                callbacks.add(model.getCallback());
            }

            @Override
            public void visit(BotStartedUpdate model) {
                assertThat(model.getUser().getUserId(), is(bot3.getUserId()));
            }

            @Override
            public void visit(MessageCreatedUpdate model) {
                // bot 3 will reply to bot 1 that it has received `message_edited` update
                // will wait to finish test
                assertThat(model.getMessage().getSender().getUserId(), is(bot3.getUserId()));
                done.countDown();
            }
        };

        bot1.addConsumer(consumer);

        try {
            for (Chat chat : chats) {
                String payload = randomText(16);
                CallbackButton button = new CallbackButton(payload, "button text");
                InlineKeyboardAttachmentRequestPayload keyboardPayload = new InlineKeyboardAttachmentRequestPayload(
                        Collections.singletonList(Collections.singletonList(button)));
                AttachmentRequest keyboardAttach = new InlineKeyboardAttachmentRequest(keyboardPayload);
                String text = "AnswerOnCallbackQueryIntegrationTest message " + now();
                NewMessageBody body = new NewMessageBody(text, null, null).attachment(keyboardAttach);

                SendMessageResult result = botAPI.sendMessage(body).chatId(chat.getChatId()).execute();
                String messageId = result.getMessage().getBody().getMid();
                bot3.pressCallbackButton(messageId, payload);

                String editedText = randomText();
                ContactAttachmentRequestPayload arPayload = new ContactAttachmentRequestPayload(randomText(16),
                        bot1.getUserId(), null, "+79991234567");
                AttachmentRequest contactAR = new ContactAttachmentRequest(arPayload);
                NewMessageBody answerMessage = new NewMessageBody(editedText, Collections.singletonList(contactAR), null);

                CallbackAnswer answer = new CallbackAnswer().message(answerMessage);
                Callback callback = callbacks.poll(10, TimeUnit.SECONDS);
                new AnswerOnCallbackQuery(client, answer, callback.getCallbackId()).execute();

                MessageBody editedMessage = getMessage(client, messageId).getBody();

                assertThat(editedMessage.getText(), is(editedText));
                compare(Collections.singletonList(contactAR), editedMessage.getAttachments());
            }

            await(done);
        } finally {
            bot1.removeConsumer(consumer);
        }
    }
}