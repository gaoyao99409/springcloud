package com.springcloud.jbsdemo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * script
 * @author 
 */
@ApiModel(value = "")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Script implements Serializable {
    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "")
    private String name;

    @ApiModelProperty(value = "游戏时长 分钟")
    private Integer playMin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table script
     *
     * @mbg.generated Tue Aug 03 11:28:04 CST 2021
     */
    private static final long serialVersionUID = 1L;
}