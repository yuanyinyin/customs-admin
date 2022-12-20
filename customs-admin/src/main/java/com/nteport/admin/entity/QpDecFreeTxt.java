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
@TableName(value = "NSW.QP_DECFREETXT")

public class QpDecFreeTxt implements Serializable {


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

	@JSONField(name = "bonNo")
	private String bonno;

	@JSONField(name = "cusFie")
    private String cusfie;

	@JSONField(name = "decBpNo")
    private String decbpno;

	@JSONField(name = "decNo")
    private String decno;

	@JSONField(name = "headId")
    private String headid;

	@JSONField(name = "relId")
    private String relid;

	@JSONField(name = "relManNo")
    private String relmanno;






	public String  getBonno() {
		return bonno;
	}
	
	public void setBonno(String bonno) {
		this.bonno = bonno;
	}
	

	public String  getCusfie() {
		return cusfie;
	}
	
	public void setCusfie(String cusfie) {
		this.cusfie = cusfie;
	}
	

	public String  getDecbpno() {
		return decbpno;
	}
	
	public void setDecbpno(String decbpno) {
		this.decbpno = decbpno;
	}
	

	public String  getDecno() {
		return decno;
	}
	
	public void setDecno(String decno) {
		this.decno = decno;
	}
	

	public String  getHeadid() {
		return headid;
	}
	
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	

	public String  getRelid() {
		return relid;
	}
	
	public void setRelid(String relid) {
		this.relid = relid;
	}
	

	public String  getRelmanno() {
		return relmanno;
	}
	
	public void setRelmanno(String relmanno) {
		this.relmanno = relmanno;
	}
	


}