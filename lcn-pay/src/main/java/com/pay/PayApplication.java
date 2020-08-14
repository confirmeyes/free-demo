package com.pay;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author WIN10 .
 * @create 2020-08-13-16:29 .
 * @description .
 */

@SpringBootApplication
@EnableDistributedTransaction
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
