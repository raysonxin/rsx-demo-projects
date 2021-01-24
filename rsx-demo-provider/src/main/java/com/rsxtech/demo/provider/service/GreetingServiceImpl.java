package com.rsxtech.demo.provider.service;

import com.rsxtech.common.dto.SingleResponse;
import com.rsxtech.demo.api.GreetingService;
import com.rsxtech.demo.dto.clientobject.HelloResponseCO;
import com.rsxtech.demo.dto.command.HelloRequestCmd;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * Greeting service provider
 *
 * @author raysonxin
 * @since 2021/1/17
 */
@DubboService(version = "1.0.0")
@Slf4j
public class GreetingServiceImpl implements GreetingService {
    @Override
    public SingleResponse<HelloResponseCO> sayHello(HelloRequestCmd helloRequestCmd) {

        log.info("sayHello request:{}", helloRequestCmd);
        HelloResponseCO responseCO = new HelloResponseCO();
        responseCO.setName("abcd");
        responseCO.setGreeting("Good morning! --from new service");
        return SingleResponse.of(responseCO);
    }
}
