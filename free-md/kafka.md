

### consumer

- test 消费组中所有消费者涉及的 topic 、 partition 消费描述

```shell
bin/kafka-consumer-groups.sh --bootstrap-server 192.168.1.111:9092 --describe --group test
```

- 消费组列表

```shell
bin/kafka-consumer-groups.sh --bootstrap-server 192.168.1.111:9092 --list
```
- topic 分区，副本重分配
```shell
bin/kafka-reassign-partitions.sh --zookeeper 192.168.1.111:2181 --topics-to-move-json-file topics-to-move.json --broker-list "1,2,3" --generate
```

- topic 的分区和副本

```shell
bin/kafka-topics.sh --zookeeper 192.168.1.111:2181 --topic test --describe
```

```linux
Topic: ooxx	PartitionCount: 3	ReplicationFactor: 1	Configs: 
	Topic: ooxx	Partition: 0	Leader: 1	Replicas: 1	Isr: 1
	Topic: ooxx	Partition: 1	Leader: 2	Replicas: 2	Isr: 2
	Topic: ooxx	Partition: 2	Leader: 1	Replicas: 1	Isr: 1
```

