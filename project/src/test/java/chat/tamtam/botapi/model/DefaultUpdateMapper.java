package chat.tamtam.botapi.model;

import java.util.function.Function;

/**
 * @author alexandrchuprin
 */
public class DefaultUpdateMapper<T> implements Update.Mapper<T> {
    private final Function<Update, T> defaultMapper;

    public DefaultUpdateMapper(Function<Update, T> defaultMapper) {
        this.defaultMapper = defaultMapper;
    }

    @Override
    public T map(MessageCreatedUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(MessageCallbackUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(MessageEditedUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(MessageRemovedUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(BotAddedToChatUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(BotRemovedFromChatUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(UserAddedToChatUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(UserRemovedFromChatUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(BotStartedUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(ChatTitleChangedUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(MessageConstructionRequest model) {
        return mapDefault(model);
    }

    @Override
    public T map(MessageConstructedUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T map(MessageChatCreatedUpdate model) {
        return mapDefault(model);
    }

    @Override
    public T mapDefault(Update model) {
        return defaultMapper.apply(model);
    }

}
