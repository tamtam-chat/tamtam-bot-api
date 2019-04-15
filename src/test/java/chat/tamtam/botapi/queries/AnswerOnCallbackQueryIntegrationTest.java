package chat.tamtam.botapi.queries;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.CallbackAnswer;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.ContactAttachmentRequest;
import chat.tamtam.botapi.model.ContactAttachmentRequestPayload;
import chat.tamtam.botapi.model.InlineKeyboardAttachment;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequestPayload;
import chat.tamtam.botapi.model.Intent;
import chat.tamtam.botapi.model.MessageBody;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.SendMessageResult;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author alexandrchuprin
 */
public class AnswerOnCallbackQueryIntegrationTest extends TamTamIntegrationTest {
    private List<Chat> chats;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        List<Chat> allChats = getChats();
        chats = Arrays.asList(
                getByType(allChats, ChatType.DIALOG),
                getByTitle(allChats, "test chat #1"),
                getByTitle(allChats, "test channel #1")
        );
    }

    @Test
    public void shouldEditMessageOnAnswer() throws Exception {
        for (Chat chat : chats) {
            CallbackButton button = new CallbackButton("payload", "button text");
            InlineKeyboardAttachmentRequestPayload keyboardPayload = new InlineKeyboardAttachmentRequestPayload(
                    Collections.singletonList(Collections.singletonList(button)));
            AttachmentRequest keyboardAttach = new InlineKeyboardAttachmentRequest(keyboardPayload);
            String text = "AnswerOnCallbackQueryIntegrationTest message " + System.currentTimeMillis();
            NewMessageBody body = new NewMessageBody(text, null, null).attachment(keyboardAttach);

            SendMessageResult result = botAPI.sendMessage(body).chatId(chat.getChatId()).execute();
            InlineKeyboardAttachment attachment =
                    (InlineKeyboardAttachment) result.getMessage().getBody().getAttachments().get(0);

            String editedText = "AnswerOnCallbackQueryIntegrationTest answer";
            ContactAttachmentRequestPayload arPayload = new ContactAttachmentRequestPayload("test", me.getUserId(), null, "+79991234567");
            AttachmentRequest contactAR = new ContactAttachmentRequest(arPayload);
            NewMessageBody answerMessage = new NewMessageBody(editedText, null, null)
                    .attachment(contactAR);
            CallbackAnswer answer = new CallbackAnswer().message(answerMessage);
            new AnswerOnCallbackQuery(client, answer, attachment.getCallbackId()).execute();

            MessageBody editedMessage = getLast(chat).getBody();
            Assert.assertThat(editedMessage.getText(), is(editedText));
            compare(Collections.singletonList(contactAR), editedMessage.getAttachments());
        }
    }
}