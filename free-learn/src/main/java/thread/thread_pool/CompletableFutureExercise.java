package thread.thread_pool;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author lpx .
 * @create 2020-04-14-13:11 .
 * @description .
 */
public class CompletableFutureExercise {

    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureExercise.class);

    @SneakyThrows
    public static void main(String[] args) {

        /*
         * 没有指定Executor的方法会使用ForkJoinPool.commonPool() 作为它的线程池执行异步代码。
         * 如果指定线程池，则使用指定的线程池运行。以下所有的方法都类同。
         *
         *  runAsync方法不支持返回值。
         *  supplyAsync可以支持返回值。
         */

        CompletableFuture<Double> futuretb = CompletableFuture.supplyAsync(CompletableFutureExercise::priceOftb);
        CompletableFuture<Double> futuretm = CompletableFuture.supplyAsync(CompletableFutureExercise::priceOftm);

        logger.info("--------------supplyAsync--------------");

        CompletableFuture<Double> futurezfb = CompletableFuture.supplyAsync(CompletableFutureExercise::priceOfzfb);


        logger.info("--------------thenCombine合并两个Future--------------");
        /*
          thenCombine 会把 两个 CompletionStage 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理。
         */
        Double price = futurezfb.thenCombine(futuretb, (zfb, tb) -> {
            logger.info("支付宝的价格: {}", zfb);
            logger.info("淘宝的价格: {}", tb);
            return zfb + tb;
        }).join();
        logger.info("合计的价格: {}", price);


        logger.info("--------------thenApply转换CompletableFuture中的泛型--------------");

        CompletableFuture<String> stringFuture = futuretm.thenApply(Object::toString);
        logger.info("stringFuture: {}", stringFuture.get());


        logger.info("--------------allOf--------------");

        CompletableFuture<Void> future = CompletableFuture.allOf(futuretb, futuretm, futurezfb);
        logger.info("合计: {}", future.get());

        logger.info("--------------thenCompose--------------");
        /*
         * 将两个线程串行连接起来，只有第一个线程返回结果时，才会将返回值作为参数传给第二个线程执行。
         */


        /*
         * thenAccept 接收任务处理结果，并消费
         * thenRun 上个任务处理完成后，并不会把计算的结果传给 thenRun 方法。
         * 只是处理玩任务后，执行 thenAccept 的后续操作。
         */
        logger.info("--------------thenAccept--------------");

        futuretb.thenAccept(System.out::println);
    }

    private static double priceOftm() {
        return 1.00;
    }


    private static double priceOftb() {
        return 2.00;
    }

    private static double priceOfzfb() {
        return 3.00;
    }

    private static void delay() {
        int time = new Random().nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("After %s sleep!\n", time);
    }
}
