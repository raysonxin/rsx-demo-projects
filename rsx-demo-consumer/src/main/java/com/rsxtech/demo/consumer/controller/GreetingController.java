package com.rsxtech.demo.consumer.controller;

import com.rsxtech.common.dto.SingleResponse;
import com.rsxtech.demo.consumer.controller.dto.clientobject.HelloResponseCO;
import com.rsxtech.demo.consumer.controller.dto.command.HelloRequestCmd;
import com.rsxtech.demo.consumer.transfer.EnableTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 旧服务实现
 *
 * @author raysonxin
 * @since 2021/1/17
 */
@RestController
@RequestMapping(value = "/greeting")
@Slf4j
@EnableTransfer
public class GreetingController {

    @RequestMapping(value = "hello", method = RequestMethod.POST)
    public SingleResponse<HelloResponseCO> sayHello(@RequestBody HelloRequestCmd helloRequestCmd) {
        log.info("sayHello params={}", helloRequestCmd);
        HelloResponseCO responseCO = new HelloResponseCO();
        responseCO.setName("abcd");
        responseCO.setGreeting("Hello," + helloRequestCmd.getName() + ". --from old service.");
        return SingleResponse.of(responseCO);
    }
}
