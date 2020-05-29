package thread.thread_exercise;

/**
 * @author lpx .
 * @create 2020-02-24-16:16 .
 * @description .
 */
public class exercise_1_CAS {

    enum ReadyToRun {T1, T2}

    static volatile ReadyToRun r = ReadyToRun.T1;

    public static void main(String[] args) throws Exception {

        char[] number = "123456789".toCharArray();
        char[] letter = "ABCDEFGHI".toCharArray();


        new Thread(() -> {

            for (char num : number) {
                while (r != ReadyToRun.T1) {
                }
                System.out.print(num);
                r = ReadyToRun.T2;
            }

        }, "t1").start();


        new Thread(() -> {

            for (char let : letter) {
                while (r != ReadyToRun.T2) {
                }
                System.out.print(let);
                r = ReadyToRun.T1;
            }

        }, "t2").start();

    }
}
