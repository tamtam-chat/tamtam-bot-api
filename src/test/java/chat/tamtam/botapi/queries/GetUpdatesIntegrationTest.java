package chat.tamtam.botapi.queries;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.SerializationException;
import chat.tamtam.botapi.model.FailByDefaultUpdateVisitor;
import chat.tamtam.botapi.model.MessageCreatedUpdate;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.User;

/**
 * @author alexandrchuprin
 */
public class GetUpdatesIntegrationTest extends TamTamIntegrationTest {
    protected static final long BOT_1_BOT_3_DIALOG = bot1.getUserId() ^ bot3.getUserId();

    protected static AutoCloseable addBot3Consumer(Update.Visitor bot3updatesConsumer) {
        return bot1.addConsumer(BOT_1_BOT_3_DIALOG,
                new Bot1ToBot3RedirectingUpdateVisitor(bot3updatesConsumer));
    }

    protected static class Bot1ToBot3RedirectingUpdateVisitor extends FailByDefaultUpdateVisitor {
        private final Update.Visitor bot3updates;

        Bot1ToBot3RedirectingUpdateVisitor(Update.Visitor bot3updates) {
            super(bot1);
            this.bot3updates = bot3updates;
        }

        @Override
        public void visit(MessageCreatedUpdate model) {
            User sender = model.getMessage().getSender();
            Long senderId = sender.getUserId();
            if (senderId.equals(bot3.getUserId())) {
                Update update;
                try {
                    update = serializer.deserialize(model.getMessage().getBody().getText(), Update.class);
                } catch (SerializationException e) {
                    throw new RuntimeException(e);
                }
                update.visit(bot3updates);
                return;
            }

            onMessageCreated(model);
        }

        protected void onMessageCreated(MessageCreatedUpdate model) {
            super.visit(model);
        }
    }
}
