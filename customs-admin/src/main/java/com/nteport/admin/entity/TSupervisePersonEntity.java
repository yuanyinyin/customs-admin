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
 * 监管责任人表
 * @TableName t_supervise_person
 */
@TableName(value ="t_supervise_person")
@Data
public class TSupervisePersonEntity extends BaseEntity implements Serializable {

    /**
     * 	t_point_info外键id
     */
    private Long pointId;

    /**
     * 	姓名
     */
    private String name;

    /**
     * 	职务
     */
    private String job;

    /**
     * 	手机
     */
    private String telephone;

    /**
     * 	状态：0-暂存，1-待审核，2-审核通过
     */
    private String status;

    /**
     * 	备注
     */
    private String memo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
