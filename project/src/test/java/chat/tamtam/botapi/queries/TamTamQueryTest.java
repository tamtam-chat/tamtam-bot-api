package chat.tamtam.botapi.queries;


import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import chat.tamtam.botapi.client.ClientResponse;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.TamTamSerializer;
import chat.tamtam.botapi.client.TamTamTransportClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.AttachmentNotReadyException;
import chat.tamtam.botapi.exceptions.ChatAccessForbiddenException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.exceptions.RequiredParameterMissingException;
import chat.tamtam.botapi.exceptions.SendMessageForbiddenException;
import chat.tamtam.botapi.exceptions.ServiceNotAvailableException;
import chat.tamtam.botapi.exceptions.TooManyRequestsException;
import chat.tamtam.botapi.exceptions.TransportClientException;
import chat.tamtam.botapi.model.User;
import chat.tamtam.botapi.server.TamTamServer;
import chat.tamtam.botapi.server.TamTamService;
import okhttp3.HttpUrl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static spark.Spark.get;
import static spark.Spark.halt;

/**
 * @author alexandrchuprin
 */
public class TamTamQueryTest extends QueryTest {
    private static final chat.tamtam.botapi.model.Error ERROR
            = new chat.tamtam.botapi.model.Error("error.code", "error");

    private static final chat.tamtam.botapi.model.Error TOO_MANY_REQUESTS
            = new chat.tamtam.botapi.model.Error("too.many.requests", "error");

    private static final chat.tamtam.botapi.model.Error ATTACH_NOT_READY_ERROR
            = new chat.tamtam.botapi.model.Error("attachment.not.ready", "error");

    private static final chat.tamtam.botapi.model.Error CHAT_DENIED_ERROR
            = new chat.tamtam.botapi.model.Error("chat.denied", "error");

    private static final chat.tamtam.botapi.model.Error CANNON_SEND_ERROR
            = new chat.tamtam.botapi.model.Error("chat.denied", "chat.send.msg.no.permission.because.not.admin");

    private static final Future<ClientResponse> INTERRUPTING_FUTURE = new Future<ClientResponse>() {
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public ClientResponse get() throws InterruptedException, ExecutionException {
            throw new InterruptedException("test interruption");
        }

        @Override
        public ClientResponse get(long timeout, @NotNull TimeUnit unit) throws InterruptedException,
                ExecutionException, TimeoutException {
            throw new InterruptedException("test interruption");
        }
    };

    @BeforeClass
    public static void before() {
        ObjectMapper mapper = new ObjectMapper();
        get("/serviceunavailable", ((request, response) -> halt(503)));
        get("/internalerror", ((request, response) -> halt(500, "not json body")));
        get("/emptybody", ((request, response) -> halt(500, null)));
        get("/errorbody", ((request, response) -> halt(500, mapper.writeValueAsString(ERROR))));
        get("/toomanyrequests", ((request, response) -> halt(429, mapper.writeValueAsString(TOO_MANY_REQUESTS))));
        get("/attachnotready", ((request, response) -> halt(429, mapper.writeValueAsString(ATTACH_NOT_READY_ERROR))));
        get("/accessdenied", ((request, response) -> halt(403, mapper.writeValueAsString(CHAT_DENIED_ERROR))));
        get("/cannotsend", ((request, response) -> halt(429, mapper.writeValueAsString(CANNON_SEND_ERROR))));
        get("/ok", ((request, response) -> "{}"));
    }

