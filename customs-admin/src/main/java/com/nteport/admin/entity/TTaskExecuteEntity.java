package com.nteport.admin.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;

/**
 * 指令执行表
 * @TableName t_task_execute
 */
@TableName(value ="t_task_execute")
@Data
public class TTaskExecuteEntity extends BaseEntity implements Serializable {

    /**
     * 指令id
     */
    private Long taskId;

    /**
     * 发布时间
     */
    private String declareDate;

    /**
     * 详情
     */
    private String detail;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 责任部门
     */
    private String dept;

    /**
     * 责任部门名称
     */
    private String deptName;

    /**
     * 频率
     */
    private Long frequency;

    /**
     * 频率名称
     */
    private String frequencyName;

    /**
     * 备注
     */
    private String memo;

    /**
     * 0待执行 1已执行 2已逾期
     */
    private String executed;

    /**
     * 执行时间
     */
    private String executeTime;

    private Long createDept;

    /**
     * 	所在地区县
     */
    private Long areaId;

    private String area;

    /**
     * 	所在街道
     */
    private Long streetId;

    private String street;

    @TableField(exist = false)
    private boolean executeShow;

    private static final long serialVersionUID = 1L;

}