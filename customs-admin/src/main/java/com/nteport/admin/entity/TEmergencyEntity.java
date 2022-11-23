package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 突发事件表
 * @TableName t_emergency
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_emergency")
@Data
public class TEmergencyEntity extends BaseEntity implements Serializable {

    /**
     * t_daily_record外键id
     */
    private Long headId;

    /**
     * 上报日期
     */
    private String reportTime;

    /**
     * 突发事件状况
     */
    private String situation;

    /**
     * 突发事件地点,t_point_info外键id
     */
    private Long pointId;

    /**
     * 	点位坐标
     */
    private String position;

    private String address;

    /**
     * 照片
     */
    private String photoId;

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
     * 状态：0-待处理，1-已处理
     */
    private String status;

    /**
     * 	处理结果
     */
    private String result;

    /**
     * 协助部门
     */
    private String assist;

    private String assistname;

    /**
     * 	备注
     */
    private String memo;

    private Long createDept;

    /**
     * 点位名称 为了减少前端查询
     */
    @TableField(exist = false)
    private String pointName;

    /**
     * 按钮显示
     */
    @TableField(exist = false)
    private boolean buttonShow;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
