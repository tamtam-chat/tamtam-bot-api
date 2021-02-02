package chat.tamtam.botapi.queries;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;

/**
 * @author alexandrchuprin
 */
public class SubscribeQueryIntegrationTest extends TamTamIntegrationTest {
    private String url;

    @Before
    public void setUp() {
        url = "https://" + randomText(16) + ".com";
    }

    @Test
    public void shouldSubscribeWebhook() throws Exception {
        try {
            SubscriptionRequestBody body = new SubscriptionRequestBody(url);
            SimpleQueryResult result = new SubscribeQuery(client, body).execute();
            assertThat(result.isSuccess(), is(true));

            GetSubscriptionsResult subscriptionsResult = new GetSubscriptionsQuery(client).execute();
            List<Subscription> subscriptions = subscriptionsResult.getSubscriptions();
            assertThat(subscriptions.get(0).getUrl(), is(url));
        } finally {
            new UnsubscribeQuery(client, url).execute();
        }
    }

    @Test
    public void shouldSubscribeWebhookWithFilter() throws Exception {
        try {
            SubscriptionRequestBody body = new SubscriptionRequestBody(url);
            Set<String> updateTypes = new HashSet<>(Arrays.asList(Update.MESSAGE_CREATED, Update.MESSAGE_CALLBACK));
            body.setUpdateTypes(updateTypes);

            SimpleQueryResult subscribeResult = new SubscribeQuery(client, body).execute();
            assertThat(subscribeResult.isSuccess(), is(true));

            GetSubscriptionsResult subscriptionsResult = new GetSubscriptionsQuery(client).execute();
            List<Subscription> subscriptions = subscriptionsResult.getSubscriptions();
            assertThat(subscriptions.get(0).getUrl(), is(url));
            assertThat(subscriptions.get(0).getUpdateTypes(), is(updateTypes));
        } finally {
            new UnsubscribeQuery(client, url).execute();
        }
    }

    @Test
    public void shouldSubscribeWebhookWithVersion() throws Exception {
        try {
            SubscriptionRequestBody body = new SubscriptionRequestBody(url);
            body.setVersion(Version.get());

            SimpleQueryResult subscribeResult = new SubscribeQuery(client, body).execute();
            assertThat(subscribeResult.isSuccess(), is(true));

            GetSubscriptionsResult subscriptionsResult = new GetSubscriptionsQuery(client).execute();
            List<Subscription> subscriptions = subscriptionsResult.getSubscriptions();
            assertThat(subscriptions.get(0).getUrl(), is(url));
            assertThat(subscriptions.get(0).getVersion(), is(Version.get()));
        } finally {
            new UnsubscribeQuery(client, url).execute();
        }
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

    @Test(expected = APIException.class)
    public void shouldThrowOnInvalidURL() throws Exception {
        SubscriptionRequestBody body = new SubscriptionRequestBody("https://invalid url");
        new SubscribeQuery(client, body).execute();
    }

    @Test
    public void shouldUnsubscribeAnotherBotWithTheSameWebhook() throws Exception {
        try {
            SubscriptionRequestBody requestBody = new SubscriptionRequestBody(url);
            new SubscribeQuery(client, requestBody).execute();
            new SubscribeQuery(client2, requestBody).execute();

            Set<String> bot1subscriptions = new GetSubscriptionsQuery(client).execute()
                    .getSubscriptions().stream().map(Subscription::getUrl).collect(Collectors.toSet());

            Set<String> bot2subscriptions = new GetSubscriptionsQuery(client2).execute()
                    .getSubscriptions().stream().map(Subscription::getUrl).collect(Collectors.toSet());

            assertThat(bot1subscriptions, is(empty()));
            assertThat(bot2subscriptions, hasItem(url));
        } finally {
            new UnsubscribeQuery(client, url).execute();
            new UnsubscribeQuery(client2, url).execute();
        }
    }
}