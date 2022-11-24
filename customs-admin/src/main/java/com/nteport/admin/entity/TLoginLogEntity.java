package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;

/**
 *
 * @TableName t_login_log
 */
@TableName(value ="T_LOGIN_LOG")
@Data
public class TLoginLogEntity extends BaseEntity implements Serializable {


    /**
     * 登陆人账号（实际为手机号）
     */
    private String userName;

    /**
     * 登陆人所在部门，t_dept外键id
     */
    private String deptId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
