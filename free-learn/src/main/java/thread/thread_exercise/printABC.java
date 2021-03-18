package thread.thread_exercise;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WIN10 .
 * @create 2021-03-12-16:38 .
 * @description .
 */
public class printABC {

    private ReentrantLock lock = new ReentrantLock();
    private AtomicInteger sign = new AtomicInteger(0);
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();


    class ATest implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                while (sign.get() == 1 || sign.get() == 2) {
                    conditionA.await();
                }
                System.out.print("A ");
                sign.set(1);
                conditionB.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    class BTest implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                while (sign.get() == 0 || sign.get() == 2) {
                    conditionB.await();
                }
                System.out.print("B ");
                sign.set(2);
                conditionC.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    class CTest implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                while (sign.get() == 0 || sign.get() == 1) {
                    conditionC.await();
                }
                System.out.print("C ");
                sign.set(0);
                conditionA.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    @Test
    public void main() {
        new Thread(new ATest()).start();
        new Thread(new BTest()).start();
        new Thread(new CTest()).start();

    }
}
