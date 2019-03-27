package chat.tamtam.botapi.queries;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.ChatAccessForbiddenException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatMember;
import chat.tamtam.botapi.model.ChatMembersList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author alexandrchuprin
 */
public class GetMembersQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldGetMembersOfChat() throws Exception {
        List<Chat> chats = getChats();
        Chat chat2 = getByTitle(chats, "test chat #2");
        ChatMembersList membersList = new GetMembersQuery(client, chat2.getChatId()).execute();
        Map<Long, ChatMember> members = membersList.getMembers().stream()
                .collect(Collectors.toMap(ChatMember::getUserId, Function.identity()));

        assertThat(members.size(), is(3));
        assertThat(members.get(me.getUserId()).isAdmin(), is(true));
        assertThat(members.get(bot2.getUserId()).isAdmin(), is(false));
    }

    @Test
    public void shouldGetMembersOfChatWhereBotIsNotAdmin() throws Exception {
        List<Chat> chats = getChats();
        Chat chat2 = getByTitle(chats, "test chat #3");
        ChatMembersList membersList = new GetMembersQuery(client, chat2.getChatId()).execute();
        Map<Long, ChatMember> members = membersList.getMembers().stream()
                .collect(Collectors.toMap(ChatMember::getUserId, Function.identity()));

        assertThat(members.size(), is(3));
        assertThat(members.get(me.getUserId()).isAdmin(), is(false));
        assertThat(members.get(bot2.getUserId()).isAdmin(), is(false));
    }

    @Test(expected = ChatAccessForbiddenException.class)
    public void shouldNotGetMembersOfChannelWhereBotIsNotAdmin() throws Exception {
        List<Chat> chats = getChats();
        Chat chat2 = getByTitle(chats, "test channel #2");
        ChatMembersList membersList = new GetMembersQuery(client, chat2.getChatId()).execute();
        membersList.getMembers().stream().collect(Collectors.toMap(ChatMember::getUserId, Function.identity()));
    }
}