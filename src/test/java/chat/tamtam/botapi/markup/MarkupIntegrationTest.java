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
import chat.tamtam.botapi.model.CodeMarkup;
import chat.tamtam.botapi.model.EmphasizedMarkup;
import chat.tamtam.botapi.model.LinkMarkup;
import chat.tamtam.botapi.model.MarkupElement;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MonospacedMarkup;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.StrongMarkup;
import chat.tamtam.botapi.model.TextFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class MarkupIntegrationTest extends TamTamIntegrationTest {
    private Chat chat;
    private Chat dialog;
    private String plainText;
    private List<MarkupElement> expectedMarkup;

    @Override
    @Before
    public void setUp() throws Exception {
        chat = getByTitle(getChats(), "SendMarkupIntegrationTest");
        dialog = getByType(getChats(), ChatType.DIALOG);
        plainText = readFile("plaintext.txt");
        expectedMarkup = Arrays.asList(
                new StrongMarkup(0, 11),
                new EmphasizedMarkup(0, 11),
                new StrongMarkup(13, 6),
                new EmphasizedMarkup(13, 14),
                new EmphasizedMarkup(29, 4),
                new StrongMarkup(29, 14),
                new CodeMarkup(45, 10),
                new CodeMarkup(57, 3),
                new CodeMarkup(66, 6),
                new CodeMarkup(157, 5),
                new CodeMarkup(209, 4),
                new LinkMarkup("/uri", 215, 4),
                new LinkMarkup("/uri", 221, 4),
                new LinkMarkup("(foo)and(bar)", 233, 4),
                new StrongMarkup(316, 6),
                new StrongMarkup(338, 6),
                new EmphasizedMarkup(338, 6),
                new CodeMarkup(420, 6),
                new MonospacedMarkup(446, 39));
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
        assertThat(format.getValue(), message.getBody().getMarkup(), is(expectedMarkup));
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
            verify(format, editedMessage);
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

            verify(format, result.getMessage());
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
