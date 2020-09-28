package redis;

import redis.clients.jedis.Jedis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author lpx .
 * @create 2019-11-26-10:57 .
 * @description redis实现分布式锁.
 */

public class RedisLockUtils {

    private static String lockKey = "xxx:lockKey";
    private static String id = UUID.randomUUID().toString();
    /**
     * 上下文，保存持有锁id
     */
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    /**
     * 可重入线程
     */
    private static Thread overThread;


    public static Boolean lock() {
        if (!tryGetLock()) {
            try {
                Thread.sleep(100);
                return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    private static Jedis init() {
        return new Jedis("192.168.1.67", 6379);
    }

    /**
     * 获取锁
     *
     * @return boolean
     */
    private static boolean tryGetLock() {

        Jedis jedis = init();
        Thread currentThread = Thread.currentThread();
        //只有当锁不存在，进行set
        //nx--key不存在，xx--key存在， ex--秒， px--毫秒
        long expireTime = 5000;
        /*if ("OK".equals(jedis.set(lockKey, id, "nx", "px", expireTime))) {
            System.err.println("---------加锁--------");
            threadLocal.set(id);
            setOwnerThread(currentThread);
            return true;
        }*/
        if (overThread == currentThread) {
            //分布式锁可重入性
            return true;
        } else {
            return false;
        }
    }


    private static void setOwnerThread(Thread t) {
        //保存当前线程
        overThread = t;
    }


    /**
     * 释放锁
     *
     * @return boolean
     */
    public static void unLock() {
        String script;
        Jedis jedis = null;
        try {
            jedis = init();
            // script = inputStream2String(getClass().getResourceAsStream("/Redis.Lua"));
            if (threadLocal.get() == null) {
                return;
            }
            //删除键
            //jedis.eval(script, Arrays.asList(lockKey), Arrays.asList(threadLocal.get()));
            if (jedis.exists(lockKey)) {
                jedis.del(lockKey);
                threadLocal.remove();
                System.out.println("---------放锁--------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    private String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }


}
