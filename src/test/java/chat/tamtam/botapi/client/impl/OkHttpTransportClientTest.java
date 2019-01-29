package chat.tamtam.botapi.client.impl;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import chat.tamtam.botapi.exceptions.TransportClientException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author alexandrchuprin
 */
public class OkHttpTransportClientTest {
    @Test(expected = TransportClientException.class)
    public void shouldThrowException() throws Exception {
        OkHttpTransportClient client = new OkHttpTransportClient();
        InputStream inputStream = mock(InputStream.class);
        IOException fakeException = new IOException("fake exception");
        when(inputStream.read(any(), anyInt(), anyInt())).thenThrow(fakeException);
        doThrow(fakeException).when(inputStream).close();

        client.post("http://invalidurl", "test.txt", inputStream);
    }
}