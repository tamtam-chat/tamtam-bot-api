package chat.tamtam.botapi.model;

import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class FailByDefaultButtonVisitor implements Button.Visitor {
    @Override
    public void visit(CallbackButton model) {
        fail("Should not happens");
    }

    @Override
    public void visit(LinkButton model) {
        fail("Should not happens");
    }

    @Override
    public void visit(RequestGeoLocationButton model) {
        fail("Should not happens");
    }

    @Override
    public void visit(RequestContactButton model) {
        fail("Should not happens");
    }

    @Override
    public void visitDefault(Button model) {
        fail("Should not happens");
    }
}
