package chat.tamtam.botapi.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author alexandrchuprin
 */
public class UpdateTest {
    @Test
    public void shouldVisiDefault() {
        Update update = new Update(System.currentTimeMillis());
        update.visit(new FailByDefaultUpdateVisitor() {
            @Override
            public void visitDefault(Update model) {
                assertThat(model, is(update));
            }
        });
    }
}