package chat.tamtam.api.requests.queries;

import chat.tamtam.api.requests.NewMessage;
import chat.tamtam.api.requests.results.SendMessageResult;

/**
 * @author alexandrchuprin
 */
public class SendMessageQuery extends TamTamQuery<SendMessageResult> {
    public SendMessageQuery(NewMessage newMessage) {
        super("/me/messages", newMessage, SendMessageResult.class, true);
    }
}
