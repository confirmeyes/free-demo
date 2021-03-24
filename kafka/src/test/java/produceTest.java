import com.kafka.SpringkafkaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author WIN10 .
 * @create 2021-03-24-16:27 .
 * @description .
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringkafkaApplication.class)
public class produceTest {

    @Autowired
    ProduceTest produceTest;

    @Test
    public void send (){
        produceTest.send("send msg !!!");
    }
}
