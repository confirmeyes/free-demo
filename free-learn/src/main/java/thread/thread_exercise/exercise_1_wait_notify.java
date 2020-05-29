package thread.thread_exercise;


/**
 * @author lpx .
 * @create 2020-02-24-16:16 .
 * @description .
 */
public class exercise_1_wait_notify {


    private static final Object object = new Object();

    public static void main(String[] args) throws Exception {

        char[] number = "123456789".toCharArray();
        char[] letter = "ABCDEFGHI".toCharArray();

        new Thread(() -> {
            synchronized (object) {
                for (char num : number) {
                    System.out.print(num);
                    try {
                        object.notify();
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                object.notify();
            }
        }, "t1").start();


        new Thread(() -> {
            synchronized (object) {
                for (char let : letter) {
                    System.out.print(let);
                    try {
                        object.notify();
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                object.notify();
            }
        }, "t2").start();


    }
}
