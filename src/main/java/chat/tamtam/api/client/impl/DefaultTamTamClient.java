package chat.tamtam.api.client.impl;

import java.util.Objects;

import chat.tamtam.api.client.TamTamClient;
import chat.tamtam.api.client.TamTamRequest;
import chat.tamtam.api.client.TamTamSerializer;
import chat.tamtam.api.client.TamTamTransportClient;
import chat.tamtam.api.objects.User;
import chat.tamtam.api.requests.CallbackAnswer;
import chat.tamtam.api.requests.NewMessage;
import chat.tamtam.api.requests.queries.AnswerOnCallbackQuery;
import chat.tamtam.api.requests.queries.GetSubscriptionsQuery;
import chat.tamtam.api.requests.queries.MyInfoQuery;
import chat.tamtam.api.requests.queries.SendMessageQuery;
import chat.tamtam.api.requests.queries.SubscribeQuery;
import chat.tamtam.api.requests.queries.TamTamQuery;
import chat.tamtam.api.requests.queries.UnsubscribeQuery;
import chat.tamtam.api.requests.results.GetSubscriptionsResult;
import chat.tamtam.api.requests.results.SendMessageResult;
import chat.tamtam.api.requests.results.SimpleQueryResult;

/**
 * @author alexandrchuprin
 */
public class DefaultTamTamClient implements TamTamClient {
    private final String accessToken;
    private final TamTamTransportClient transport;
    private final TamTamSerializer serializer;

    public DefaultTamTamClient(String accessToken, TamTamTransportClient transport, TamTamSerializer serializer) {
        this.accessToken = accessToken;
        this.transport = transport;
        this.serializer = serializer;
    }

    public static DefaultTamTamClient create(String accessToken) {
        Objects.requireNonNull(accessToken, "No access token given. Get it using https://tt.me/primebot");
        DefaultTransportClient transport = new DefaultTransportClient();
        JacksonSerializer serializer = new JacksonSerializer();
        return new DefaultTamTamClient(accessToken, transport, serializer);
    }

    @Override
    public TamTamRequest<GetSubscriptionsResult> getSubscriptions() {
        return request(new GetSubscriptionsQuery());
    }

    @Override
    public TamTamRequest<SimpleQueryResult> subscribe(String webhookUrl) {
        return request(new SubscribeQuery(webhookUrl));
    }

    @Override
    public TamTamRequest<SimpleQueryResult> unsubscribe(String webhookUrl) {
        return request(new UnsubscribeQuery(webhookUrl));
    }

    @Override
    public TamTamRequest<SendMessageResult> sendMessage(NewMessage message) {
        return request(new SendMessageQuery(message));
    }

    @Override
    public TamTamRequest<SimpleQueryResult> answerOnCallback(String callbackId, CallbackAnswer answer) {
        return request(new AnswerOnCallbackQuery(callbackId, answer));
    }

    @Override
    public TamTamRequest<User> getMyInfo() {
        return request(new MyInfoQuery());
    }

    private <T> TamTamRequest<T> request(TamTamQuery<T> query) {
        return new TamTamRequestImpl<>(transport, serializer, accessToken, query);
    }
}
