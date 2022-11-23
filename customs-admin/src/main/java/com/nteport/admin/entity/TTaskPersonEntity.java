package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;

/**
 * 巡查指令子表
 * @TableName t_task_person
 */
@TableName(value ="t_task_person")
@Data
public class TTaskPersonEntity extends BaseEntity implements Serializable {

    /**
     * t_user的外键id
     */
    private Long personId;

    private String personName;

    /**
     * 完成状态：1-未完成，2-已完成
     */
    private String status;

    /**
     * t_task的外键id
     */
    private Long headId;

    /**
     * 	备注
     */
    private String memo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
