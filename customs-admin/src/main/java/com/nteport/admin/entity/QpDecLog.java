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

/**
 * 表头
 */
@TableName(value = "NSW.QP_DECLOG")
public class QpDecLog implements Serializable {
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

	@JSONField(name = "headId")
    private String headid;

	@JSONField(name = "operCode")
    private String opercode;

	@JSONField(name = "operContent")
    private String opercontent;

	@JSONField(name = "operDate")
    private String operdate;

	@JSONField(name = "operUser")
    private String operuser;

	@JSONField(name = "operUserId")
    private String operuserid;

	@JSONField(name = "receiptType")
    private String receipttype;

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

	public String  getHeadid() {
		return headid;
	}
	
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	

	public String  getOpercode() {
		return opercode;
	}
	
	public void setOpercode(String opercode) {
		this.opercode = opercode;
	}
	

	public String  getOpercontent() {
		return opercontent;
	}
	
	public void setOpercontent(String opercontent) {
		this.opercontent = opercontent;
	}
	

	public String  getOperdate() {
		return operdate;
	}
	
	public void setOperdate(String operdate) {
		this.operdate = operdate;
	}
	

	public String  getOperuser() {
		return operuser;
	}
	
	public void setOperuser(String operuser) {
		this.operuser = operuser;
	}
	

	public String  getOperuserid() {
		return operuserid;
	}
	
	public void setOperuserid(String operuserid) {
		this.operuserid = operuserid;
	}
	

	public String  getReceipttype() {
		return receipttype;
	}
	
	public void setReceipttype(String receipttype) {
		this.receipttype = receipttype;
	}
	


}