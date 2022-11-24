package com.nteport.admin.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户-角色实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_USER_ROLE")
public class UserRoleEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String roleId;

}
