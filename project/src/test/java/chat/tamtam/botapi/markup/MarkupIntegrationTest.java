package chat.tamtam.botapi.markup;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatType;
import chat.tamtam.botapi.model.EmphasizedMarkup;
import chat.tamtam.botapi.model.LinkMarkup;
import chat.tamtam.botapi.model.MarkupElement;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MonospacedMarkup;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.StrongMarkup;
import chat.tamtam.botapi.model.TextFormat;
import chat.tamtam.botapi.model.UserMentionMarkup;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author alexandrchuprin
 */
public class MarkupIntegrationTest extends TamTamIntegrationTest {
    private Chat chat;
    private Chat dialog;
    private String plainText;
    private List<MarkupElement> expectedMarkup;
    private List<MarkupElement> expectedDialogMarkup;

    @Before
    public void setUp() throws Exception {
        chat = getByTitle(getChats(), "SendMarkupIntegrationTest");
        dialog = getByType(getChats(), ChatType.DIALOG);
        plainText = readFile("plaintext.txt");
        expectedMarkup = expectedMarkup(false);
        expectedDialogMarkup = expectedMarkup(true);
    }

    @Test
    public void shouldNotParseMarkup() throws Exception {
        _shouldNotParse("markup.md");
        _shouldNotParse("markup.html");
    }

    @Test
    public void shouldParseMarkup() throws Exception {
        _shouldParse("markup.md", TextFormat.MARKDOWN);
        _shouldParse("markup.html", TextFormat.HTML);
    }

    @Test
    public void shouldNotParseOnEdit() throws Exception {
        _shouldNotParseOnEdit("markup.md");
        _shouldNotParseOnEdit("markup.html");
    }

    @Test
    public void shouldParseOnEdit() throws Exception {
        _shouldParseOnEdit("markup.md", TextFormat.MARKDOWN);
        _shouldParseOnEdit("markup.html", TextFormat.HTML);
    }

    private void verify(TextFormat format, Message message) {
        assertThat(format.getValue(), message.getBody().getText(), is(plainText));
        assertThat(format.getValue(), message.getBody().getMarkup(),
                is(isDialog ? expectedDialogMarkup : expectedMarkup));
    }

    private void _shouldParseOnEdit(String inputFile, TextFormat format) throws Exception {
        String textWithMarkup = readFile(inputFile);
        for (Chat chat : Arrays.asList(chat, dialog)) {
            info("Chat: " + chat.getChatId());
            SendMessageResult result = botAPI.sendMessage(new NewMessageBody(randomText(), null, null))
                    .chatId(chat.getChatId())
                    .execute();

            String messageId = result.getMessage().getBody().getMid();
            botAPI.editMessage(new NewMessageBody(textWithMarkup, null, null)
                    .format(format), messageId)
                    .execute();

            Message editedMessage = botAPI.getMessageById(messageId).execute();
            verify(format, editedMessage, chat.getType() == ChatType.DIALOG);
        }
    }

    private void _shouldNotParseOnEdit(String inputFile) throws Exception {
        String textWithMarkup = readFile(inputFile);

        for (Chat chat : Arrays.asList(chat, dialog)) {
            info("Chat: " + chat.getChatId());
            SendMessageResult result = botAPI.sendMessage(new NewMessageBody(randomText(), null, null))
                    .chatId(chat.getChatId())
                    .execute();

            String messageId = result.getMessage().getBody().getMid();
            botAPI.editMessage(new NewMessageBody(textWithMarkup, null, null), messageId).execute();

            Message editedMessage = botAPI.getMessageById(messageId).execute();
            assertThat(editedMessage.getBody().getText(), is(textWithMarkup));
            assertThat(editedMessage.getBody().getMarkup(), is(nullValue()));
        }
    }

    private void _shouldParse(String inputFile, TextFormat format) throws Exception {
        String text = readFile(inputFile);
        for (Chat chat : Arrays.asList(chat, dialog)) {
            info("Chat: " + chat.getChatId());
            SendMessageResult result = botAPI.sendMessage(new NewMessageBody(text, null, null)
                    .format(format))
                    .chatId(chat.getChatId())
                    .execute();

            verify(format, result.getMessage(), chat.getType() == ChatType.DIALOG);
        }
    }

    private void _shouldNotParse(String markupFile) throws Exception {
        String text = readFile(markupFile);
        for (Chat chat : Arrays.asList(chat, dialog)) {
            info("Chat: " + chat.getChatId());
            SendMessageResult result = botAPI.sendMessage(new NewMessageBody(text, null, null))
                    .chatId(chat.getChatId())
                    .execute();

            assertThat(result.getMessage().getBody().getText(), is(text));
            assertThat(result.getMessage().getBody().getMarkup(), is(nullValue()));
        }
    }

    private String readFile(String name) throws IOException {
        return IOUtils.toString(getClass().getClassLoader().getResourceAsStream("markup/" + name),
                StandardCharsets.UTF_8);
    }
}
