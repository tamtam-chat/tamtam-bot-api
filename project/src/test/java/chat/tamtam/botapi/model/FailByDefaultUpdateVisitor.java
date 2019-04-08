package chat.tamtam.botapi.model;

import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class FailByDefaultUpdateVisitor implements Update.Visitor {
    @Override
    public void visit(MessageCreatedUpdate model) {
        fail("Should not happens");
    }

    @Override
    public void visit(MessageCallbackUpdate model) {
        fail("Should not happens");
    }

    @Override
    public void visit(MessageEditedUpdate model) {
        fail("Should not happens");
    }

    @Override
    public void visit(MessageRemovedUpdate model) {
        fail("Should not happens");
    }

    @Override
    public void visit(BotAddedToChatUpdate model) {
        fail("Should not happens");
    }

    @Override
    public void visit(BotRemovedFromChatUpdate model) {
        fail("Should not happens");
    }

    @Override
    public void visit(UserAddedToChatUpdate model) {
        fail("Should not happens");
    }

    @Override
    public void visit(UserRemovedFromChatUpdate model) {
        fail("Should not happens");
    }

    @Override
    public void visit(BotStartedUpdate model) {
        fail("Should not happens");
    }

    @Override
    public void visit(ChatTitleChangedUpdate model) {
        fail("Should not happens");
    }

    @Override
    public void visitDefault(Update model) {
        fail("Should not happens");
    }
}
