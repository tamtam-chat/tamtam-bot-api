package chat.tamtam.botapi.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class ButtonTest {
    @Test
    public void shouldVisitDefault() {
        Button button = new Button("text", Intent.POSITIVE);
        button.visit(new FailByDefaultButtonVisitor() {
            @Override
            public void visitDefault(Button model) {
                assertThat(model, is(button));
            }
        });
    }
}