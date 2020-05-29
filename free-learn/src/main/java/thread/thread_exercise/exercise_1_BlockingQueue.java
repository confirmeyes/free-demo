package thread.thread_exercise;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author lpx .
 * @create 2020-02-24-16:16 .
 * @description .
 */
public class exercise_1_BlockingQueue {

    static BlockingQueue<String> queue1 = new ArrayBlockingQueue<String>(1);
    static BlockingQueue<String> queue2 = new ArrayBlockingQueue<String>(1);

    public static void main(String[] args) throws Exception {

        char[] number = "123456789".toCharArray();
        char[] letter = "ABCDEFGHI".toCharArray();


        new Thread(() -> {

            for (char num : number) {
                System.out.print(num);
                try {
                    queue1.put("ok");
                    queue2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "t1").start();


        new Thread(() -> {

            for (char let : letter) {
                try {
                    queue2.put("ok");
                    System.out.print(let);
                    queue1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }, "t2").start();

    }
}
