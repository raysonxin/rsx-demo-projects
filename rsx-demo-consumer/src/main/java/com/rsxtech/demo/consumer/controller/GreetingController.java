package com.rsxtech.demo.consumer.controller;

import com.rsxtech.demo.consumer.rpc.GreetingRpc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author raysonxin
 * @since 2021/1/17
 */
@RestController
@RequestMapping(value = "/greeting")
public class GreetingController {

    @Resource
    private GreetingRpc greetingRpc;

    @RequestMapping(value = "hello/{name}/{hometown}/{greeting}")
    public String sayHello(@PathVariable("name") String name, @PathVariable("hometown") String hometown, @PathVariable("greeting") String greeting) {
        return greetingRpc.sayHello(name, hometown, greeting);
    }
}
