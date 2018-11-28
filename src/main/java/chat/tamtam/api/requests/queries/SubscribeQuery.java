package chat.tamtam.api.requests.queries;

import java.util.Collections;

import chat.tamtam.api.requests.results.SimpleQueryResult;

/**
 * @author alexandrchuprin
 */
public class SubscribeQuery extends TamTamQuery<SimpleQueryResult> {
    public SubscribeQuery(String webHookUrl) {
        super("/me/subscribe", Collections.singletonMap("url", webHookUrl), SimpleQueryResult.class, true);
    }
}
