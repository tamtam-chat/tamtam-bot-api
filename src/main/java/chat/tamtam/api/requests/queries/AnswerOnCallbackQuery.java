package chat.tamtam.api.requests.queries;

import chat.tamtam.api.client.impl.RequestParam;
import chat.tamtam.api.requests.CallbackAnswer;
import chat.tamtam.api.requests.results.SimpleQueryResult;

/**
 * @author alexandrchuprin
 */
public class AnswerOnCallbackQuery extends TamTamQuery<SimpleQueryResult> {
    private final RequestParam<String> callbackId = new RequestParam<String>("callback_id", this).required();

    public AnswerOnCallbackQuery(String callbackId, CallbackAnswer answer) {
        super("/me/answer", answer, SimpleQueryResult.class, true);
        this.callbackId.setValue(callbackId);
    }
}
