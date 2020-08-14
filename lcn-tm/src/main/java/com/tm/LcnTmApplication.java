package com.tm;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author WIN10 .
 * @create 2020-08-13-10:17 .
 * @description .
 */


@SpringBootApplication
@EnableTransactionManagerServer
public class LcnTmApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnTmApplication.class, args);
    }
}
