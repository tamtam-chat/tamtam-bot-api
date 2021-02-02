package chat.tamtam.botapi.model;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import chat.tamtam.botapi.UnitTest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author alexandrchuprin
 */
@Category(UnitTest.class)
public class AttachmentRequestTest {
    @Test
    public void shouldVisitDefault() {
        AttachmentRequest attachmentRequest = new AttachmentRequest();
        attachmentRequest.visit(new FailByDefaultARVisitor() {
            @Override
            public void visitDefault(AttachmentRequest model) {
                assertThat(model, is(attachmentRequest));
            }
        });
    }

}