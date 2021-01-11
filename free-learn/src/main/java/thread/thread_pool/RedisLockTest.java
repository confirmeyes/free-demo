package thread.thread_pool;

import org.apache.curator.shaded.com.google.common.util.concurrent.ThreadFactoryBuilder;
import redis.RedisLockUtils;

import java.util.concurrent.*;

/**
 * @author lpx .
 * @create 2019-11-26-14:58 .
 * @description .
 */
public class RedisLockTest {

    private static Integer ticketNum = 300;

    private void reduce(int num) {
        while (RedisLockUtils.lock()) {
            if (ticketNum >= 0) {
                if (ticketNum == 0) {
                    System.err.print(System.currentTimeMillis() + "票已售罄！！！");
                }
                ticketNum -= num;
                if (ticketNum > 0) {
                    System.out.println(Thread.currentThread().getId() + "-----成功卖出"
                            + num + "张，剩余" + ticketNum + "张票");
                }
            }
            RedisLockUtils.unLock();
        }

    }


    /**
     * 线程池维护着多个线程，等待着监督管理者分配可并发执行的任务。这避免了在处理短时间任务时创建与销毁线程的代价。
     * 线程池不仅能够保证内核的充分利用，还能防止过分调度。
     * 可用线程数量应该取决于可用的并发处理器、处理器内核、内存、网络sockets等的数量。
     */

    public static void main(String[] args) {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("redisTest-pool-%d").build();

        ExecutorService ThreadPool = new ThreadPoolExecutor(4, 4,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        RedisLockTest redisLockTest = new RedisLockTest();

        for (int i = 0; i < 400; i++) {
            ThreadPool.execute(() -> {
                System.out.println(Thread.currentThread().getId());
                redisLockTest.reduce(1);
            });
        }

        for (int i = 0; i < 400; i++) {
            ThreadPool.execute(() -> {
                System.out.println(Thread.currentThread().getId());
                redisLockTest.reduce(1);
            });
        }


        ThreadPool.shutdown();

    }
}
