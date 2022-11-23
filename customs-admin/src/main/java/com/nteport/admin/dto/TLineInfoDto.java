package com.nteport.admin.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nteport.admin.entity.TPatrolPersonEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TLineInfoDto {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 	点位名称
     */
    private String name;

    /**
     * 	起点坐标
     */
    private String startPosition;

    /**
     * 	起点地址
     */
    private String startAddress;

    /**
     * 	终点坐标
     */
    private String endPosition;

    /**
     * 	终点位置
     */
    private String endAddress;

    /**
     *
     */
    private String memo;

    /**
     * 	状态：0-暂存，1-待审核，2-审核通过，3-审核未通过
     */
    private String status;

    private Long areaId;
    /**
     * 	所在地区县
     */
    private String area;

    private Long streetId;
    /**
     * 	所在街道
     */
    private String street;

    /**
     *
     */
    private String creatorName;

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
     * 监管责任人表
     */
    private List<TPatrolPersonEntity> patrolPersonList;
}
