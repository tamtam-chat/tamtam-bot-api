package chat.tamtam.api.client;

import chat.tamtam.api.objects.User;
import chat.tamtam.api.requests.CallbackAnswer;
import chat.tamtam.api.requests.NewMessage;
import chat.tamtam.api.requests.results.GetSubscriptionsResult;
import chat.tamtam.api.requests.results.SendMessageResult;
import chat.tamtam.api.requests.results.SimpleQueryResult;

/**
 * @author alexandrchuprin
 */
public interface TamTamClient {
    TamTamRequest<GetSubscriptionsResult> getSubscriptions();

    TamTamRequest<SimpleQueryResult> subscribe(String webhookUrl);

    TamTamRequest<SimpleQueryResult> unsubscribe(String webhookUrl);

    TamTamRequest<SendMessageResult> sendMessage(NewMessage message);

    TamTamRequest<SimpleQueryResult> answerOnCallback(String callbackId, CallbackAnswer answer);

    TamTamRequest<User> getMyInfo();
}
