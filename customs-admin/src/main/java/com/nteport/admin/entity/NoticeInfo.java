package com.nteport.admin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "T_NOTICE_INFO")
public class NoticeInfo implements Serializable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	@TableField("CREATETIME")

	private LocalDateTime createTime;


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

	public String getNoticeUserId() {
		return noticeUserId;
	}

	public void setNoticeUserId(String noticeUserId) {
		this.noticeUserId = noticeUserId;
	}

	public String getNoticeUserName() {
		return noticeUserName;
	}

	public void setNoticeUserName(String noticeUserName) {
		this.noticeUserName = noticeUserName;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	@TableField("UPDATETIME")
	private LocalDateTime updateTime;

    private String type;


    private String content;

	@TableField("NOTICE_USER_ID")
    private String noticeUserId;


    private String noticeUserName;


    private String flag;






	public String  getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	

	public String  getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	


	

	public String  getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	


}