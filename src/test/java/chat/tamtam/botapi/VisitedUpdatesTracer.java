package chat.tamtam.botapi;

import java.util.HashSet;
import java.util.Set;

import chat.tamtam.botapi.model.BotAddedToChatUpdate;
import chat.tamtam.botapi.model.BotRemovedFromChatUpdate;
import chat.tamtam.botapi.model.BotStartedUpdate;
import chat.tamtam.botapi.model.ChatTitleChangedUpdate;
import chat.tamtam.botapi.model.MessageCallbackUpdate;
import chat.tamtam.botapi.model.MessageCreatedUpdate;
import chat.tamtam.botapi.model.MessageEditedUpdate;
import chat.tamtam.botapi.model.MessageRemovedUpdate;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.UserAddedToChatUpdate;
import chat.tamtam.botapi.model.UserRemovedFromChatUpdate;

/**
 * @author alexandrchuprin
 */
public class VisitedUpdatesTracer extends DelegatingUpdateVisitor {
    private final Set<String> notVisited = new HashSet<>(Update.TYPES);
    private final Set<String> visited = new HashSet<>();

    public VisitedUpdatesTracer(Update.Visitor delegate) {
        super(delegate);
    }

    @Override
    public void visit(MessageCreatedUpdate model) {
        super.visit(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    @Override
    public void visit(MessageCallbackUpdate model) {
        super.visit(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    @Override
    public void visit(MessageEditedUpdate model) {
        super.visit(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    @Override
    public void visit(MessageRemovedUpdate model) {
        super.visit(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    @Override
    public void visit(BotAddedToChatUpdate model) {
        super.visit(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    @Override
    public void visit(BotRemovedFromChatUpdate model) {
        super.visit(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    @Override
    public void visit(UserAddedToChatUpdate model) {
        super.visit(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    @Override
    public void visit(UserRemovedFromChatUpdate model) {
        super.visit(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    @Override
    public void visit(BotStartedUpdate model) {
        super.visit(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    @Override
    public void visit(ChatTitleChangedUpdate model) {
        super.visit(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    @Override
    public void visitDefault(Update model) {
        super.visitDefault(model);
        notVisited.remove(model.getType());
        visited.add(model.getType());
    }

    public Set<String> getNotVisited() {
        return notVisited;
    }

    public Set<String> getVisited() {
        return visited;
    }
}
