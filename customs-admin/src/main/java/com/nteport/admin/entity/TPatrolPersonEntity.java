package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 巡查负责人表
 * @TableName t_patrol_person
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_patrol_person")
@Data
public class TPatrolPersonEntity extends BaseEntity implements Serializable {

    /**
     * 	t_line_info外键id
     */
    private Long lineId;

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
