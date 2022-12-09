package com.nteport.admin.entity.system;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 角色对应菜单实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_ROLE_MENU")
public class RoleMenuEntity implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;
//    private Date createTime;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;
//    private Date updateTime;

    private String createUser;

    private String updateUser;

    private String creatorName;

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String menuId;

}
