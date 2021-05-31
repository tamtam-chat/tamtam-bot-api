package chat.tamtam.botapi.model;

import org.junit.Assert;

import chat.tamtam.botapi.TestBot;

/**
 * @author alexandrchuprin
 */
public class FailByDefaultUpdateVisitor implements Update.Visitor {
    private final TestBot testBot;

    public FailByDefaultUpdateVisitor(TestBot testBot) {
        this.testBot = testBot;
    }

    @Override
    public void visit(MessageCreatedUpdate model) {
        fail(model);
    }

    @Override
    public void visit(MessageCallbackUpdate model) {
        fail(model);
    }

    @Override
    public void visit(MessageEditedUpdate model) {
        fail(model);
    }

    @Override
    public void visit(MessageRemovedUpdate model) {
        fail(model);
    }

    @Override
    public void visit(BotAddedToChatUpdate model) {
        fail(model);
    }

    @Override
    public void visit(BotRemovedFromChatUpdate model) {
        fail(model);
    }

    @Override
    public void visit(UserAddedToChatUpdate model) {
        fail(model);
    }

    @Override
    public void visit(UserRemovedFromChatUpdate model) {
        fail(model);
    }

    @Override
    public void visit(BotStartedUpdate model) {
        fail(model);
    }

    @Override
    public void visit(ChatTitleChangedUpdate model) {
        fail(model);
    }

    @Override
    public void visit(MessageConstructionRequest model) {
        fail(model);
    }

    @Override
    public void visit(MessageConstructedUpdate model) {
        fail(model);
    }

    @Override
    public void visit(MessageChatCreatedUpdate model) {
        fail(model);
    }

    @Override
    public void visitDefault(Update model) {
        fail(model);
    }

    private void fail(Update update) {
        testBot.sendToMaster("Unexpected update in test " + update);
        Assert.fail("Should not happens");
    }
}
