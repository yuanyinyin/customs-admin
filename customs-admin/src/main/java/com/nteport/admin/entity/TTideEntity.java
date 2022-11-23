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
 * 潮汐发布表
 * @TableName t_tide
 */
@TableName(value ="t_tide")
@Data
public class TTideEntity extends BaseEntity implements Serializable {

    /**
     * 发布时间
     */
    private String declareDate;

    /**
     * 详情
     */
    private String detail;

    /**
     * 涨潮时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private LocalDateTime riseTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private LocalDateTime riseTime2;

    /**
     * 退潮时间
     */

    private LocalDateTime fallTime;
    private LocalDateTime fallTime2;

    /**
     * 	备注
     */
    private String memo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
