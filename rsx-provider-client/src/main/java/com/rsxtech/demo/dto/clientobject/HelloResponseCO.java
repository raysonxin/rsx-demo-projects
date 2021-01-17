package com.rsxtech.demo.dto.clientobject;

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

    private String name;

    private String greeting;

}