    @Test(expected = ServiceNotAvailableException.class)
    public void shouldThrowServiceUnavailableException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/serviceunavailable", Void.class,
                TamTamTransportClient.Method.GET);
        query.execute();
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/internalerror", Void.class,
                TamTamTransportClient.Method.GET);
        query.execute();
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIException2() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/emptybody", Void.class, TamTamTransportClient.Method.GET);
        query.execute();
    }

    @Test(expected = APIException.class)
    public void shouldParseError() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/errorbody", Void.class, TamTamTransportClient.Method.GET);
        query.execute();
    }

    @Test(expected = TooManyRequestsException.class)
    public void shouldThrowTMRException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/toomanyrequests", Void.class,
                TamTamTransportClient.Method.GET);
        query.execute();
    }

    @Test(expected = AttachmentNotReadyException.class)
    public void shouldThrowANRException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/attachnotready", Void.class,
                TamTamTransportClient.Method.GET);
        query.execute();
    }

    @Test(expected = ChatAccessForbiddenException.class)
    public void shouldThrowAccessDeniedException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/accessdenied", Void.class,
                TamTamTransportClient.Method.GET);
        query.execute();
    }

    @Test(expected = SendMessageForbiddenException.class)
    public void shouldThrowSMFException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/cannotsend", Void.class,
                TamTamTransportClient.Method.GET);
        query.execute();
    }

    @Test(expected = ClientException.class)
    public void shouldThrowClientException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(invalidClient, "/me", Void.class, TamTamTransportClient.Method.GET);
        query.execute();
    }

    @Test
    public void testAsync() throws Exception {
        Future<User> future = new TamTamQuery<>(client, "/ok", User.class, TamTamTransportClient.Method.GET).enqueue();
        future.get();
    }

    @Test(expected = ClientException.class)
    public void testAsyncError() throws Throwable {
        Future<User> future = new TamTamQuery<>(invalidClient, "/ok", User.class,
                TamTamTransportClient.Method.GET).enqueue();
        try {
            future.get();
        } catch (ExecutionException e) {
            throw e.getCause();
        }
    }

    @Test(expected = ClientException.class)
    public void shouldThrowExceptionOnUnsupportedMethodCall() throws Exception {
        new TamTamQuery<>(client, "/me", User.class, TamTamTransportClient.Method.OPTIONS).execute();
    }

    @Test(expected = ClientException.class)
    public void shouldWrapTransportException() throws Exception {
        TamTamTransportClient transport = mock(TamTamTransportClient.class);
        TamTamSerializer serializer = mock(TamTamSerializer.class);
        when(transport.post(anyString(), any(byte[].class))).thenThrow(new TransportClientException("test exception"));
        TamTamClient clientMock = new TamTamClient(TamTamService.ACCESS_TOKEN, transport, serializer);
        new TamTamQuery<>(clientMock, "/me", User.class, TamTamTransportClient.Method.POST).execute();
    }

    @Test
    public void shouldAppendParamsToUrlIfItAlreadyHasParams() throws Exception {
        TamTamQuery<User> query = new TamTamQuery<>(client, "/me?param=value", User.class,
                TamTamTransportClient.Method.GET);
        String param2Name = "param2";
        String param2Value = "value2";
        QueryParam<String> param2 = new QueryParam<>(param2Name, query);
        param2.setValue(param2Value);
        String url = client.buildURL(query);
        HttpUrl parsed = HttpUrl.parse(url);
        assertThat(parsed.queryParameter("param"), is("value"));
        assertThat(parsed.queryParameter(param2Name), is(param2Value));
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowExceptionIfParamIsMissing() throws Exception {
        TamTamQuery<User> query = new TamTamQuery<>(client, "/me", User.class, TamTamTransportClient.Method.GET);
        String param2Name = "param2";
        new QueryParam<String>(param2Name, query).required();
        client.buildURL(query);
    }

    @Test(expected = ClientException.class)
    public void shouldWrapInterruptedException() throws Exception {
        TamTamTransportClient transport = mock(TamTamTransportClient.class);
        TamTamSerializer serializer = mock(TamTamSerializer.class);
        when(transport.post(anyString(), any(byte[].class))).thenReturn(INTERRUPTING_FUTURE);

        TamTamClient clientMock = new TamTamClient(TamTamService.ACCESS_TOKEN, transport, serializer);
        new TamTamQuery<>(clientMock, "/me", User.class, TamTamTransportClient.Method.POST).execute();
    }

    @Test(expected = ClientException.class)
    public void shouldWrapEncodingException() throws Exception {
        TamTamClient client = new TamTamClient(TamTamService.ACCESS_TOKEN, transport, serializer) {
            @Override
            public String getEndpoint() {
                return TamTamServer.ENDPOINT;
            }

            @Override
            protected String encodeParam(String paramValue) throws UnsupportedEncodingException {
                throw new UnsupportedEncodingException("test");
            }
        };

        TamTamQuery<User> query = new TamTamQuery<User>(client, "/me", User.class, TamTamTransportClient.Method.POST);

        new QueryParam<>("param", "value", query);
        client.buildURL(query);
    }
}