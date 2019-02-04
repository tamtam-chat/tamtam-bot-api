package chat.tamtam.botapi.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
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