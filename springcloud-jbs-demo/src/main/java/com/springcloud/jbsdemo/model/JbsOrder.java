package com.springcloud.jbsdemo.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * order
 * @author 
 */
@ApiModel(value = "")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JbsOrder implements Serializable {
    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "")
    private Long scriptId;

    @ApiModelProperty(value = "")
    private Long roomId;

    @ApiModelProperty(value = "")
    private Date beginTime;

    @ApiModelProperty(value = "")
    private Date endTime;

    @ApiModelProperty(value = "")
    private Integer playerNum;

    @ApiModelProperty(value = "")
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table order
     *
     * @mbg.generated Tue Aug 03 11:28:04 CST 2021
     */
    private static final long serialVersionUID = 1L;
}