package chat.tamtam.botapi.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class TamTamEnumTest {
    @Test
    public void shouldCreate() {
        ChatType chatType = TamTamEnum.create(ChatType.class, "dialog");
        assertThat(chatType, is(ChatType.DIALOG));
    }

    @Test
    public void shouldReturnNull() {
        ChatType chatType = TamTamEnum.create(ChatType.class, null);
        assertThat(chatType, is(nullValue()));
    }
}