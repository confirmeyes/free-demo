package com.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author WIN10 .
 * @create 2020-08-18-11:19 .
 * @description .
 */

@EnableScheduling
@EnableJms
@SpringBootApplication
public class SoftPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoftPayApplication.class, args);
    }
}
