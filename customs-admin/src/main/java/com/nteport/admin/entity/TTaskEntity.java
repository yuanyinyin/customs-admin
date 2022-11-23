package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;

/**
 * 巡查指令表
 * @TableName t_task
 */
@TableName(value ="t_task")
@Data
public class TTaskEntity extends BaseEntity implements Serializable {

    /**
     * 发布时间
     */
    private String declareDate;

    /**
     * 详情
     */
    private String detail;

    private String startDate;
    private String endDate;
    /**
     * 责任部门
     */
    private String dept;
    private String deptName;

    /**
     * 频率
     */
    private Long frequency;
    private String frequencyName;

    /**
     * 	备注
     */
    private String memo;

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

    private Long createDept;

    private String water;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Long> personList;

    @TableField(exist = false)
    private boolean executeShow;
}
