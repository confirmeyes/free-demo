package redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author WIN10 .
 * @create 2020-07-23-10:42 .
 * @description Redis 键监听.
 */

@Component
public class RedisKeyListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {

    }
}
