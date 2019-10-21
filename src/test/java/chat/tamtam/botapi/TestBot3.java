package chat.tamtam.botapi;

import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.queries.SendMessageQuery;

/**
 * @author alexandrchuprin
 */
public class TestBot3 extends TestBot {
    private final Chat controlChat;
    private final TamTamClient controlBot;

    public TestBot3(TamTamClient bot3client, TamTamClient controlBot, boolean isTravis) throws APIException, ClientException {
        super(bot3client, isTravis);
        this.controlBot = controlBot;
        this.controlChat = findControlChat();
    }

    public void pressCallbackButton(String messageId, String payload) throws APIException, ClientException {
        sendCommand(String.format("/press_callback_button %s %s", messageId, payload));
    }

    public void startYourself(long userId, @Nullable String payload) throws Exception {
        String command = String.format("/start_bot --session=%d %d", userId, getUserId());
        if (payload != null) {
            command += String.format(" \"%s\"", payload);
        }

        sendCommand(command);
    }

    public void startAnotherBot(long botId, @Nullable String payload) throws APIException, ClientException {
        String command = String.format("/start_bot %d", botId);
        if (payload != null) {
            command += String.format(" \"%s\"", payload);
        }

        sendCommand(command);
    }

    public void joinChat(String link) throws APIException, ClientException {
        sendCommand(String.format("/join_chat %s", Objects.requireNonNull(link, "link")));
    }

    public void leaveChat(Long chatId) throws APIException, ClientException {
        sendCommand(String.format("/leave_chat %d", chatId));
    }

    private void sendCommand(String command) throws APIException, ClientException {
        NewMessageBody body = new NewMessageBody(command, null, null);
        new SendMessageQuery(controlBot, body).chatId(controlChat.getChatId()).disableLinkPreview(true).execute();
    }

    private Chat findControlChat() throws APIException, ClientException {
        ChatList chatList = api.getChats().count(100).execute();
        return chatList.getChats().stream()
                .filter(c -> "test bot 3 control chat".equals(c.getTitle()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Control chat not found!"));
    }
}
