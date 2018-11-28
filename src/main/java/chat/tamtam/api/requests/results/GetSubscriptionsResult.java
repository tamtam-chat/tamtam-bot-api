package chat.tamtam.api.requests.results;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import chat.tamtam.api.objects.Subscription;

/**
 * @author alexandrchuprin
 */
public class GetSubscriptionsResult {
    private final List<Subscription> subscriptions;

    @JsonCreator
    public GetSubscriptionsResult(@JsonProperty("subscriptions") List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }
}
