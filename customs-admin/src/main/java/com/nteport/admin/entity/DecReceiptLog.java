package com.nteport.admin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
//@TableName(value = "NSW.DECRECEIPTLOG")
@TableName(value = "DECRECEIPTLOG")
public class DecReceiptLog implements Serializable {

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

	private String customstate;


    private String headid;


    private String noticedate;


    private String receiptcontent;


    private String receipttype;






	public String  getCustomstate() {
		return customstate;
	}
	
	public void setCustomstate(String customstate) {
		this.customstate = customstate;
	}
	

	public String  getHeadid() {
		return headid;
	}
	
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	

	public String  getNoticedate() {
		return noticedate;
	}
	
	public void setNoticedate(String noticedate) {
		this.noticedate = noticedate;
	}
	

	public String  getReceiptcontent() {
		return receiptcontent;
	}
	
	public void setReceiptcontent(String receiptcontent) {
		this.receiptcontent = receiptcontent;
	}
	

	public String  getReceipttype() {
		return receipttype;
	}
	
	public void setReceipttype(String receipttype) {
		this.receipttype = receipttype;
	}
	


}