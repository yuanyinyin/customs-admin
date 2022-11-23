package com.nteport.admin.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 日志实体
 */
@TableName("t_log")
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "LogEntity对象", description = "日志表")
public class LogEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("操作模块")
    private String opeModule;

    @ApiModelProperty("操作描述")
    private String opeDesc;

    @ApiModelProperty("请求方法")
    private String opeMethod;

    @ApiModelProperty("请求参数")
    private String opeReqUri;

    @ApiModelProperty("操作人")
    private String handler;

    @ApiModelProperty("操作员ip")
    private String opeIp;

}
