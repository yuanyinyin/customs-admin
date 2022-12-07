package com.nteport.admin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
@TableName(value = "QP_DECLICENSEDOCU")

public class QpDecLicenseDocu implements Serializable {

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

	private String certcode;


    private String docucode;


    private String docucodeValue;


    private String headid;






	public String  getCertcode() {
		return certcode;
	}
	
	public void setCertcode(String certcode) {
		this.certcode = certcode;
	}
	

	public String  getDocucode() {
		return docucode;
	}
	
	public void setDocucode(String docucode) {
		this.docucode = docucode;
	}
	

	public String  getDocucodeValue() {
		return docucodeValue;
	}
	
	public void setDocucodeValue(String docucodeValue) {
		this.docucodeValue = docucodeValue;
	}
	

	public String  getHeadid() {
		return headid;
	}
	
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	


}