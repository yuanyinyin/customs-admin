package com.nteport.admin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//@TableName(value = "NSW.DEC_FILE")
@TableName(value = "DEC_FILE")
public class DecFile implements Serializable {

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

	private String electronicproxy;


    private String filename;


    private String filepath;


    private String filepathpre;


    private BigDecimal filesize;


    private String filetype;


    private String filetypecode;


    private String headid;


    private String oldfilename;


    private String proxytext;






	public String  getElectronicproxy() {
		return electronicproxy;
	}
	
	public void setElectronicproxy(String electronicproxy) {
		this.electronicproxy = electronicproxy;
	}
	

	public String  getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
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
	

	public String  getFiletype() {
		return filetype;
	}
	
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	

	public String  getFiletypecode() {
		return filetypecode;
	}
	
	public void setFiletypecode(String filetypecode) {
		this.filetypecode = filetypecode;
	}
	

	public String  getHeadid() {
		return headid;
	}
	
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	

	public String  getOldfilename() {
		return oldfilename;
	}
	
	public void setOldfilename(String oldfilename) {
		this.oldfilename = oldfilename;
	}
	

	public String  getProxytext() {
		return proxytext;
	}
	
	public void setProxytext(String proxytext) {
		this.proxytext = proxytext;
	}
	


}