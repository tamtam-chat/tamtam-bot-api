package chat.tamtam.botapi.queries;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.AttachmentNotReadyException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.exceptions.ServiceNotAvailableException;
import chat.tamtam.botapi.exceptions.TooManyRequestsException;
import chat.tamtam.botapi.model.User;

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

    private static final chat.tamtam.botapi.model.Error ATTACH_NOT_REQDY_ERROR
            = new chat.tamtam.botapi.model.Error("attachment.not.ready", "error");

    @BeforeClass
    public static void before() {
        ObjectMapper mapper = new ObjectMapper();
        get("/serviceunavailable", ((request, response) -> halt(503)));
        get("/internalerror", ((request, response) -> halt(500, "not json body")));
        get("/emptybody", ((request, response) -> halt(500, null)));
        get("/errorbody", ((request, response) -> halt(500, mapper.writeValueAsString(ERROR))));
        get("/toomanyrequests", ((request, response) -> halt(429, mapper.writeValueAsString(TOO_MANY_REQUESTS))));
        get("/attachnotready", ((request, response) -> halt(429, mapper.writeValueAsString(ATTACH_NOT_REQDY_ERROR))));
        get("/ok", ((request, response) -> "{}"));
    }

    @Test(expected = ServiceNotAvailableException.class)
    public void shouldThrowServiceUnavailableException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/serviceunavailable", Void.class, TamTamQuery.Method.GET);
        query.execute();
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/internalerror", Void.class, TamTamQuery.Method.GET);
        query.execute();
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIException2() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/emptybody", Void.class, TamTamQuery.Method.GET);
        query.execute();
    }

    @Test(expected = APIException.class)
    public void shouldParseError() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/errorbody", Void.class, TamTamQuery.Method.GET);
        query.execute();
    }

    @Test(expected = TooManyRequestsException.class)
    public void shouldThrowTMRException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/toomanyrequests", Void.class, TamTamQuery.Method.GET);
        query.execute();
    }

    @Test(expected = AttachmentNotReadyException.class)
    public void shouldThrowANRException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(client, "/attachnotready", Void.class, TamTamQuery.Method.GET);
        query.execute();
    }

    @Test(expected = ClientException.class)
    public void shouldThrowClientException() throws Exception {
        TamTamQuery<Void> query = new TamTamQuery<>(invalidClient, "/me", Void.class, TamTamQuery.Method.GET);
        query.execute();
    }

    @Test
    public void testAsync() throws Exception {
        Future<User> future = new TamTamQuery<>(client, "/ok", User.class, TamTamQuery.Method.GET).enqueue();
        future.get();
    }

    @Test(expected = ClientException.class)
    public void testAsyncError() throws Throwable {
        Future<User> future = new TamTamQuery<>(invalidClient, "/ok", User.class, TamTamQuery.Method.GET).enqueue();
        try {
            future.get();
        } catch (ExecutionException e) {
            throw e.getCause();
        }
    }
}