package redis;

import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * @author lpx .
 * @create 2019-11-25-18:05 .
 * @description RedisTemplate操作类.
 */

public class RedisUtils {

    private RedisTemplate redisTemplate;

    private static int expiration = 2;

    public void saveCode(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
        redisTemplate.expire(key, expiration, TimeUnit.MINUTES);
    }
}
