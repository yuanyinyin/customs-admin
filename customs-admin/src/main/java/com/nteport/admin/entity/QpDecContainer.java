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
@TableName(value = "NSW.QP_DECCONTAINER")
public class QpDecContainer  implements Serializable {

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

	@JSONField(name = "containerId")
	private String containerid;

	@JSONField(name = "containerMd")
    private String containermd;

	@JSONField(name = "containerMd_value")
    private String containermdValue;

	@JSONField(name = "containerWt")
    private String containerwt;

	@JSONField(name = "headId")
    private String headid;

	@JSONField(name = "goodsNo")
    private String goodsno;

	@JSONField(name = "lclFlag")
    private String lclflag;

	@JSONField(name = "lclFlag_value")
    private String lclflagValue;






	public String  getContainerid() {
		return containerid;
	}
	
	public void setContainerid(String containerid) {
		this.containerid = containerid;
	}
	

	public String  getContainermd() {
		return containermd;
	}
	
	public void setContainermd(String containermd) {
		this.containermd = containermd;
	}
	

	public String  getContainermdValue() {
		return containermdValue;
	}
	
	public void setContainermdValue(String containermdValue) {
		this.containermdValue = containermdValue;
	}
	

	public String  getContainerwt() {
		return containerwt;
	}
	
	public void setContainerwt(String containerwt) {
		this.containerwt = containerwt;
	}
	

	public String  getHeadid() {
		return headid;
	}
	
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	

	public String  getGoodsno() {
		return goodsno;
	}
	
	public void setGoodsno(String goodsno) {
		this.goodsno = goodsno;
	}
	

	public String  getLclflag() {
		return lclflag;
	}
	
	public void setLclflag(String lclflag) {
		this.lclflag = lclflag;
	}
	

	public String  getLclflagValue() {
		return lclflagValue;
	}
	
	public void setLclflagValue(String lclflagValue) {
		this.lclflagValue = lclflagValue;
	}
	


}