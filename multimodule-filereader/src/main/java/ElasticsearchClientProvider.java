import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Elasticsearch client provider contains common logic of creating and configuring ES client and common retry logic.
 *
 * @author Mikhail_Kavaliou
 */
public class ElasticsearchClientProvider {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchClientProvider.class);

    private static final int NO_NODE_EXCEPTION_THRESHOLD = 5;
    private static final int LAST_NO_NODE_EXCEPTION_MINUTES_THRESHOLD = 5;

    private final ReentrantLock elasticClientInitLock = new ReentrantLock();

    private DateTime lastNoNodeAvailableException;
    private int noNodeAvailableExceptionCount = 0;

    /**
     * Client initialization.
     * Specifying the directConnectionNodeType (nullable) enables 2-step connection initialization.
     * The initialization occurs in the following way:
     * 1. connect to the cluster to get a list of all available nodes.
     * 2. use the received nodes of type defined in the ElasticsearchConnectionConfig
     * (MASTER or COORDINATOR (default)) for creating ES client
     */
    public TransportClient init(String cluster, String baseUrl, int port) {
        TransportClient client = null;
        if (elasticClientInitLock.tryLock()) {
            try {
                Settings settings = Settings.builder()
                    .put("cluster.name", cluster)
                    .put("client.transport.sniff", true)
                    .build();

                client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(baseUrl), port));

            } catch (UnknownHostException e) {
                LOG.error(String.format("Failed to connect to elasticsearch with base url '%s', " +
                    "and elastic cluster '%s'", baseUrl, cluster));
                throw new RuntimeException(e);
            } finally {
                elasticClientInitLock.unlock();
            }
        }
        return client;
    }

    /**
     * Thread-safe ES client re-initialization based on {@link ElasticsearchClientProvider #init()}.
     * Will call the init() method if initialization doesn't occurs at the moment.
     * Otherwise, will wait once launched re-initialization attempt was completed without calling init().
     * Also won't allow init() calls if the difference between attempts is less than
     * LAST_NO_NODE_EXCEPTION_MINUTES_THRESHOLD or the number of attempts is more then NO_NODE_EXCEPTION_THRESHOLD over
     * the last LAST_NO_NODE_EXCEPTION_MINUTES_THRESHOLD.
     * The noNodeAvailableExceptionCount counter increases in case of init() failure.
     * <p>
     * The re-initialization caused by an IllegalStateException is also possible here.
     * The IllegalStateException is thrown by the ES transport client in case of its unexpected closing.
     *
     * @param nnae The root cause Exception thrown as a result of failed ES query execution.
     * @return
     */
    public TransportClient reinitClient(String cluster, String baseUrl, int port, RuntimeException nnae) {
        TransportClient client = null;
        try {
            if (!elasticClientInitLock.isLocked() && elasticClientInitLock.tryLock()) {
                // Log message for appropriate debugging.
                LOG.warn("ReInit ES connection attempt #" + noNodeAvailableExceptionCount, nnae);

                // TODO: simplify 2 if-conditions
                if (lastNoNodeAvailableException != null && lastNoNodeAvailableException
                    .plusMinutes(LAST_NO_NODE_EXCEPTION_MINUTES_THRESHOLD).isBeforeNow()) {
                    noNodeAvailableExceptionCount = 0;
                }

                if ((lastNoNodeAvailableException == null ||
                    // if last no node exception was earlier than threshold
                    lastNoNodeAvailableException.plusMinutes(LAST_NO_NODE_EXCEPTION_MINUTES_THRESHOLD).isBeforeNow())
                    // and if NoNodeAvailableException count is less than threshold
                    && noNodeAvailableExceptionCount++ < NO_NODE_EXCEPTION_THRESHOLD) {
                    client = init(cluster, baseUrl, port);
                    lastNoNodeAvailableException = DateTime.now();
                    LOG.error("ES transport client error.", nnae);
                    return client;
                } else {
                    LOG.warn("The ReInit attempt # " + noNodeAvailableExceptionCount
                        + " was executed less then " + LAST_NO_NODE_EXCEPTION_MINUTES_THRESHOLD
                        + " minutes ago or the number of permitted attempts was exceeded for the threshold interval.");
                }

                throw nnae;
            } else {
                LOG.info("Waiting untill ReInit ES connection attempt #" + noNodeAvailableExceptionCount
                    + " will be finished.");
                // if the lock is not available then the current thread will wait till the reinit completed.
                elasticClientInitLock.lock();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (elasticClientInitLock.isHeldByCurrentThread()) {
                elasticClientInitLock.unlock();
            }
        }
        return client;
    }

}