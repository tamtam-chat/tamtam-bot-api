package chat.tamtam.botapi.queries;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatAdminPermission;
import chat.tamtam.botapi.model.ChatMember;
import chat.tamtam.botapi.model.ChatType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class GetMembershipQueryIntegrationTest extends TamTamIntegrationTest {
    @Test(expected = APIException.class)
    public void shouldNotReturnPermissionsForDialog() throws Exception {
        Chat dialog = getByType(getChats(), ChatType.DIALOG);
        new GetMembershipQuery(client, dialog.getChatId()).execute();
    }

    @Test
    public void shouldReturnPermissionsForChatWhereBotIsAdmin() throws Exception {
        Chat chat = getByTitle(getChats(), "test chat #1");
        GetMembershipQuery query = new GetMembershipQuery(client, chat.getChatId());
        ChatMember chatMember = query.execute();
        assertThat(chatMember.getPermissions().size(), is(greaterThan(0)));
        assertThat(chatMember.getPermissions(), hasItem(ChatAdminPermission.WRITE));
    }

    @Test
    public void shouldReturnNullForChatWhereBotIsNOTAdmin() throws Exception {
        Chat chat = getByTitle(getChats(), "test chat #3");
        GetMembershipQuery query = new GetMembershipQuery(client, chat.getChatId());
        ChatMember chatMember = query.execute();
        assertThat(chatMember.getPermissions(), is(nullValue()));
    }
}