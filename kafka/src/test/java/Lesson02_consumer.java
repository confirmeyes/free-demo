import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author WIN10 .
 * @create 2021-03-24-17:47 .
 * @description .
 */
public class Lesson02_consumer {


    @Test
    public void consumer() {
        /*
        kafka-consumer-groups.sh --bootstrap-server node02:9092  --list
         */

        //基础配置
        Properties p = new Properties();
        p.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.111:9092,192.168.1.112:9092,192.168.1.113:9092");
        p.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        p.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //消费的细节
        //p.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "OOXX");
        //KAKFA IS MQ  IS STORAGE
        p.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");//第一次启动，米有offset
        /**
         *         "What to do when there is no initial offset in Kafka or if the current offset
         *         does not exist any more on the server
         *         (e.g. because that data has been deleted):
         *         <ul>
         *             <li>earliest: automatically reset the offset to the earliest offset
         *             <li>latest: automatically reset the offset to the latest offset</li>
         *             <li>none: throw exception to the consumer if no previous offset is found for the consumer's group</li><li>anything else: throw exception to the consumer.</li>
         *         </ul>";
         */
        p.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");//自动提交时异步提交，丢数据&&重复数据
        //一个运行的consumer ，那么自己会维护自己消费进度
        //一旦你自动提交，但是是异步的
        //1，还没到时间，挂了，没提交，重起一个consuemr，参照offset的时候，会重复消费
        //2，一个批次的数据还没写数据库成功，但是这个批次的offset背异步提交了，挂了，重起一个consuemr，参照offset的时候，会丢失消费

//        p.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"15000");//5秒
//        p.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,""); // POLL 拉取数据，弹性，按需，拉取多少？


        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(p);

        //kafka 的consumer会动态负载均衡
        consumer.subscribe(Arrays.asList("ooxx"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                System.out.println("---onPartitionsRevoked:");
                Iterator<TopicPartition> iter = partitions.iterator();
                while (iter.hasNext()) {

                    System.out.println(iter.next().partition());
                }

            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("---onPartitionsAssigned:");
                Iterator<TopicPartition> iter = partitions.iterator();

                while (iter.hasNext()) {
                    System.out.println(iter.next().partition());
                }
            }
        });
    }
}
