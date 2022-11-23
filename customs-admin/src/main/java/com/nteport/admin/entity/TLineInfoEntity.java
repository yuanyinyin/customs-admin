package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @TableName t_line_info
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_line_info")
@Data
public class TLineInfoEntity extends BaseEntity implements Serializable {

    private String name;

    /**
     * 起点坐标
     */
    private String startPosition;

    /**
     * 	起点地址
     */
    private String startAddress;

    /**
     * 终点坐标
     */
    private String endPosition;

    /**
     * 	终点位置
     */
    private String endAddress;

    /**
     * 备注
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
