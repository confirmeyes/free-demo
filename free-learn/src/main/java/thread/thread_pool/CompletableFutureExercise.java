package thread.thread_pool;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author lpx .
 * @create 2020-04-14-13:11 .
 * @description .
 */
public class CompletableFutureExercise {

    public static void main(String[] args) {

        /**
         * 没有指定Executor的方法会使用ForkJoinPool.commonPool() 作为它的线程池执行异步代码。
         * 如果指定线程池，则使用指定的线程池运行。以下所有的方法都类同。
         *
         *  runAsync方法不支持返回值。
         *  supplyAsync可以支持返回值。
         */


        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(() -> priceOfTB());
        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(() -> priceOfTM());

        CompletableFuture<Double> futureZFB = CompletableFuture.supplyAsync(new Supplier<Double>() {
            @Override
            public Double get() {
                return priceOfZFB();
            }
        });

        CompletableFuture.allOf(futureTB, futureTM, futureZFB).join();


        /**
         * thenCombine 会把 两个 CompletionStage 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理。
         */
        CompletableFuture.supplyAsync(() ->
                priceOfTB()
        ).thenCombine(futureTB, new BiFunction<Double, Double, Object>() {
            @Override
            public Object apply(Double aDouble, Double aDouble2) {
                return aDouble + aDouble2;
            }
        }).thenAccept(System.out::println)
                .thenRun(() -> {
                    System.out.println("------ thenRun -----");
                });


        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       /* try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        /**
         * 当一个线程依赖另一个线程时，可以使用 thenApply 方法来把这两个线程串行化。
         * thenAccept 接收任务处理结果，并消费
         * thenRun 上个任务处理完成后，并不会把计算的结果传给 thenRun 方法。
         * 只是处理玩任务后，执行 thenAccept 的后续操作。
         */
        CompletableFuture.supplyAsync(() ->
                priceOfTB()

        ).thenApply(String::valueOf)
                .thenApply(str -> "price: " + str)
                .thenAccept(System.out::println);


    }


    private static double priceOfTM() {
        delay();
        return 1.00;
    }


    private static double priceOfTB() {
        delay();
        return 2.00;
    }

    private static double priceOfZFB() {
        delay();
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
