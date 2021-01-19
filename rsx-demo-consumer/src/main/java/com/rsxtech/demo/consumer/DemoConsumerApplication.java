package com.rsxtech.demo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * application
 *
 * @author raysonxin
 * @since 2021/1/17
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class DemoConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }
}
