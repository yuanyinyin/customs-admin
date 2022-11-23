package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色和地区对应表
 * @TableName t_role_area
 */
@TableName(value ="t_role_area")
@Data
public class TRoleAreaEntity implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     * t_role外键id
     */
    private Long roleId;

    /**
     * 地区
     */
    private String area;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
