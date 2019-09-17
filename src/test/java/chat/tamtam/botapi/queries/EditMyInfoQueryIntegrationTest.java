package chat.tamtam.botapi.queries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Before;
import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.BotCommand;
import chat.tamtam.botapi.model.BotInfo;
import chat.tamtam.botapi.model.BotPatch;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class EditMyInfoQueryIntegrationTest extends TamTamIntegrationTest {
    private String newName;
    private String newUsername;
    private String newDescription;
    private ArrayList<BotCommand> commands;
    private PhotoAttachmentRequestPayload photo;
    private AtomicReference<BotInfo> originalMe = new AtomicReference<>();

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        originalMe.compareAndSet(null, bot1.getBotInfo());
        newName = "TT Integration Test Bot " + now();
        newUsername = randomText(16);
        newDescription = randomText(64);
        commands = new ArrayList<>();
        commands.add(new BotCommand("command" + now()));
        commands.add(new BotCommand("command" + now() + 1).description("description" + now()));
        photo = getPhotoAttachmentRequest().getPayload();
    }

    @Test
    public void shouldEditName() throws Exception {
        doEdit(() -> {
            BotPatch patch = new BotPatch().name(newName);
            BotInfo botInfo = new EditMyInfoQuery(client, patch).execute();
            assertThat(botInfo.getName(), is(newName));
            assertThat(getBot1().getName(), is(newName));
        });
    }

    @Test
    public void shouldEditUsername() throws Exception {
        doEdit(() -> {
            BotPatch patch = new BotPatch().username(newUsername);
            BotInfo botInfo = new EditMyInfoQuery(client, patch).execute();
            assertThat(botInfo.getUsername(), is(newUsername));
            assertThat(getBot1().getUsername(), is(newUsername));
        });
    }

    @Test
    public void shouldEditDescription() throws Exception {
        doEdit(() -> {
            BotPatch patch = new BotPatch().description(newDescription);
            BotInfo botInfo = new EditMyInfoQuery(client, patch).execute();
            assertThat(botInfo.getDescription(), is(newDescription));
            assertThat(getBot1().getDescription(), is(newDescription));
        });
    }

    @Test
    public void shouldEditCommands() throws Exception {
        doEdit(() -> {
            BotPatch patch = new BotPatch().commands(commands);
            BotInfo botInfo = new EditMyInfoQuery(client, patch).execute();
            assertThat(botInfo.getCommands(), is(commands));
            assertThat(getBot1().getCommands(), is(commands));
        });
    }

    @Test
    public void shouldRemoveCommands() throws Exception {
        doEdit(() -> {
            BotPatch patch = new BotPatch().commands(Collections.emptyList());
            BotInfo botInfo = new EditMyInfoQuery(client, patch).execute();
            assertThat(botInfo.getCommands(), is(nullValue()));
            assertThat(getBot1().getCommands(), is(nullValue()));
        });
    }

    @Test
    public void shouldEditPhoto() throws Exception {
        doEdit(() -> {
            BotPatch patch = new BotPatch().photo(photo);
            BotInfo botInfo = new EditMyInfoQuery(client, patch).execute();
            assertThat(botInfo.getAvatarUrl(), is(not(bot1.getAvatarUrl())));
            assertThat(getBot1().getAvatarUrl(), is(not(bot1.getAvatarUrl())));
        });
    }

    @Test
    public void shouldEditAll() throws Exception {
        doEdit(() -> {
            BotPatch patch = new BotPatch()
                    .name(newName)
                    .description(newDescription)
                    .commands(commands)
                    .photo(photo)
                    .username(newUsername);

            BotInfo botInfo = new EditMyInfoQuery(client, patch).execute();
            BotInfo updatedMe = getBot1();
            assertThat(botInfo, is(updatedMe));
            assertThat(updatedMe.getName(), is(newName));
            assertThat(updatedMe.getUsername(), is(newUsername));
            assertThat(updatedMe.getDescription(), is(newDescription));
            assertThat(updatedMe.getAvatarUrl(), is(not(bot1.getAvatarUrl())));
            assertThat(updatedMe.getCommands(), is(commands));
        });
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionOnNotAvailableUsername() throws Exception {
        BotPatch patch = new BotPatch().username(bot2.getUsername());
        new EditMyInfoQuery(client, patch).execute();
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionOnShortUsername() throws Exception {
        BotPatch patch = new BotPatch().username("xxx");
        new EditMyInfoQuery(client, patch).execute();
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionOnInvalidSymbols() throws Exception {
        BotPatch patch = new BotPatch().username("x*x*x*x");
        new EditMyInfoQuery(client, patch).execute();
    }

    @Test(expected = APIException.class)
    public void usernameShoulNotStartWithNonLetter() throws Exception {
        BotPatch patch = new BotPatch().username("1xxxxx");
        new EditMyInfoQuery(client, patch).execute();
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionWhenUsernameIsTooLong() throws Exception {
        BotPatch patch = new BotPatch().username(randomText(65));
        new EditMyInfoQuery(client, patch).execute();
    }

    private void doEdit(EditAction action) throws Exception {
        try {
            action.doEdit();
        } finally {
            rollback();
        }
    }

    private void rollback() throws Exception {
        BotPatch patch = new BotPatch()
                .name(originalMe.get().getName())
                .description(originalMe.get().getDescription())
                .commands(originalMe.get().getCommands())
                .username(originalMe.get().getUsername());

        new EditMyInfoQuery(client, patch).execute();
    }

    @FunctionalInterface
    private interface EditAction {
        void doEdit() throws Exception;
    }
}