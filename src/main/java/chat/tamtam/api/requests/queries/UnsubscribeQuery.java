package chat.tamtam.api.requests.queries;

import java.util.Collections;

import chat.tamtam.api.requests.results.SimpleQueryResult;

/**
 * @author alexandrchuprin
 */
public class UnsubscribeQuery extends TamTamQuery<SimpleQueryResult> {
    public UnsubscribeQuery(String webHookUrl) {
        super("/me/unsubscribe", Collections.singletonMap("url", webHookUrl), SimpleQueryResult.class, true);
    }
}
