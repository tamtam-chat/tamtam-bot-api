package chat.tamtam.botapi;

import java.lang.invoke.MethodHandles;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.BotInfo;
import chat.tamtam.botapi.model.Update;
import chat.tamtam.botapi.model.UpdateList;

import static chat.tamtam.botapi.TamTamIntegrationTest.info;

/**
 * @author alexandrchuprin
 */
public class TestBot {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final Update POISON_PILL = new Update(System.currentTimeMillis());

    protected final TamTamBotAPI api;
    private final Thread poller;
    private final Thread consumerThread;
    private final BotInfo me;
    private final String hashedName;

    private final AtomicBoolean isStopped = new AtomicBoolean();
    private final Set<Update.Visitor> consumers = ConcurrentHashMap.newKeySet();
    private final BlockingQueue<Update> updates = new ArrayBlockingQueue<>(100);
    private final AtomicReference<Long> marker = new AtomicReference<>();

    TestBot(TamTamClient botClient, boolean isTravis) throws APIException, ClientException {
        this.api = new TamTamBotAPI(botClient);
        this.me = api.getMyInfo().execute();
        this.hashedName = isTravis ? TamTamIntegrationTest.randomText(16) : me.getName();
        this.poller = new Thread(this::poll, "updates-poller-" + hashedName);
        this.consumerThread = new Thread(this::consumeUpdates, "updates-consumer-" + hashedName);
    }

    public Long getUserId() {
        return getBotInfo().getUserId();
    }

    public String getAvatarUrl() {
        return getBotInfo().getAvatarUrl();
    }

    public String getUsername() {
        return getBotInfo().getUsername();
    }

    public String getName() {
        return getBotInfo().getName();
    }

    public BotInfo getBotInfo() {
        return me;
    }

    public void start() {
        flush();
        poller.start();
        consumerThread.start();
        LOG.info("Bot " + hashedName + " started");
    }

    public void stop() {
        LOG.info("Stopping bot {}", hashedName);
        isStopped.set(true);
        try {
            poller.interrupt();
            poller.join();
            updates.offer(POISON_PILL);
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        LOG.info("Bot " + hashedName + " stopped");
    }

    public void addConsumer(Update.Visitor consumer) {
        consumers.add(consumer);
    }

    public void removeConsumer(Update.Visitor consumer) {
        consumers.remove(consumer);
    }

    private void flush() {
        marker.set(pollOnce(null));
    }

    private void consumeUpdates() {
        while (true) {
            Update update;
            try {
                update = updates.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (update == POISON_PILL) {
                return;
            }

            for (Update.Visitor consumer : consumers) {
                update.visit(consumer);
            }
        }
    }

    private void poll() {
        do {
            marker.set(pollOnce(marker.get()));
        } while (!Thread.currentThread().isInterrupted() && !isStopped.get());
    }

    private Long pollOnce(Long marker) {
        int error = 0;
        try {
            UpdateList updateList = api.getUpdates().marker(marker).timeout(5).execute();
            for (Update update : updateList.getUpdates()) {
                updates.offer(update);
                info("Bot " + hashedName + " got update: {}", update);
            }
            error = 0;
            return updateList.getMarker();
        } catch (APIException | ClientException e) {
            if (e.getCause() instanceof InterruptedException) {
                return marker;
            }

            error++;
            LOG.error(e.getMessage(), e);
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(Math.min(error, 5)));
            } catch (InterruptedException e1) {
                Thread.currentThread().interrupt();
            }
        }

        return marker;
    }
}
