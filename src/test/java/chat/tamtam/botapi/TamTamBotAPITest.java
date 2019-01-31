package chat.tamtam.botapi;

import org.junit.Test;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.TamTamSerializer;
import chat.tamtam.botapi.client.TamTamTransportClient;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;

/**
 * @author alexandrchuprin
 */
public class TamTamBotAPITest {
    @Test
    public void shouldConstructEqualObjects() {
        TamTamTransportClient transport = mock(TamTamTransportClient.class);
        TamTamSerializer serializer = mock(TamTamSerializer.class);
        String accessToken = "access_token";
        TamTamClient client = new TamTamClient(accessToken, transport, serializer);
        TamTamBotAPI api = new TamTamBotAPI(accessToken, transport, serializer);
        assertThat(api.client.getTransport(), sameInstance(client.getTransport()));
        assertThat(api.client.getSerializer(), sameInstance(client.getSerializer()));
    }
}