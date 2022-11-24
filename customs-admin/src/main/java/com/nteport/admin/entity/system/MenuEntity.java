package com.nteport.admin.entity.system;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_MENU")
@ApiModel(value = "菜单对象", description = "菜单表")
public class MenuEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父级菜单id
     */
    private String pid;

    /**
     * 菜单名称（英文）
     */
    private String name;

    /**
     * 菜单路由
     */
    private String path;

    /**
     * 菜单相对路径
     */
    private String component;

    /**
     * 菜单名称（中文）
     */
    private String title;

    /**
     * 菜单按钮
     */
    private String icon;

    /**
     * 排序
     */
    private String sort;

    /**
     * 0--可见，1--不可见
     */
    private String hidden;

    /**
     * 0-没有，1-有
     */
    private String hasChildren;

    private String permissions;

    private String redirect;

    private String type;

    /**
     * 是否始终显示父级菜单 0-否，1-是
     */
    private String alwaysShow;

}
