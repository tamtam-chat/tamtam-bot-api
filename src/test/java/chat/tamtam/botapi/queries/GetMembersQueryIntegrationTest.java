package chat.tamtam.botapi.queries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.ChatAccessForbiddenException;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatMember;
import chat.tamtam.botapi.model.ChatMembersList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
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

    @Test
    public void shouldGetMembersByIds() throws Exception {
        List<Chat> chats = getChats();
        Chat chat = getByTitle(chats, "test chat #1");
        HashSet<Long> ids = new HashSet<>(Arrays.asList(me.getUserId(), bot2.getUserId()));
        ChatMembersList members = new GetMembersQuery(client, chat.getChatId()).userIds(ids).execute();
        assertThat(members.getMarker(), is(nullValue()));
        assertThat(members.getMembers().size(), is(ids.size()));

        Map<Long, ChatMember> byId = members.getMembers().stream().collect(
                Collectors.toMap(ChatMember::getUserId, Function.identity()));

        ChatMember myMembership = byId.get(me.getUserId());
        ChatMember bot2Membership = byId.get(bot2.getUserId());
        assertThat(myMembership.getPermissions(), is(not(empty())));
        assertThat(myMembership.isAdmin(), is(true));
        assertThat(myMembership.getUsername(), is(me.getUsername()));
        assertThat(bot2Membership.isAdmin(), is(false));
        assertThat(bot2Membership.getPermissions(), is(nullValue()));
    }

    @Test
    public void shouldGetAllMembers() throws Exception {
        Long chatId = getByTitle(getChats(), "GetMembershipQueryIntegrationTest#shouldReturnAllMembers").getChatId();
        Long marker = null;
        List<ChatMember> result = new ArrayList<>();
        do {
            ChatMembersList membersList = new GetMembersQuery(client, chatId).count(1).marker(marker).execute();

            result.addAll(membersList.getMembers().stream().filter(cm -> cm.getName().toLowerCase().contains("bot"))
                    .collect(Collectors.toList()));

            marker = membersList.getMarker();
        } while (marker != null);

        assertThat(result.stream().map(ChatMember::getUserId).collect(Collectors.toList()),
                is(Stream.of(me.getUserId(), bot2.getUserId(), bot3.getUserId())
                        .sorted(Comparator.reverseOrder()).collect(Collectors.toList())));
    }


    @Test(expected = ChatAccessForbiddenException.class)
    public void shouldThrowExceptionNotChannelAdmin() throws Exception {
        List<Chat> chats = getChats();
        Chat chat = getByTitle(chats, "test channel #2");
        HashSet<Long> ids = new HashSet<>(Arrays.asList(me.getUserId(), bot2.getUserId()));
        new GetMembersQuery(client, chat.getChatId()).userIds(ids).execute();
    }

    @Test(expected = ChatAccessForbiddenException.class)
    public void shouldThrowExceptionIfNotChatMember() throws Exception {
        List<Chat> chats = getChats();
        Chat chat = getByTitle(chats, "test chat #5");
        HashSet<Long> ids = new HashSet<>(Arrays.asList(me.getUserId(), bot2.getUserId()));
        new GetMembersQuery(client2, chat.getChatId()).userIds(ids).execute();
    }

    @Test(expected = ChatAccessForbiddenException.class)
    public void shouldThrowExceptionIfNotChannelMember() throws Exception {
        List<Chat> chats = getChats();
        Chat chat = getByTitle(chats, "test channel #3");
        HashSet<Long> ids = new HashSet<>(Arrays.asList(me.getUserId(), bot2.getUserId()));
        new GetMembersQuery(client2, chat.getChatId()).userIds(ids).execute();
    }
}