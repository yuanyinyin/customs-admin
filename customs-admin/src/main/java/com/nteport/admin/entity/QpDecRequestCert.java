package com.nteport.admin.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "NSW.QP_DECREQUESTCERT")
public class QpDecRequestCert implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField("CREATETIME")

    private LocalDateTime createTime;



    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField("UPDATETIME")
    private LocalDateTime updateTime;

    @JSONField(name = "appCertCode")
    private String appcertcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getAppcertcode() {
        return appcertcode;
    }

    public void setAppcertcode(String appcertcode) {
        this.appcertcode = appcertcode;
    }

    public String getAppcertcodeValue() {
        return appcertcodeValue;
    }

    public void setAppcertcodeValue(String appcertcodeValue) {
        this.appcertcodeValue = appcertcodeValue;
    }

    public String getApplcopyquan() {
        return applcopyquan;
    }

    public void setApplcopyquan(String applcopyquan) {
        this.applcopyquan = applcopyquan;
    }

    public String getApplori() {
        return applori;
    }

    public void setApplori(String applori) {
        this.applori = applori;
    }

    public String getHeadid() {
        return headid;
    }

    public void setHeadid(String headid) {
        this.headid = headid;
    }

    @JSONField(name = "appCertCode_value")
    private String appcertcodeValue;

    @JSONField(name = "applCopyQuan")
    private String applcopyquan;

    @JSONField(name = "applOri")
    private String applori;

    @JSONField(name = "headId")
    private String headid;





}