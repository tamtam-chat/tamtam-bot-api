package chat.tamtam.botapi.queries;

import java.util.concurrent.CountDownLatch;

import org.junit.Ignore;
import org.junit.Test;

import chat.tamtam.botapi.model.BotStartedUpdate;
import chat.tamtam.botapi.model.FailByDefaultUpdateVisitor;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class BotStartedUpdateIntegrationTest extends GetUpdatesIntegrationTest {
    @Test
    public void shouldGetUpdate() throws Exception {
        String payload = randomText();
        CountDownLatch updateReceived = new CountDownLatch(1);
        FailByDefaultUpdateVisitor consumer = new FailByDefaultUpdateVisitor() {
            @Override
            public void visit(BotStartedUpdate model) {
                assertThat(model.getUser().getUserId(), is(bot3.getUserId()));
                updateReceived.countDown();
            }
        };

        bot1.addConsumer(consumer);
        bot3.startAnotherBot(bot1.getUserId(), payload);

        await(updateReceived);
    }

    @Ignore
    @Test
    public void shouldGetWebhookUpdate() throws Exception {
        String payload = randomText();
        CountDownLatch updateReceived = new CountDownLatch(1);
        Bot1ToBot3RedirectingUpdateVisitor consumer = new Bot1ToBot3RedirectingUpdateVisitor(
                new FailByDefaultUpdateVisitor() {
                    @Override
                    public void visit(BotStartedUpdate model) {
                        assertThat(model.getUser().getUserId(), is(bot1.getUserId()));
                        updateReceived.countDown();
                    }
                });

        bot1.addConsumer(consumer);
        bot3.startYourself(bot1.getUserId(), payload);
        await(updateReceived);
    }
}
