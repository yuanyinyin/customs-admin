package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


/**
 * 
 * @TableName t_app_version
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_app_version")
@Data
public class TAppVersionEntity extends BaseEntity implements Serializable {

    /**
     * type: android ios 
     */
    private String type;

    /**
     * app版本号
     */
    private String version;

    /**
     * 是否最新版本
     */
    private Integer isNew;

    /**
     * 版本描述
     */
    private String des;

    /**
     * 下载地址
     */
    private String downurl;

    private static final long serialVersionUID = 1L;

}