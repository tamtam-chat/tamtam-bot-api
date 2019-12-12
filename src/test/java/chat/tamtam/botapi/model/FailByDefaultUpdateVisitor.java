package chat.tamtam.botapi.model;

import org.junit.Assert;

/**
 * @author alexandrchuprin
 */
public class FailByDefaultUpdateVisitor implements Update.Visitor {
    @Override
    public void visit(MessageCreatedUpdate model) {
        fail();
    }

    @Override
    public void visit(MessageCallbackUpdate model) {
        fail();
    }

    @Override
    public void visit(MessageEditedUpdate model) {
        fail();
    }

    @Override
    public void visit(MessageRemovedUpdate model) {
        fail();
    }

    @Override
    public void visit(BotAddedToChatUpdate model) {
        fail();
    }

    @Override
    public void visit(BotRemovedFromChatUpdate model) {
        fail();
    }

    @Override
    public void visit(UserAddedToChatUpdate model) {
        fail();
    }

    @Override
    public void visit(UserRemovedFromChatUpdate model) {
        fail();
    }

    @Override
    public void visit(BotStartedUpdate model) {
        fail();
    }

    @Override
    public void visit(ChatTitleChangedUpdate model) {
        fail();
    }

    @Override
    public void visit(MessageConstructionRequest model) {
        fail();
    }

    @Override
    public void visit(MessageConstructedUpdate model) {
        fail();
    }

    @Override
    public void visit(MessageChatCreatedUpdate model) {
        fail();
    }

    @Override
    public void visitDefault(Update model) {
        fail();
    }

    private static void fail() {
        Assert.fail("Should not happens");
    }
}
