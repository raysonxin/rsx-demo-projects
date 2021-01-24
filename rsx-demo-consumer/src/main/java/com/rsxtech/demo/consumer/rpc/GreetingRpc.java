package com.rsxtech.demo.consumer.rpc;

import com.rsxtech.common.dto.SingleResponse;
import com.rsxtech.demo.api.GreetingService;
import com.rsxtech.demo.dto.clientobject.HelloResponseCO;
import com.rsxtech.demo.dto.command.HelloRequestCmd;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * consumer dubbo service
 *
 * @author raysonxin
 * @since 2021/1/17
 */
@Component
@Slf4j
public class GreetingRpc {

    @DubboReference(version = "1.0.0",check = false,interfaceClass =GreetingService.class )
    private GreetingService greetingService;
}
