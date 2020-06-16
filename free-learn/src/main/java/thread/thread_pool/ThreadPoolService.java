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

    private static final ThreadPoolExecutor consumeExecutor;

    private final ScheduledExecutorService scheduledExecutorService;

    /**
     * 核心线程数
     */
    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 最大线程数
     */
    public static final int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 默认情况下，除核心线程外线程的空闲存活时间
     */
    public static final int KEEP_ALIVE_TIME = 60;


    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolService.class);

    ThreadPoolService() {
        /*this.consumeExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder()
                .setNameFormat("consumeExecutor").build());*/

        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder()
                .setNameFormat("ConsumeMessageScheduledThread--%d").build());

        logger.debug("Processing trade with create: {} and  {}", "consumeExecutor", "scheduledExecutorService");

    }

    static {
        logger.info("开始初始化线程池工具类，核心线程数:{},最大线程数:{},超出线程最大存活时间:{}s", CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME);
        consumeExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder()
                .setNameFormat("consumeExecutor_%d").build());
    }


    public static ThreadPoolExecutor getConsumeExecutor() {
        return consumeExecutor;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }


}
