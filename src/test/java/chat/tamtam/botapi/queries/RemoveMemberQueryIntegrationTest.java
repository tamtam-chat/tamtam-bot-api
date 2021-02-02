package chat.tamtam.botapi.queries;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatMember;
import chat.tamtam.botapi.model.SimpleQueryResult;
import chat.tamtam.botapi.model.UserIdsList;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author alexandrchuprin
 */
public class RemoveMemberQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldRemoveMember() throws Exception {
        testRemove(getByTitle(getChats(), "RemoveMemberQueryIntegrationTest#shouldRemoveMember"));
        testRemove(getByTitle(getChats(), "RemoveMemberQueryIntegrationTest#shouldRemoveMemberInPrivateChat"));
        testRemove(getByTitle(getChats(), "RemoveMemberQueryIntegrationTest#shouldRemoveMemberInPublicChat"));
        testRemove(getByTitle(getChats(), "RemoveMemberQueryIntegrationTest#shouldRemoveMemberInChannel"));
    }

    @Test
    public void shouldRemoveAndBlockMember() throws Exception {
        testRemoveAndBlock(getByTitle(getChats(), "RemoveMemberQueryIntegrationTest#shouldRemoveAndBlockMemberInPrivateChat"));
        testRemoveAndBlock(getByTitle(getChats(), "RemoveMemberQueryIntegrationTest#shouldRemoveAndBlockMemberInPublicChat"));
    }

    private void testRemove(Chat chat) throws Exception {
        testRemove(chat, false);
    }

    private void testRemoveAndBlock(Chat chat) throws Exception {
        testRemove(chat, true);
    }

    private void testRemove(Chat chat, boolean block) throws Exception {
        info("Chat: " + chat.getTitle());
        Long chatId = chat.getChatId();
        Long anotherBotId = bot3.getUserId();
        assertMembership(chatId, hasItem(anotherBotId));

        try {
            SimpleQueryResult result = new RemoveMemberQuery(client, chatId, anotherBotId).block(block).execute();
            assertThat(result.getMessage(), result.isSuccess(), is(true));

            assertMembership(chatId, not(hasItem(anotherBotId)));

            if (block) {
                // test if blocked user cannot return to chat
                bot3.joinChat(chat.getLink());
                Thread.sleep(3000);
                assertMembership(chatId, not(hasItem(anotherBotId)));
            }
        } finally {
            new AddMembersQuery(client, new UserIdsList(Collections.singletonList(anotherBotId)), chatId).execute();
        }
    }

    private void assertMembership(long chatId, Matcher<Iterable<? super Long>> matcher) throws Exception {
        List<ChatMember> members = new GetMembersQuery(client, chatId).execute().getMembers();
        assertThat(members.stream().map(ChatMember::getUserId).collect(Collectors.toSet()), matcher);
    }
}
