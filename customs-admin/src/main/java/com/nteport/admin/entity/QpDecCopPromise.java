package com.nteport.admin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "NSW.QP_DECCOPPROMISE")
public class QpDecCopPromise  implements Serializable {

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
    private String declaratiomaterialcode;


    private String declaratiomaterialcodeValue;


    private String headid;






	public String  getDeclaratiomaterialcode() {
		return declaratiomaterialcode;
	}
	
	public void setDeclaratiomaterialcode(String declaratiomaterialcode) {
		this.declaratiomaterialcode = declaratiomaterialcode;
	}
	

	public String  getDeclaratiomaterialcodeValue() {
		return declaratiomaterialcodeValue;
	}
	
	public void setDeclaratiomaterialcodeValue(String declaratiomaterialcodeValue) {
		this.declaratiomaterialcodeValue = declaratiomaterialcodeValue;
	}
	

	public String  getHeadid() {
		return headid;
	}
	
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	


}