package com.springcloud.springcloudshardingjdbcnew.mapper.primary;

import java.util.List;
import java.util.Map;

import com.springcloud.springcloudshardingjdbcnew.model.primary.User;

public interface PrimaryUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Mon Mar 22 09:50:42 CST 2021
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Mon Mar 22 09:50:42 CST 2021
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Mon Mar 22 09:50:42 CST 2021
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Mon Mar 22 09:50:42 CST 2021
     */
    User selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Mon Mar 22 09:50:42 CST 2021
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Mon Mar 22 09:50:42 CST 2021
     */
    int updateByPrimaryKey(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Mon Mar 22 09:50:42 CST 2021
     */
    List<User> getList(Map<String, Object> map);
}