package com.rsxtech.demo.consumer.transfer.gray;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 灰度配置获取
 *
 * @author raysonxin
 * @since 2021/1/23
 */
@Component
@Slf4j
public class TransferGray {

    @Value("${nacos.server-addr}")
    private String nacosServer;

    private static final String dataId = "com.rsxtech.demo.consumer:gray-rule.json";
    private static final String group = "DEFAULT_GROUP";

    /**
     * 配置内容
     */
    public static PercentGrayModel grayConfig;

    @PostConstruct
    public void init() throws NacosException, JsonProcessingException {
        Properties properties = new Properties();
        properties.put("serverAddr", nacosServer);

        ConfigService configService = NacosFactory.createConfigService(properties);

        // 启动后，主动拉取配置。
        String content = configService.getConfig(dataId, group, 5000);
        log.info("TransferGrayConfig get config={}", content);
        if (ObjectUtils.isEmpty(content)) {
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        grayConfig = objectMapper.readValue(content, PercentGrayModel.class);

        // 当Nacos中配置变更时，可接收新的内容。
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String s) {
                log.info("TransferGrayConfig get config={}", s);
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    grayConfig = objectMapper.readValue(content, PercentGrayModel.class);
                } catch (Exception ex) {
                    log.error("TransferGrayConfig parse config error", ex);
                }
            }
        });
    }
}
