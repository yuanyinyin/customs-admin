package com.nteport.admin.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;

/**
 * 
 * @TableName t_camera_info
 */
@TableName(value ="t_camera_info")
@Data
public class TCameraInfo extends BaseEntity  implements Serializable {

    /**
     * 沿江还是沿海：1-沿江，2-沿海
     */
    private String along;

    /**
     * 点位名称
     */
    private String name;

    /**
     * 点位坐标
     */
    private String position;

    /**
     * 点位地址
     */
    private String address;

    /**
     * 区县id
     */
    private Long areaId;

    /**
     * 所在地区县
     */
    private String area;

    /**
     * 街道id
     */
    private Long streetId;

    /**
     * 街道
     */
    private String street;

    /**
     * 备注
     */
    private String memo;

    /**
     * 状态：0-暂存，1-待审核，2-审核通过，3-审核未通过
     */
    private String status;

    /**
     * 监控国标码
     */
    private String gbcode;


    private static final long serialVersionUID = 1L;

}