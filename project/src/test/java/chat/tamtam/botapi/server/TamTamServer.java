package chat.tamtam.botapi.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import chat.tamtam.botapi.client.impl.JacksonSerializer;
import spark.ResponseTransformer;
import spark.Spark;

import static spark.Spark.awaitInitialization;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.patch;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 * @author alexandrchuprin
 */
public class TamTamServer {
    public static final String ENDPOINT = "http://localhost:4567";

    public static void start() {
        JacksonSerializer mapper = new JacksonSerializer();
        TamTamService service = new TamTamService(mapper);
        before(((request, response) -> {
            boolean isUpload = request.pathInfo().startsWith("/fileupload")
                    || request.pathInfo().startsWith("/imageupload")
                    || request.pathInfo().startsWith("/avupload");

            if (isUpload) {
                return;
            }

            String accessToken = request.queryParams("access_token");
            if (!TamTamService.ACCESS_TOKEN.equals(accessToken)) {
                halt(401, "Invalid access token.");
            }
        }));

        get("/me", service::getMyInfo, service::serialize);

        get("/chats", service::getChats, service::serialize);
        get("/chats/:chatId", service::getChat, service::serialize);
        patch("/chats/:chatId", service::editChat, service::serialize);
        get("/chats/:chatId/members", service::getMembers, service::serialize);
        post("/chats/:chatId/members", service::addMembers, service::serialize);
        delete("/chats/:chatId/members", service::removeMembers, service::serialize);
        delete("/chats/:chatId/members/me", service::leaveChat, service::serialize);
        post("/chats/:chatId/actions", service::sendAction, service::serialize);

        put("/messages", service::editMessage, service::serialize);
        get("/messages", service::getMessages, service::serialize);

        post("/answers", service::answer, service::serialize);

        get("/subscriptions", service::getSubscriptions, service::serialize);
        post("/subscriptions", service::addSubscription, service::serialize);
        delete("/subscriptions", service::removeSubscription, service::serialize);

        post("/uploads", service::getUploadUrl, service::serialize);

        awaitInitialization();
    }

    public static void stop() {
        Spark.stop();
        awaitInitialization();
    }
}
