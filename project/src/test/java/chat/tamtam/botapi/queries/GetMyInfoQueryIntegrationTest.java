package chat.tamtam.botapi.queries;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.BotCommand;
import chat.tamtam.botapi.model.BotInfo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author alexandrchuprin
 */
public class GetMyInfoQueryIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldGetMyInfo() throws Exception {
        BotInfo botInfo = new GetMyInfoQuery(client2).execute();
        List<BotCommand> expectedCommands = Arrays.asList(
                new BotCommand("cmd1").description("desc1"),
                new BotCommand("cmd2")
        );

        assertThat(botInfo.getCommands(), is(expectedCommands));
        assertThat(botInfo.getDescription(), is("test bot 2 description"));
    }
}