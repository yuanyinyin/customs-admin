package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 定期检查记录子表
 * @TableName t_trends_record_detail
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_trends_record_detail")
@Data
public class TTrendsRecordDetailEntity extends BaseEntity implements Serializable {
    /**
     * t_period_record外键id
     */
    private Long headId;
    /**
     * t_point_info的外键id
     */
    private Long pointId;
    /**
     * 巡查日期
     */
    private String patrolDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
