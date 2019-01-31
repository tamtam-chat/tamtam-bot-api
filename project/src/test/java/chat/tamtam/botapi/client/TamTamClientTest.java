package chat.tamtam.botapi.client;

import org.junit.Test;

import chat.tamtam.botapi.server.TamTamService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author alexandrchuprin
 */
public class TamTamClientTest {
    @Test
    public void shouldObtainEndpointFromEnvironment() {
        String endpoint = "https://testapi.tamtam.chat";
        TamTamClient client = new TamTamClient(TamTamService.ACCESS_TOKEN, mock(TamTamTransportClient.class),
                mock(TamTamSerializer.class)) {
            @Override
            String getEnvironment(String name) {
                if (name.equals(TamTamClient.ENDPOINT_ENV_VAR_NAME)) {
                    return endpoint;
                }

                return super.getEnvironment(name);
            }
        };

        assertThat(client.getEndpoint(), is(endpoint));
    }
}