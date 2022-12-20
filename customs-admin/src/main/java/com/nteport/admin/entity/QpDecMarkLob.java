package com.nteport.admin.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@TableName(value = "NSW.QP_DECMARKLOB")
public class QpDecMarkLob implements Serializable{

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
	@JSONField(name = "attachName")
	private String attachname;

	@JSONField(name = "attachType")
    private String attachtype;

	@JSONField(name = "attachment")
    private String attachment;

	@JSONField(name = "filePath")
    private String filepath;

	@JSONField(name = "filePathPre")
    private String filepathpre;

	@JSONField(name = "fileSize")
    private BigDecimal filesize;

	@JSONField(name = "headId")
    private String headid;

	@JSONField(name = "state")
    private String state;






	public String  getAttachname() {
		return attachname;
	}
	
	public void setAttachname(String attachname) {
		this.attachname = attachname;
	}
	

	public String  getAttachtype() {
		return attachtype;
	}
	
	public void setAttachtype(String attachtype) {
		this.attachtype = attachtype;
	}
	

	public String  getAttachment() {
		return attachment;
	}
	
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	

	public String  getFilepath() {
		return filepath;
	}
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	

	public String  getFilepathpre() {
		return filepathpre;
	}
	
	public void setFilepathpre(String filepathpre) {
		this.filepathpre = filepathpre;
	}
	

	public BigDecimal  getFilesize() {
		return filesize;
	}
	
	public void setFilesize(BigDecimal filesize) {
		this.filesize = filesize;
	}
	

	public String  getHeadid() {
		return headid;
	}
	
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	

	public String  getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	


}