package com.rsxtech.demo.provider.config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableDubbo(scanBasePackages = "com.rsxtech.demo.provider.service")
@PropertySource("classpath:/dubbo/dubbo-provider.properties")
public class ProviderConfiguration {
}
