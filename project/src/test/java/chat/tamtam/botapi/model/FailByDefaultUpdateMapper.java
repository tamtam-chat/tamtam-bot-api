package chat.tamtam.botapi.model;

import org.junit.Assert;

/**
 * @author alexandrchuprin
 */
public class FailByDefaultUpdateMapper<T> implements Update.Mapper<T> {


    @Override
    public T map(MessageCreatedUpdate model) {
        return fail();
    }

    @Override
    public T map(MessageCallbackUpdate model) {
        return fail();
    }

    @Override
    public T map(MessageEditedUpdate model) {
        return fail();
    }

    @Override
    public T map(MessageRemovedUpdate model) {
        return fail();
    }

    @Override
    public T map(BotAddedToChatUpdate model) {
        return fail();
    }

    @Override
    public T map(BotRemovedFromChatUpdate model) {
        return fail();
    }

    @Override
    public T map(UserAddedToChatUpdate model) {
        return fail();
    }

    @Override
    public T map(UserRemovedFromChatUpdate model) {
        return fail();
    }

    @Override
    public T map(BotStartedUpdate model) {
        return fail();
    }

    @Override
    public T map(ChatTitleChangedUpdate model) {
        return fail();
    }

    @Override
    public T map(MessageConstructionRequest model) {
        return fail();
    }

    @Override
    public T map(MessageConstructedUpdate model) {
        return fail();
    }

    @Override
    public T map(MessageChatCreatedUpdate model) {
        return fail();
    }

    @Override
    public T mapDefault(Update model) {
        return fail();
    }

    private static <T> T fail() {
        Assert.fail("Should not happens");
        return null;
    }
}
