package chat.tamtam.botapi.server;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        ObjectMapper mapper = new ObjectMapper();
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

        get("/me", service::getMyInfo, mapper::writeValueAsString);

        get("/chats", service::getChats, mapper::writeValueAsString);
        get("/chats/:chatId", service::getChat, mapper::writeValueAsString);
        patch("/chats/:chatId", service::editChat, mapper::writeValueAsString);
        get("/chats/:chatId/members", service::getMembers, mapper::writeValueAsString);
        post("/chats/:chatId/members", service::addMembers, mapper::writeValueAsString);
        delete("/chats/:chatId/members", service::removeMembers, mapper::writeValueAsString);
        delete("/chats/:chatId/members/me", service::leaveChat, mapper::writeValueAsString);
        post("/chats/:chatId/actions", service::sendAction, mapper::writeValueAsString);

        post("/messages", service::sendMessage, mapper::writeValueAsString);
        put("/messages", service::editMessage, mapper::writeValueAsString);
        get("/messages", service::getMessages, mapper::writeValueAsString);

        post("/answers", service::answer, mapper::writeValueAsString);

        get("/subscriptions", service::getSubscriptions, mapper::writeValueAsString);
        post("/subscriptions", service::addSubscription, mapper::writeValueAsString);
        delete("/subscriptions", service::removeSubscription, mapper::writeValueAsString);

        post("/uploads", service::getUploadUrl, mapper::writeValueAsString);

        get("/updates", service::getUpdates, mapper::writeValueAsString);

        awaitInitialization();
    }

    public static void stop() {
        Spark.stop();
        awaitInitialization();
    }
}
