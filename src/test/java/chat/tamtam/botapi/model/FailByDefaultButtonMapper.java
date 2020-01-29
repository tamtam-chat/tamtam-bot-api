package chat.tamtam.botapi.model;

import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class FailByDefaultButtonMapper<T> implements Button.Mapper<T> {
    @Override
    public T map(CallbackButton model) {
        return shouldNotHappens();
    }

    @Override
    public T map(LinkButton model) {
        return shouldNotHappens();
    }

    @Override
    public T map(RequestGeoLocationButton model) {
        return shouldNotHappens();
    }

    @Override
    public T map(RequestContactButton model) {
        return shouldNotHappens();
    }

    @Override
    public T map(ChatButton model) {
        return shouldNotHappens();
    }

    @Override
    public T mapDefault(Button model) {
        return shouldNotHappens();
    }

    private T shouldNotHappens() {
        fail("Should not happens");
        return null;
    }
}
