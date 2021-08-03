package com.springcloud.jbsdemo.mapper;

import java.util.List;
import java.util.Map;

import com.springcloud.jbsdemo.model.ScriptWorker;

public interface ScriptWorkerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table script_worker
     *
     * @mbg.generated Tue Aug 03 11:28:04 CST 2021
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table script_worker
     *
     * @mbg.generated Tue Aug 03 11:28:04 CST 2021
     */
    int insert(ScriptWorker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table script_worker
     *
     * @mbg.generated Tue Aug 03 11:28:04 CST 2021
     */
    int insertSelective(ScriptWorker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table script_worker
     *
     * @mbg.generated Tue Aug 03 11:28:04 CST 2021
     */
    ScriptWorker selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table script_worker
     *
     * @mbg.generated Tue Aug 03 11:28:04 CST 2021
     */
    int updateByPrimaryKeySelective(ScriptWorker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table script_worker
     *
     * @mbg.generated Tue Aug 03 11:28:04 CST 2021
     */
    int updateByPrimaryKey(ScriptWorker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table script_worker
     *
     * @mbg.generated Tue Aug 03 11:28:04 CST 2021
     */
    List<ScriptWorker> getList(Map<String, Object> map);
}