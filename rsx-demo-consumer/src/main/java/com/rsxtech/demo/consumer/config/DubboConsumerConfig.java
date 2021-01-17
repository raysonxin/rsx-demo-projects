package com.rsxtech.demo.consumer.config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * dubbo consumer config
 *
 * @author raysonxin
 * @since 2021/1/10
 */
@Configuration
@EnableDubbo(scanBasePackages = {"com.rsxtech.demo.consumer.rpc"})
@PropertySource("classpath:/dubbo/dubbo-consumer.properties")
@ComponentScan(value = {"com.rsxtech.demo.consumer.rpc"})
public class DubboConsumerConfig {

}
