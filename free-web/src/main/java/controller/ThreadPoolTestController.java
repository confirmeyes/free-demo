package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import thread.thread_pool.ThreadPoolService;
import thread.thread_pool.ThreadPoolUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WIN10 .
 * @create 2020-05-27-10:41 .
 * @description .
 */

@RestController
public class ThreadPoolTestController {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolTestController.class);

    private static AtomicInteger lock = new AtomicInteger(0);

    @GetMapping("/thread")
    public String hello() {

        ExecutorService executor = ThreadPoolService.getConsumeExecutor();

        //用于获取到本java进程，进而获取总线程数
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        String jvmName = runtimeBean.getName();
        logger.info("JVM Name = " + jvmName);
        long pid = Long.valueOf(jvmName.split("@")[0]);
        logger.info("JVM PID = " + pid);
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();


        executor.execute(() -> {
            try {
                logger.info(Thread.currentThread().getName() + " 开始执行 ");
                lock.getAndIncrement();
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
        logger.info("应用线程总数: {}", bean.getThreadCount());
        return "请求数" + lock.get();
    }
}
