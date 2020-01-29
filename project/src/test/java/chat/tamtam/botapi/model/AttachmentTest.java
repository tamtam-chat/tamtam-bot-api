package chat.tamtam.botapi.model;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import chat.tamtam.botapi.UnitTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
@Category(UnitTest.class)
public class AttachmentTest {
    @Test
    public void shouldVisitDefault() {
        Attachment attachment = new Attachment() {
            @Override
            public boolean equals(Object obj) {
                return obj == this;
            }

            @Override
            public String toString() {
                return "Attachment{}";
            }

        };

        attachment.visit(new FailByDefaultAttachmentVisitor() {
            @Override
            public void visitDefault(Attachment model) {
                assertThat(model, is(attachment));
            }
        });
    }
}