package com.springcloud.shardingjdbctable.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * t_order
 * @author 
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Serializable {

    private Long id;

    private Long userId;

    private Long orderId;

    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_order
     *
     * @mbg.generated Tue Apr 20 14:08:48 CST 2021
     */
    private static final long serialVersionUID = 1L;
}