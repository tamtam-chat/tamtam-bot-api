package chat.tamtam.botapi;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.experimental.categories.Category;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.client.impl.OkHttpTransportClient;

/**
 * @author alexandrchuprin
 */
@Category(IntegrationTest.class)
public class TamTamIntegrationTest {
    static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    protected TamTamClient client = new TamTamClient(getToken(), new OkHttpTransportClient(), new JacksonSerializer());
    protected TamTamBotAPI botAPI = new TamTamBotAPI(client);
    protected TamTamUploadAPI uploadAPI = new TamTamUploadAPI(client);

    private static String getToken() {
        String tokenEnv = System.getenv("TAMTAM_ACCESS_TOKEN");
        if (tokenEnv != null) {
            return tokenEnv;
        }

        return System.getProperty("tamtam.access.token");
    }
}
