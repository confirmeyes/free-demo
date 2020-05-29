package zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author lpx .
 * @create 2020-01-02-18:07 .
 * @description zookeeper 实现分布式锁.
 */
public class zKLockTest {


    public static void main(String[] args) {
        CuratorFramework zkClient = getZkClient();

        ExecutorService executorService = Executors.newCachedThreadPool();
        InterProcessMutex zkLock = new InterProcessMutex(zkClient, "/zkLock");
        Consumer<InterProcessMutex> consumer = (InterProcessMutex typeLock) -> {
            try {
                List<Callable<String>> callableList = new ArrayList<>();
                Callable<String> callable = () -> {
                    try {
                        typeLock.acquire();
                        //业务
                        System.err.println("{" + Thread.currentThread() + "----------- 获取锁 -------}");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        typeLock.release();
                        System.err.println("{" + Thread.currentThread() + "----------- 释放锁 -------}");
                    }
                    return "true";
                };
                for (int x = 0; x < 10; x++) {
                    callableList.add(callable);
                    Thread.sleep(3000);
                }
                executorService.invokeAll(callableList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.shutdown();

        };
        consumer.accept(zkLock);
        executorService.shutdown();
        zkClient.close();
    }


    private static CuratorFramework getZkClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(6000, 2);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString("192.168.88.135")
                .sessionTimeoutMs(9000)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        return zkClient;
    }
}
