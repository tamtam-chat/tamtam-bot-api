package chat.tamtam.botapi.queries;

import org.junit.Test;

import static chat.tamtam.botapi.queries.TamTamQuery.substitute;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author alexandrchuprin
 */
public class TamTamQueryTest {
    @Test
    public void should_Substitute_Params_In_Path() {
        assertThat(substitute("/"), is("/"));
        assertThat(substitute("/me"), is("/me"));
        assertThat(substitute("/me/chats", "fake_subst"), is("/me/chats"));
        assertThat(substitute("/me/chats/{chat_id}", "123"), is("/me/chats/123"));
        assertThat(substitute("/me/chats/{chat_id}/control", "123"), is("/me/chats/123/control"));
        assertThat(substitute("/me/chats/{chat_id}/control/{control_id}", "123", "456"), is("/me/chats/123/control/456"));
    }
}