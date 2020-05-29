package thread.thread_exercise;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lpx .
 * @create 2020-02-24-16:16 .
 * @description .
 */
public class exercise_1_AtomicInteger {

    private static AtomicInteger lock = new AtomicInteger(1);

    public static void main(String[] args) throws Exception {

        char[] number = "123456789".toCharArray();
        char[] letter = "ABCDEFGHI".toCharArray();

        new Thread(() -> {

            for (char num : number) {
                while (lock.get() != 1) {
                }
                System.out.print(num);
                lock.set(0);
            }

        }, "t1").start();


        new Thread(() -> {

            for (char let : letter) {
                while (lock.get() != 0) {
                }
                System.out.print(let);
                lock.set(1);
            }

        }, "t2").start();

    }
}
