package chat.tamtam.botapi.client;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class ClientResponseTest {
    @Test
    public void shouldReturnHeaders() {
        Map<String, String> headers = Collections.singletonMap("Header", "Value");
        ClientResponse response = new ClientResponse(200, null, headers);
        assertThat(response.getHeaders(), is(sameInstance(headers)));
    }

    @Test
    public void shouldReturnBody() {
        byte[] body = new byte[]{1, 2, 3};
        ClientResponse response = new ClientResponse(200, body, Collections.emptyMap());
        assertThat(response.getBody(), is(sameInstance(body)));
    }
}