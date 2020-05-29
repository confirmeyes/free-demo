package thread.thread_exercise;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lpx .
 * @create 2020-02-24-16:16 .
 * @description .
 */
public class exercise_1_ConditionPlus {


    public static void main(String[] args) throws Exception {

        char[] number = "123456789".toCharArray();
        char[] letter = "ABCDEFGHI".toCharArray();
        //重入锁
        Lock lock = new ReentrantLock();
        Condition conditionT1 = lock.newCondition();
        Condition conditionT2 = lock.newCondition();

        new Thread(() -> {

            lock.lock();

            for (char num : number) {
                //加锁
                System.out.print(num);
                conditionT2.signal();
                try {
                    conditionT1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();

        }, "t1").start();


        new Thread(() -> {
            //加锁
            lock.lock();

            for (char let : letter) {
                System.out.print(let);
                conditionT1.signal();
                try {
                    conditionT2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();

        }, "t2").start();

    }
}
