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

@TableName(value = "NSW.QP_DECRESULT")
public class QpDecResult implements Serializable{



	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

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

	public String getdDate() {
		return dDate;
	}

	public void setdDate(String dDate) {
		this.dDate = dDate;
	}

	public String getiE_date() {
		return iE_date;
	}

	public void setiE_date(String iE_date) {
		this.iE_date = iE_date;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	@TableField("CREATETIME")

	private LocalDateTime createTime;



	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	@TableField("UPDATETIME")
	private LocalDateTime updateTime;


	@JSONField(name = "CHANNEL")
    private String channel;

	@JSONField(name = "CUSTOM_MASTER")
    private String customMaster;

	@JSONField(name = "CUS_CIQ_NO")
	@TableField("CUS_CIQ_NO")
    private String cusCiq_no;

	@JSONField(name = "D_DATE")
    private String dDate;

	@JSONField(name = "ENTRY_ID")
    private String entryId;

	@TableField("I_E_DATE")
	@JSONField(name = "I_E_DATE")
    private String iE_date;

	@JSONField(name = "NOTE")
    private String note;

	@JSONField(name = "NOTICE_DATE")
    private String noticeDate;

	@JSONField(name = "orgId")
    private String orgid;

	@JSONField(name = "orgName")
    private String orgname;






	public String  getChannel() {
		return channel;
	}
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	

	public String  getCustomMaster() {
		return customMaster;
	}
	
	public void setCustomMaster(String customMaster) {
		this.customMaster = customMaster;
	}
	

	public String  getCusCiq_no() {
		return cusCiq_no;
	}
	
	public void setCusCiq_no(String cusCiq_no) {
		this.cusCiq_no = cusCiq_no;
	}
	

	public String  getDDate() {
		return dDate;
	}
	
	public void setDDate(String dDate) {
		this.dDate = dDate;
	}
	

	public String  getEntryId() {
		return entryId;
	}
	
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	

	public String  getIE_date() {
		return iE_date;
	}
	
	public void setIE_date(String iE_date) {
		this.iE_date = iE_date;
	}
	

	public String  getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	

	public String  getNoticeDate() {
		return noticeDate;
	}
	
	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}
	

	public String  getOrgid() {
		return orgid;
	}
	
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	

	public String  getOrgname() {
		return orgname;
	}
	
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	


}