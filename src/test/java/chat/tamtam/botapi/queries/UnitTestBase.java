package chat.tamtam.botapi.queries;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.experimental.categories.Category;

import chat.tamtam.botapi.TamTamBotAPI;
import chat.tamtam.botapi.UnitTest;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.client.impl.OkHttpTransportClient;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.server.TamTamServer;
import chat.tamtam.botapi.server.TamTamService;

/**
 * @author alexandrchuprin
 */
@Category(UnitTest.class)
public class UnitTestBase extends TamTamService {
    protected static final AtomicLong ID_COUNTER = new AtomicLong();

    protected OkHttpTransportClient transport = new OkHttpTransportClient();
    JacksonSerializer serializer = new JacksonSerializer();
    public final TamTamClient client = new TamTamClient(TamTamService.ACCESS_TOKEN, transport, serializer) {
        @Override
        public String getEndpoint() {
            return TamTamServer.ENDPOINT;
        }
    };

    public final TamTamBotAPI api = new TamTamBotAPI(client);

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

    public UnitTestBase() {
        super(new JacksonSerializer());
    }

    protected Chat randomChat() throws Exception {
        ChatList chatList = new GetChatsQuery(client).count(Integer.MAX_VALUE).execute();
        return chatList.getChats().get(ThreadLocalRandom.current().nextInt(0, chatList.getChats().size()));
    }
}
