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
 * 每日巡详情
 * @TableName t_daily_record_detail
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_daily_record_detail")
@Data
public class TDailyRecordDetailEntity extends BaseEntity implements Serializable {

    /**
     * t_daily_record的外键id
     */
    private Long headId;

    /**
     * t_point_info的外键id
     */
    private Long pointId;

    /**
     * t_line_info的外键id
     */
    private Long lineId;

    /**
     * 打卡定位
     */
    private String position;

    /**
     * 	备注
     */
    private String memo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
