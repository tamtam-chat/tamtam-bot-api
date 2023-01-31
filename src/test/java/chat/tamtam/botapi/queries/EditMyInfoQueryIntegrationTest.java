package chat.tamtam.botapi.queries;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.BotCommand;
import chat.tamtam.botapi.model.BotInfo;
import chat.tamtam.botapi.model.BotPatch;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author alexandrchuprin
 */
public class EditMyInfoQueryIntegrationTest extends TamTamIntegrationTest {
    private final AtomicReference<BotInfo> originalMe = new AtomicReference<>();
    private String newName;
    private String newDescription;
    private ArrayList<BotCommand> commands;

    @Before
    public void setUp() {
        originalMe.compareAndSet(null, bot1.getBotInfo());
        newName = "TT Integration Test Bot " + now();
        newDescription = randomText(64);
        commands = new ArrayList<>();
        commands.add(new BotCommand("command" + now()));
        commands.add(new BotCommand("command" + now() + 1).description("description" + now()));
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
            BotPatch patch = new BotPatch().photo(createAvatar());
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
                    .photo(createAvatar());

            BotInfo botInfo = new EditMyInfoQuery(client, patch).execute();
            BotInfo updatedMe = getBot1();
            assertUser(botInfo, updatedMe);
            assertThat(updatedMe.getName(), is(newName));
            assertThat(updatedMe.getDescription(), is(newDescription));
            assertThat(updatedMe.getAvatarUrl(), is(not(bot1.getAvatarUrl())));
            assertThat(updatedMe.getCommands(), is(commands));
        });
    }

    private static byte[] generateAvatarImage() throws IOException {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int width = 192;
        int height = 192;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int a = rand.nextInt(0, 256);
                int r = rand.nextInt(0, 256);
                int g = rand.nextInt(0, 256);
                int b = rand.nextInt(0, 256);
                int p = (a << 24) | (r << 16) | (g << 8) | b;
                img.setRGB(x, y, p);
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);

        return out.toByteArray();
    }

    private PhotoAttachmentRequestPayload createAvatar() throws Exception {
        InputStream imageStream = new ByteArrayInputStream(generateAvatarImage());
        return getPhotoAttachmentRequest(imageStream).getPayload();
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
                .commands(originalMe.get().getCommands());

        new EditMyInfoQuery(client, patch).execute();
    }

    @FunctionalInterface
    private interface EditAction {
        void doEdit() throws Exception;
    }
}