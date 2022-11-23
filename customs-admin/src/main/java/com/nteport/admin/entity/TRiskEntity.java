package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;

/**
 * 隐患表
 * @TableName t_risk
 */
@TableName(value ="t_risk")
@Data
public class TRiskEntity extends BaseEntity implements Serializable {

    /**
     * t_period_record外键id
     */
    private Long periodId;

    /**
     * t_trends_record外键id
     */
    private Long trendsId;

    /**
     * 上报时间
     */
    private String reportTime;

    /**
     * 风险类别：0-一般，1-较大风险
     */
    private String type;

    /**
     * 突发事件状况
     */
    private String situation;

    /**
     * 事件地点,t_point_info外键id
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

    /**
     * 状态：0-待处理，1-已处理
     */
    private String status;

    /**
     * 	备注
     */
    private String memo;

    private String result;

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
