package com.springcloud.jbsdemo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * worker
 * @author 
 */
@ApiModel(value = "")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Worker implements Serializable {
    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "1男 2女")
    private Byte sex;

    @ApiModelProperty(value = "")
    private String name;

    @ApiModelProperty(value = "类型 1dm")
    private Byte type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table worker
     *
     * @mbg.generated Tue Aug 03 11:28:04 CST 2021
     */
    private static final long serialVersionUID = 1L;
}