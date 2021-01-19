package com.rsxtech.demo.consumer.controller.dto.clientobject;

import com.rsxtech.common.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * response
 *
 * @author raysonxin
 * @since 2021/1/17
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class HelloResponseCO extends DTO {

    /**
     * name
     */
    private String name;

    /**
     * greeting
     */
    private String greeting;

}
