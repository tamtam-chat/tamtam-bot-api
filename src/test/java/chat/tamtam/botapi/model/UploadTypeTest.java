package chat.tamtam.botapi.model;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import chat.tamtam.botapi.UnitTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author alexandrchuprin
 */
@Category(UnitTest.class)
public class UploadTypeTest {
    @Test
    public void shouldConvertToStringAndBackAgain() {
        assertThat(UploadType.create(UploadType.FILE.getValue()), is(UploadType.FILE));
    }
}