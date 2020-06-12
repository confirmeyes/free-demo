package thread.thread_pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WIN10 .
 * @create 2020-06-12-19:12 .
 * @description .
 */
@Component
public class ThreadPoolUtil {

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

    /**
     * 最大阻塞队列
     */
    private static final int MAX_QUEUE = 200;

    private static ExecutorService executorService;

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolUtil.class);

    static {
        logger.info("开始初始化线程池工具类，核心线程数:{},最大线程数:{},超出线程最大存活时间:{}s", CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME);
        executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(MAX_QUEUE),
                new ThreadFactory() {
                    // 记录线程名称，便于日志排查问题
                    private final AtomicInteger threadNum = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = null;
                        try {
                            thread = new Thread(r);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        thread.setName("ExecutorPoolUtil-thread-" + threadNum);
                        thread.setDaemon(false);
                        thread.setPriority(Thread.NORM_PRIORITY);
                        return thread;
                    }
                }, new WaitPolicy());
        logger.info("初始化线程池结束");
    }


    public static ExecutorService getExecutorService() {
        return executorService;
    }


    private ThreadPoolUtil() {
    }


    public static void execute(Runnable runnable) {
        Assert.notNull(runnable, "线程池初始化失败，不能执行任务");
        logger.info("executorService.execute 开始执行线程");
        executorService.execute(runnable);
    }

    /**
     * 销毁线程池对象
     */
    @PreDestroy
    public void destory() {
        // 线程池拒绝接受新的任务，等待执行中的任务
        executorService.shutdown();
        while (true) {
            // 判断执行中的任务是否结束
            if (executorService.isTerminated()) {
                logger.info("所有线程任务执行结束");
                break;
            }
            // 若任务未执行结束，线程sleep
            try {
                Thread.sleep(5l);
                logger.info("线程尚未执行结束，继续等待。。。");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static boolean isFull() {
        int activeCount = ((ThreadPoolExecutor) executorService).getActiveCount();
        if (MAX_POOL_SIZE == activeCount) {
            return true;
        }
        return false;
    }


    // 自定义拒绝策略(可以选择默认等其他策略，根据业务需求定)
    private static class WaitPolicy implements RejectedExecutionHandler {
        private static final Logger logger = LoggerFactory.getLogger(WaitPolicy.class);

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            try {
                executor.getQueue().offer(r, 60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }

        }
    }

}


