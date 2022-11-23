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
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 每日巡
 * @TableName t_daily_record
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_daily_record")
@Data
public class TDailyRecordEntity extends BaseEntity implements Serializable {

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
     * 1每日巡  2定期查  3巡查指令
     */
    private String type;

    private Long taskExecuteId;

    /**
     * 	备注
     */
    private String memo;

    //状态0完成 1完成
    private String status;

    /**
     * 	所在地区县
     */
    private String area;

    private Long areaId;

    /**
     * 	所在街道
     */
    private String street;

    private Long streetId;

    private Long createDept;

    private String createDeptName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
