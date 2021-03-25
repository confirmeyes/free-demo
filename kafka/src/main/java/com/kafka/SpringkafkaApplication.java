package com.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

import static com.alibaba.fastjson.JSONPatch.OperationType.test;

/**
 * @author WIN10 .
 * @create 2021-03-24-17:05 .
 * @description .
 */

@SpringBootApplication
@EnableKafka
public class SpringkafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringkafkaApplication.class, args);

    }
}
