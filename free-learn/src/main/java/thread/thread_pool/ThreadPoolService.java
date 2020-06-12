package thread.thread_pool;

import org.apache.curator.shaded.com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author WIN10 .
 * @create 2020-04-29-17:35 .
 * @description .
 */
public class ThreadPoolService {

    private final ThreadPoolExecutor consumeExecutor;
    private final ScheduledExecutorService scheduledExecutorService;

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolService.class);

    ThreadPoolService() {
        this.consumeExecutor = new ThreadPoolExecutor(8, 8,
                60000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder()
                .setNameFormat("consumeExecutor").build());
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder()
                .setNameFormat("ConsumeMessageScheduledThread_").build());

        logger.debug("Processing trade with create: {} and  {}", "consumeExecutor", "scheduledExecutorService");

    }


    public static void main(String[] args) {

        ThreadPoolService threadPoolService = new ThreadPoolService();
        ThreadPoolExecutor threadPoolExecutor = threadPoolService.getConsumeExecutor();
        //threadPoolExecutor.invokeAll()
    }


    public ThreadPoolExecutor getConsumeExecutor() {
        return consumeExecutor;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }


}
