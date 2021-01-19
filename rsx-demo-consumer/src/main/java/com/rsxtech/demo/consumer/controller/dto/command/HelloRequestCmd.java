package com.rsxtech.demo.consumer.controller.dto.command;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * hello
 *
 * @author raysonxin
 * @since 2021/1/17
 */
@Data
@ToString
public class HelloRequestCmd implements Serializable {

    private static final long serialVersionID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 问候语
     */
    private String greeting;
}
