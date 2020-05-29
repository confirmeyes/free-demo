package thread.thread_exercise;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lpx .
 * @create 2020-02-24-16:16 .
 * @description .
 */
public class exercise_1_LockSupport {

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) throws Exception {

        char[] number = "123456789".toCharArray();
        char[] letter = "ABCDEFGHI".toCharArray();

        t1 = new Thread(() -> {

            for (char num : number) {
                System.out.print(num);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "t1");

        t2 = new Thread(() -> {

            for (char let : letter) {
                LockSupport.park();
                System.out.print(let);
                LockSupport.unpark(t1);
            }
        }, "t2");


        t1.start();
        t2.start();

    }
}
