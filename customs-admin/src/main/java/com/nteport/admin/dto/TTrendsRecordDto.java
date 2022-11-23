package com.nteport.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nteport.admin.entity.TTrendsRecordDetailEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class TTrendsRecordDto {
    private Long id;

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

    /**
     *
     */
    private Long createUser;

    /**
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    /**
     *
     */
    private Long updateUser;

    /**
     *
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;

    /**
     *
     */
    private String creatorName;

    private List<TTrendsRecordDetailEntity> detailList;
}
