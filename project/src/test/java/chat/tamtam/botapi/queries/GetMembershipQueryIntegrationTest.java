package chat.tamtam.botapi.queries;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import static org.hamcrest.Matchers.hasSize;
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
        Map<Boolean, List<Chat>> chats = getChats().stream()
                .filter(c -> c.getType() != ChatType.DIALOG)
                .collect(Collectors.groupingBy(c -> c.getTitle().contains("bot is admin")));

        assertThat(chats.get(true), hasSize(greaterThan(0)));
        assertThat(chats.get(false), hasSize(greaterThan(0)));

        for (Chat chatWherBotIsAdmin : chats.get(true)) {
            GetMembershipQuery query = new GetMembershipQuery(client, chatWherBotIsAdmin.getChatId());
            ChatMember chatMember = query.execute();
            assertThat(chatMember.getPermissions().size(), is(greaterThan(0)));
            assertThat(chatMember.getPermissions(), hasItem(ChatAdminPermission.WRITE));
        }

        for (Chat chatWherBotIsNotAdmin : chats.get(false)) {
            GetMembershipQuery query = new GetMembershipQuery(client, chatWherBotIsNotAdmin.getChatId());
            ChatMember chatMember = query.execute();
            assertThat(chatMember.getPermissions(), is(nullValue()));
        }
    }
}