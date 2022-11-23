package com.nteport.admin.entity.system;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色对应菜单实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_role_menu")
public class RoleMenuEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;

}
