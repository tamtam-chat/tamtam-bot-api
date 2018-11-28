package chat.tamtam.api.requests.queries;

import chat.tamtam.api.requests.results.GetSubscriptionsResult;

/**
 * @author alexandrchuprin
 */
public class GetSubscriptionsQuery extends TamTamQuery<GetSubscriptionsResult> {
    public GetSubscriptionsQuery() {
        super("/me/subscriptions", GetSubscriptionsResult.class, true);
    }
}
