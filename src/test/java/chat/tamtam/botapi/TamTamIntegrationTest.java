package chat.tamtam.botapi;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.client.impl.OkHttpTransportClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.model.UserWithPhoto;

/**
 * @author alexandrchuprin
 */
@Category(IntegrationTest.class)
public abstract class TamTamIntegrationTest {
    private TamTamClient client = new TamTamClient(getToken(), new OkHttpTransportClient(), new JacksonSerializer());

    protected TamTamBotAPI botAPI = new TamTamBotAPI(client);
    protected TamTamUploadAPI uploadAPI = new TamTamUploadAPI(client);

    protected UserWithPhoto getMe() throws APIException, ClientException {
        return botAPI.getMyInfo().execute();
    }

    protected List<Chat> getChats() throws APIException, ClientException {
        ChatList chatList = botAPI.getChats().count(10).execute();
        return chatList.getChats();
    }

    protected static <T> T random(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    private static String getToken() {
        String tokenEnv = System.getenv("TAMTAM_BOTAPI_TOKEN");
        if (tokenEnv != null) {
            return tokenEnv;
        }

        String property = System.getProperty("tamtam.botapi.token");
        if (property == null) {
            throw new NullPointerException("No token provided. Please set TAMTAM_BOTAPI_TOKEN environment variable.");
        }

        return property;
    }
}
