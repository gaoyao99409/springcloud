package com.springcloud.springcloudshardingjdbc.old.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private Long id;

    private String name;

    private Integer age;

    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user
     *
     * @mbg.generated Mon Mar 22 09:50:42 CST 2021
     */
    private static final long serialVersionUID = 1L;
}