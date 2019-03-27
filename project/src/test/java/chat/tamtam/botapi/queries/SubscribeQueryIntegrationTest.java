package chat.tamtam.botapi.queries;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.Version;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.model.GetSubscriptionsResult;
import chat.tamtam.botapi.model.SimpleQueryResult;
import chat.tamtam.botapi.model.Subscription;
import chat.tamtam.botapi.model.SubscriptionRequestBody;
import chat.tamtam.botapi.model.Update;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class SubscribeQueryIntegrationTest extends TamTamIntegrationTest {
    private String url;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        url = "https://tamtam.chat/" + System.currentTimeMillis();
    }

    @Test
    public void shouldSubscribeWebhook() throws Exception {
        SubscriptionRequestBody body = new SubscriptionRequestBody(url);
        SimpleQueryResult result = new SubscribeQuery(client, body).execute();
        assertThat(result.isSuccess(), is(true));

        GetSubscriptionsResult subscriptionsResult = new GetSubscriptionsQuery(client).execute();
        List<Subscription> subscriptions = subscriptionsResult.getSubscriptions();
        assertThat(subscriptions.get(0).getUrl(), is(url));

        new UnsubscribeQuery(client, url).execute();
    }

    @Test
    public void shouldSubscribeWebhookWithFilter() throws Exception {
        SubscriptionRequestBody body = new SubscriptionRequestBody(url);
        Set<String> updateTypes = new HashSet<>(Arrays.asList(Update.MESSAGE_CREATED, Update.MESSAGE_REMOVED));
        body.setUpdateTypes(updateTypes);

        SimpleQueryResult subscribeResult = new SubscribeQuery(client, body).execute();
        assertThat(subscribeResult.isSuccess(), is(true));

        GetSubscriptionsResult subscriptionsResult = new GetSubscriptionsQuery(client).execute();
        List<Subscription> subscriptions = subscriptionsResult.getSubscriptions();
        assertThat(subscriptions.get(0).getUrl(), is(url));
        assertThat(subscriptions.get(0).getUpdateTypes(), is(updateTypes));

        new UnsubscribeQuery(client, url).execute();
    }

    @Test
    public void shouldSubscribeWebhookWithVersion() throws Exception {
        SubscriptionRequestBody body = new SubscriptionRequestBody(url);
        body.setVersion(Version.get());

        SimpleQueryResult subscribeResult = new SubscribeQuery(client, body).execute();
        assertThat(subscribeResult.isSuccess(), is(true));

        GetSubscriptionsResult subscriptionsResult = new GetSubscriptionsQuery(client).execute();
        List<Subscription> subscriptions = subscriptionsResult.getSubscriptions();
        assertThat(subscriptions.get(0).getUrl(), is(url));
        assertThat(subscriptions.get(0).getVersion(), is(Version.get()));

        new UnsubscribeQuery(client, url).execute();
    }

    @Test(expected = APIException.class)
    public void shouldThrowOnInvalidRequest() throws Exception {
        SubscriptionRequestBody body = new SubscriptionRequestBody(url);
        body.setVersion("invalid version");
        new SubscribeQuery(client, body).execute();
    }

    @Test(expected = APIException.class)
    public void shouldThrowOnInvalidRequest2() throws Exception {
        SubscriptionRequestBody body = new SubscriptionRequestBody(url);
        body.setUpdateTypes(Collections.singleton("invalid_type"));
        new SubscribeQuery(client, body).execute();
    }
}