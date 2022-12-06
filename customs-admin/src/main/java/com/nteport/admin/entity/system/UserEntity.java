package com.nteport.admin.entity.system;

import java.io.Serializable;
import java.util.ArrayList;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nteport.admin.entity.system.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体
 */

@TableName("T_USER")
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户对象", description = "用户表")
public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String realName;

    private String userName;

    private String deptId;

    @TableField(exist = false)
    private String deptName;

    @JSONField(serialize = false)
    private String userPassword;

    private String telephone;

    @JSONField(serialize = false)
    private String token;

    @JSONField(serialize = false)
    private String openId;

    /**
     * 审核状态，1-待审核，2-审核通过
     */
    private String status;

    @TableField(exist = false)
    private String captchaCode;

//    @JSONField(serialize = false)
//    private String openId;

    private String job;

    /**
     * 值等于userid
     * 为了前端构造树标签
     */
    @TableField(exist = false)
    private String value;

    //角色编码数组
    @TableField(exist = false)
    private ArrayList<String> roleCodes =new ArrayList<>();

    /**
     * 值等于userName
     * 为了前端构造树标签
     */
    @TableField(exist = false)
    private String label;

    public void setRealName(String realName) {
        this.realName = realName;
        this.label=realName;
    }

    @Override
    public void setId(String id) {
        super.setId(id);
        this.value=id;
    }
}
