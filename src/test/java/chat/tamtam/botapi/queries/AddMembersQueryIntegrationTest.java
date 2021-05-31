package chat.tamtam.botapi.queries;

import java.util.Collections;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.SimpleQueryResult;
import chat.tamtam.botapi.model.UserIdsList;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;

/**
 * @author alexandrchuprin
 */
public class AddMembersQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void testAddEmptyMembersList() throws Exception {
        Chat chat = getByTitle(getChats(client), "AddMembersQueryIntegrationTest#testAddMemberAlreadyAdded");
        SimpleQueryResult result = new AddMembersQuery(client, new UserIdsList(Collections.emptyList()),
                chat.getChatId()).execute();

        assertThat(result.isSuccess(), is(false));
        assertThat(result.getMessage(), not(isEmptyString()));
    }

    @Test
    public void testAddMembersAlreadyAdded() throws Exception {
        Chat chat = getByTitle(getChats(client), "AddMembersQueryIntegrationTest#testAddMemberAlreadyAdded");
        SimpleQueryResult result = new AddMembersQuery(client, new UserIdsList(
                Collections.singletonList(bot2.getUserId())), chat.getChatId()).execute();

        assertThat(result.isSuccess(), is(false));
        assertThat(result.getMessage(), not(isEmptyString()));
    }
}