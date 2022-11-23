package com.nteport.admin.dto;

import com.nteport.admin.entity.TPeriodRecordDetailEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class TPeriodRecordDto {
    private Long id;

    /**
     * 巡查时间
     */
    private String patrolTime;

    /**
     * 巡查开始时间
     */
    private LocalDateTime startTime;

    /**
     * 巡查结束时间
     */
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
    private LocalDateTime createTime;

    /**
     *
     */
    private Long updateUser;

    /**
     *
     */
    private LocalDateTime updateTime;

    /**
     *
     */
    private String creatorName;

    private List<TPeriodRecordDetailEntity> detailList;
}
