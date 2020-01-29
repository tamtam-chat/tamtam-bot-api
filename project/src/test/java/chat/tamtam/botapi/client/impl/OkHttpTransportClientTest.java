package chat.tamtam.botapi.client.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Level;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.tamtam.botapi.UnitTest;
import chat.tamtam.botapi.client.ClientResponse;
import chat.tamtam.botapi.exceptions.TransportClientException;
import chat.tamtam.botapi.server.TamTamServer;
import chat.tamtam.botapi.server.TamTamService;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static spark.Spark.get;

/**
 * @author alexandrchuprin
 */
@Category(UnitTest.class)
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

    @Test(expected = TransportClientException.class)
    public void shouldThrowExceptionWhenResponseBodyIsInvalid() throws Throwable {
        String path = "/shouldThrowExceptionWhenResponseBodyIsInvalid";
        get(path, (req, resp) -> {
            // too big content-length
            resp.header("Content-Length", String.valueOf(Long.MAX_VALUE));
            return "response";
        });

        OkHttpTransportClient client = new OkHttpTransportClient();
        Future<ClientResponse> futureResponse = client.get(TamTamServer.ENDPOINT + path + "?access_token=" + TamTamService.ACCESS_TOKEN);
        org.apache.log4j.Logger logger4j = org.apache.log4j.Logger.getRootLogger();
        Level currentLevel = logger4j.getLevel();
        logger4j.setLevel(Level.DEBUG);

        try {
            futureResponse.get();
        } catch (ExecutionException e) {
            throw e.getCause();
        } finally {
            logger4j.setLevel(currentLevel);
        }
    }
}