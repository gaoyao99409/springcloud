package com.springcloud.rabbitmq.bean;

/**
 * @ClassName UserDTO
 * @Description UserDTO
 * @Author gaoyao
 * @Date 2021/8/12 5:20 PM
 * @Version 1.0
 */
public class UserDTO {
    private String username;
    private Integer type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
