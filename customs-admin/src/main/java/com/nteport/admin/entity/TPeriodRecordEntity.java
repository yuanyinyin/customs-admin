package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 定期查
 * @TableName t_period_record
 */
@TableName(value ="t_period_record")
@Data
public class TPeriodRecordEntity extends BaseEntity implements Serializable {
    /**
     * 巡查时间
     */
    private String patrolTime;

    /**
     * 巡查开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime startTime;

    /**
     * 巡查结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime endTime;

    /**
     * 巡查情况
     */
    private String result;

    /**
     * 照片
     */
    private String photoId;

    /**
     * 	备注
     */
    private String memo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
