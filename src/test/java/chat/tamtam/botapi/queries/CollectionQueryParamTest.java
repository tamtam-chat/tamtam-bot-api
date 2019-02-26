package chat.tamtam.botapi.queries;

import java.util.Arrays;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author alexandrchuprin
 */
public class CollectionQueryParamTest {
    @Test
    public void shouldFormatCollection() throws Exception {
        TamTamQuery<?> holder = mock(TamTamQuery.class);
        CollectionQueryParam<String> param = new CollectionQueryParam<>("testparam", holder);
        param.setValue(Arrays.asList("one", "two"));
        assertThat(param.format(), is("one,two"));
    }

    @Test
    public void shouldFormatToEmptyString() {
        TamTamQuery<?> holder = mock(TamTamQuery.class);
        CollectionQueryParam<String> param = new CollectionQueryParam<>("testparam", holder);
        assertThat(param.format(), is(""));
    }
}