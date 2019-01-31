package chat.tamtam.botapi.queries;

import java.util.concurrent.ThreadLocalRandom;

import chat.tamtam.botapi.TamTamBotAPI;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.server.TamTamServer;
import chat.tamtam.botapi.server.TamTamService;

/**
 * @author alexandrchuprin
 */
public class QueryTest {
    public final TamTamClient client = TamTamClient.create(TamTamService.ACCESS_TOKEN);
    public final TamTamBotAPI api = TamTamBotAPI.create(TamTamService.ACCESS_TOKEN);

    protected TamTamClient invalidClient = new TamTamClient("accesstoken", client.getTransport(), client.getSerializer()) {
        @Override
        public String getEndpoint() {
            return "https://invalid_endpoint";
        }
    };

    static {
        System.setProperty("tamtam.botapi.endpoint", TamTamServer.ENDPOINT);
        TamTamServer.start();
    }

    protected Chat randomChat() throws Exception {
        ChatList chatList = new GetChatsQuery(client).count(Integer.MAX_VALUE).execute();
        return chatList.getChats().get(ThreadLocalRandom.current().nextInt(0, chatList.getChats().size()));
    }
}
