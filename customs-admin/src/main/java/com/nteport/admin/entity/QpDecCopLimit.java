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

@TableName(value = "NSW.QP_DECCOPLIMIT")
public class QpDecCopLimit  implements Serializable  {
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

	@JSONField(name = "entQualifNo")
    private String entqualifno;

	@JSONField(name = "entQualifTypeCode")
    private String entqualiftypecode;

	@JSONField(name = "entQualifTypeCode_value")
    private String entqualiftypecodeValue;

	@JSONField(name = "headId")
    private String headid;






	public String  getEntqualifno() {
		return entqualifno;
	}
	
	public void setEntqualifno(String entqualifno) {
		this.entqualifno = entqualifno;
	}
	

	public String  getEntqualiftypecode() {
		return entqualiftypecode;
	}
	
	public void setEntqualiftypecode(String entqualiftypecode) {
		this.entqualiftypecode = entqualiftypecode;
	}
	

	public String  getEntqualiftypecodeValue() {
		return entqualiftypecodeValue;
	}
	
	public void setEntqualiftypecodeValue(String entqualiftypecodeValue) {
		this.entqualiftypecodeValue = entqualiftypecodeValue;
	}
	

	public String  getHeadid() {
		return headid;
	}
	
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	


}