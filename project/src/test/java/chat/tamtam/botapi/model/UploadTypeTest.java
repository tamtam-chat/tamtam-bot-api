package chat.tamtam.botapi.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author alexandrchuprin
 */
public class UploadTypeTest {
    @Test
    public void shouldConvertToStringAndBackAgain() {
        assertThat(UploadType.create(UploadType.FILE.getValue()), is(UploadType.FILE));
    }
}