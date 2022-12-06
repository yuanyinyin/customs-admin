package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门表
 * @TableName t_dept
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="T_DEPT")
@Data
public class TDeptEntity extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 父部门id
     */
    private String parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    private String areaId;
    private String area;

    private String street;
    private String streetId;

    /**
     * 负责人
     */
    private String leaderId;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 1-沿江，2-沿海
     */
    private String along;

    /**
     * app-移动端是否部门标记
     */
    @TableField(exist = false)
    private boolean tree = true;

    /**
     * 值等于deptid
     * 为了前端构造树标签
     */
    @TableField(exist = false)
    private String value;

    /**
     * 值等于deptName
     * 为了前端构造树标签
     */
    @TableField(exist = false)
    private String label;

    /**
     *
     * 为了前端构造树标签
     */
    @TableField(exist = false)
    private boolean disabled;

    /** 子部门 */
    @TableField(exist = false)
    private List children = new ArrayList();

    public void setDeptName(String deptName) {
        this.deptName = deptName;
        this.label = deptName;
    }

    @Override
    public void setId(String id) {
        super.setId(id);
        this.value="dept"+id;
    }
}
