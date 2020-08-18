package com.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author WIN10 .
 * @create 2020-08-18-11:04 .
 * @description .
 */

@EnableJms
@EnableScheduling
@SpringBootApplication
public class SoftOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftOrderApplication.class, args);
    }
}
