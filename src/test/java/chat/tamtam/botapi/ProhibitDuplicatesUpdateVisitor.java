package chat.tamtam.botapi;

import java.util.HashSet;
import java.util.Set;

import chat.tamtam.botapi.model.BotAddedToChatUpdate;
import chat.tamtam.botapi.model.BotRemovedFromChatUpdate;
import chat.tamtam.botapi.model.BotStartedUpdate;
import chat.tamtam.botapi.model.ChatTitleChangedUpdate;
import chat.tamtam.botapi.model.MessageCallbackUpdate;
import chat.tamtam.botapi.model.MessageConstructedUpdate;
import chat.tamtam.botapi.model.MessageConstructionRequest;
import chat.tamtam.botapi.model.MessageCreatedUpdate;
import chat.tamtam.botapi.model.MessageEditedUpdate;
import chat.tamtam.botapi.model.MessageRemovedUpdate;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.UserAddedToChatUpdate;
import chat.tamtam.botapi.model.UserRemovedFromChatUpdate;

import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class ProhibitDuplicatesUpdateVisitor extends DelegatingUpdateVisitor {
    private final Set<Update> visited = new HashSet<>();

    ProhibitDuplicatesUpdateVisitor(Update.Visitor delegate) {
        super(delegate);
    }

    @Override
    public void visit(MessageConstructionRequest model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(MessageConstructedUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(MessageCreatedUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(MessageCallbackUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(MessageEditedUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(MessageRemovedUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(BotAddedToChatUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(BotRemovedFromChatUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(UserAddedToChatUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(UserRemovedFromChatUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(BotStartedUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visit(ChatTitleChangedUpdate model) {
        saveAndCheck(model);
        super.visit(model);
    }

    @Override
    public void visitDefault(Update model) {
        saveAndCheck(model);
        super.visitDefault(model);
    }

    private void saveAndCheck(Update model) {
        boolean isNew = visited.add(model);
        if (!isNew) {
            fail(model + " is visited twice");
        }
    }
}
