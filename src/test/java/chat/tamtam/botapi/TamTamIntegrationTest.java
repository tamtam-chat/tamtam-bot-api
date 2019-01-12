package chat.tamtam.botapi;

import java.lang.invoke.MethodHandles;

import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.client.impl.OkHttpTransportClient;
import chat.tamtam.botapi.model.User;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
@Category(IntegrationTest.class)
public abstract class TamTamIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private TamTamClient client = new TamTamClient(getToken(), new OkHttpTransportClient(), new JacksonSerializer());

    TamTamBotAPI botAPI = new TamTamBotAPI(client);
    TamTamUploadAPI uploadAPI = new TamTamUploadAPI(client);
    User me;

    @Before
    public void setUp() throws Exception {
        me = botAPI.myInfo().execute();
        assertThat(me, is(notNullValue()));
        LOG.info("Current bot: " + me);
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
