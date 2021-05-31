package chat.tamtam.botapi;

import chat.tamtam.botapi.model.BotAddedToChatUpdate;
import chat.tamtam.botapi.model.BotRemovedFromChatUpdate;
import chat.tamtam.botapi.model.BotStartedUpdate;
import chat.tamtam.botapi.model.ChatTitleChangedUpdate;
import chat.tamtam.botapi.model.MessageCallbackUpdate;
import chat.tamtam.botapi.model.MessageChatCreatedUpdate;
import chat.tamtam.botapi.model.MessageConstructedUpdate;
import chat.tamtam.botapi.model.MessageConstructionRequest;
import chat.tamtam.botapi.model.MessageCreatedUpdate;
import chat.tamtam.botapi.model.MessageEditedUpdate;
import chat.tamtam.botapi.model.MessageRemovedUpdate;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.UserAddedToChatUpdate;
import chat.tamtam.botapi.model.UserRemovedFromChatUpdate;

/**
 * @author alexandrchuprin
 */
public class GetChatId implements Update.Mapper<Long> {
    public static final Update.Mapper<Long> INSTANCE = new GetChatId();

    @Override
    public Long map(MessageCreatedUpdate model) {
        return model.getMessage().getRecipient().getChatId();
    }

    @Override
    public Long map(MessageCallbackUpdate model) {
        return model.getMessage().getRecipient().getChatId();
    }

    @Override
    public Long map(MessageEditedUpdate model) {
        return model.getMessage().getRecipient().getChatId();
    }

    @Override
    public Long map(MessageRemovedUpdate model) {
        return model.getChatId();
    }

    @Override
    public Long map(BotAddedToChatUpdate model) {
        return model.getChatId();
    }

    @Override
    public Long map(BotRemovedFromChatUpdate model) {
        return model.getChatId();
    }

    @Override
    public Long map(UserAddedToChatUpdate model) {
        return model.getChatId();
    }

    @Override
    public Long map(UserRemovedFromChatUpdate model) {
        return model.getChatId();
    }

    @Override
    public Long map(BotStartedUpdate model) {
        return model.getChatId();
    }

    @Override
    public Long map(ChatTitleChangedUpdate model) {
        return model.getChatId();
    }

    @Override
    public Long map(MessageConstructionRequest model) {
        throw new UnsupportedOperationException("Is not chat related");
    }

    @Override
    public Long map(MessageConstructedUpdate model) {
        throw new UnsupportedOperationException("Chat is unknown");
    }

    @Override
    public Long map(MessageChatCreatedUpdate model) {
        return model.getChat().getChatId();
    }

    @Override
    public Long mapDefault(Update model) {
        throw new UnsupportedOperationException("Chat is unknown");
    }
}
