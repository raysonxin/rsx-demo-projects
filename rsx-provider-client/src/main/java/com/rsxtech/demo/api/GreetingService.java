package com.rsxtech.demo.api;

import com.rsxtech.common.dto.SingleResponse;
import com.rsxtech.demo.dto.clientobject.HelloResponseCO;
import com.rsxtech.demo.dto.command.HelloRequestCmd;

/**
 * 问好服务
 *
 * @author raysonxin
 * @since 2021/1/17
 */
public interface GreetingService {

    /**
     * 问好
     */
    SingleResponse<HelloResponseCO> sayHello(HelloRequestCmd helloRequestCmd);

}
