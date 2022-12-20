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

@TableName(value = "NSW.QP_DECLIST")

public class QpDecList implements Serializable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	//new
//	@ModelAttr("原产地证明项号")
	@TableField("CERTIFICATENO")
	private String certificateNo;
	@TableField("CERTTYPE")
	private String certType;//单据类型
	@TableField("CERTTYPE_VALUE")
	private String certType_value;//单据类型
	@TableField("ECOCERTNO")
	private String ecoCertNo;//单据证书号
	@TableField("EXTENDFILED")
	private String extendFiled;  //扩展字段

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertType_value() {
		return certType_value;
	}

	public void setCertType_value(String certType_value) {
		this.certType_value = certType_value;
	}

	public String getEcoCertNo() {
		return ecoCertNo;
	}

	public void setEcoCertNo(String ecoCertNo) {
		this.ecoCertNo = ecoCertNo;
	}

	public String getExtendFiled() {
		return extendFiled;
	}

	public void setExtendFiled(String extendFiled) {
		this.extendFiled = extendFiled;
	}

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

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	@TableField("UPDATETIME")
	private LocalDateTime updateTime;

	@JSONField(name = "currentrate")
    private String currentrate;

	@JSONField(name = "agreementId")
    private String agreementid;

	@JSONField(name = "agreementId_value")
    private String agreementidValue;

	@JSONField(name = "classMark")
    private String classmark;

	@JSONField(name = "codeTS")
    private String codets;

	@JSONField(name = "codeTS_value")
    private String codetsValue;

	@JSONField(name = "contrItem")
    private String contritem;

	@JSONField(name = "declPrice")
		private String declprice;

	@JSONField(name = "declTotal")
    private String decltotal;

	@JSONField(name = "destinationCountry")
    private String destinationcountry;

	@JSONField(name = "destinationCountry_value")
    private String destinationcountryValue;

	@JSONField(name = "dutyMode")
    private String dutymode;

	@JSONField(name = "dutyMode_value")
    private String dutymodeValue;

	@JSONField(name = "exgNo")
    private String exgno;

	@JSONField(name = "exgVersion")
    private String exgversion;

	@JSONField(name = "extraCode")
    private String extracode;

	@JSONField(name = "factor")
    private String factor;

	@JSONField(name = "firstQty")
    private String firstqty;

	@JSONField(name = "firstQty_value")
    private String firstqtyValue;

	@JSONField(name = "firstunit")
    private String firstunit;

	@JSONField(name = "firstUnit_value")
    private String firstunitValue;

	@JSONField(name = "gmodel")
    private String gmodel;

	@JSONField(name = "gmodel_mood")
    private String gmodelMood;

	@JSONField(name = "gname")
    private String gname;

	@JSONField(name = "gno")
    private String gno;

	@JSONField(name = "gqty")
    private String gqty;

	@JSONField(name = "gunit")
    private String gunit;

	@JSONField(name = "gunit_value")
    private String gunitValue;

	@JSONField(name = "headId")
    private String headid;

	@JSONField(name = "jgzj")
    private String jgzj;

	@JSONField(name = "originCountry")
    private String origincountry;

	@JSONField(name = "originCountry_value")
    private String origincountryValue;

	@JSONField(name = "secondQty")
    private String secondqty;

	@JSONField(name = "headId")
    private String secondqtyValue;

	@JSONField(name = "headId")
    private String secondunit;

	@JSONField(name = "secondQty_value")
    private String secondunitValue;

	@JSONField(name = "tradeCurr")
    private String tradecurr;

	@JSONField(name = "tradeCurr_value")
    private String tradecurrValue;

	@JSONField(name = "useTo")
		private String useto;

	@JSONField(name = "workUsd")
    private String workusd;

	@JSONField(name = "ciqCode")
    private String ciqcode;

	@JSONField(name = "ciqName")
    private String ciqname;

	@JSONField(name = "dangName")
		private String dangname;

	@JSONField(name = "headId")
    private String dangpackspec;

	@JSONField(name = "dangPackSpec")
    private String dangpacktype;

	@JSONField(name = "dangPackType_value")
		private String dangpacktypeValue;

	@JSONField(name = "dangerFlag")
    private String dangerflag;

	@JSONField(name = "declGoodsEname")
    private String declgoodsename;

	@JSONField(name = "destcode")
		private String destcode;

	@JSONField(name = "destCode_value")
    private String destcodeValue;

	@JSONField(name = "districtCode")
    private String districtcode;

	@JSONField(name = "districtCode_value")
    private String districtcodeValue;

	@JSONField(name = "engManEntCnm")
    private String engmanentcnm;

	@JSONField(name = "goodsAttr")
    private String goodsattr;

	@JSONField(name = "goodsAttr_value")
    private String goodsattrValue;

	@JSONField(name = "goodsBrand")
    private String goodsbrand;

	@JSONField(name = "goodsModel")
    private String goodsmodel;

	@JSONField(name = "goodsSpec")
    private String goodsspec;

	@JSONField(name = "jyjyGgxh")
    private String jyjyggxh;

	@JSONField(name = "mnufctrRegName")
    private String mnufctrregname;

	@JSONField(name = "mnufctrRegno")
    private String mnufctrregno;

	@JSONField(name = "noDangFlag")
    private String nodangflag;

	@JSONField(name = "noDangFlag_value")
    private String nodangflagValue;

	@JSONField(name = "origPlaceCode")
    private String origplacecode;

	@JSONField(name = "origPlaceCode_value")
    private String origplacecodeValue;

	@JSONField(name = "prodBatchNo")
    private String prodbatchno;

	@JSONField(name = "prodQgp")
    private String prodqgp;

	@JSONField(name = "prodValidDt")
    private String prodvaliddt;

	@JSONField(name = "produceDate")
    private String producedate;

	@JSONField(name = "purpose")
    private String purpose;

	@JSONField(name = "purpose_value")
    private String purposeValue;

	@JSONField(name = "stuff")
    private String stuff;

	@JSONField(name = "unCode")
    private String uncode;






	public String  getCurrentrate() {
		return currentrate;
	}
	
	public void setCurrentrate(String currentrate) {
		this.currentrate = currentrate;
	}
	

	public String  getAgreementid() {
		return agreementid;
	}
	
	public void setAgreementid(String agreementid) {
		this.agreementid = agreementid;
	}
	

	public String  getAgreementidValue() {
		return agreementidValue;
	}
	
	public void setAgreementidValue(String agreementidValue) {
		this.agreementidValue = agreementidValue;
	}
	

	public String  getClassmark() {
		return classmark;
	}
	
	public void setClassmark(String classmark) {
		this.classmark = classmark;
	}
	

	public String  getCodets() {
		return codets;
	}
	
	public void setCodets(String codets) {
		this.codets = codets;
	}
	

	public String  getCodetsValue() {
		return codetsValue;
	}
	
	public void setCodetsValue(String codetsValue) {
		this.codetsValue = codetsValue;
	}
	

	public String  getContritem() {
		return contritem;
	}
	
	public void setContritem(String contritem) {
		this.contritem = contritem;
	}
	

	public String  getDeclprice() {
		return declprice;
	}
	
	public void setDeclprice(String declprice) {
		this.declprice = declprice;
	}
	

	public String  getDecltotal() {
		return decltotal;
	}
	
	public void setDecltotal(String decltotal) {
		this.decltotal = decltotal;
	}
	

	public String  getDestinationcountry() {
		return destinationcountry;
	}
	
	public void setDestinationcountry(String destinationcountry) {
		this.destinationcountry = destinationcountry;
	}
	

	public String  getDestinationcountryValue() {
		return destinationcountryValue;
	}
	
	public void setDestinationcountryValue(String destinationcountryValue) {
		this.destinationcountryValue = destinationcountryValue;
	}
	

	public String  getDutymode() {
		return dutymode;
	}
	
	public void setDutymode(String dutymode) {
		this.dutymode = dutymode;
	}
	

	public String  getDutymodeValue() {
		return dutymodeValue;
	}
	
	public void setDutymodeValue(String dutymodeValue) {
		this.dutymodeValue = dutymodeValue;
	}
	

	public String  getExgno() {
		return exgno;
	}
	
	public void setExgno(String exgno) {
		this.exgno = exgno;
	}
	

	public String  getExgversion() {
		return exgversion;
	}
	
	public void setExgversion(String exgversion) {
		this.exgversion = exgversion;
	}
	

	public String  getExtracode() {
		return extracode;
	}
	
	public void setExtracode(String extracode) {
		this.extracode = extracode;
	}
	

	public String  getFactor() {
		return factor;
	}
	
	public void setFactor(String factor) {
		this.factor = factor;
	}
	

	public String  getFirstqty() {
		return firstqty;
	}
	
	public void setFirstqty(String firstqty) {
		this.firstqty = firstqty;
	}
	

	public String  getFirstqtyValue() {
		return firstqtyValue;
	}
	
	public void setFirstqtyValue(String firstqtyValue) {
		this.firstqtyValue = firstqtyValue;
	}
	

	public String  getFirstunit() {
		return firstunit;
	}
	
	public void setFirstunit(String firstunit) {
		this.firstunit = firstunit;
	}
	

	public String  getFirstunitValue() {
		return firstunitValue;
	}
	
	public void setFirstunitValue(String firstunitValue) {
		this.firstunitValue = firstunitValue;
	}
	

	public String  getGmodel() {
		return gmodel;
	}
	
	public void setGmodel(String gmodel) {
		this.gmodel = gmodel;
	}
	

	public String  getGmodelMood() {
		return gmodelMood;
	}
	
	public void setGmodelMood(String gmodelMood) {
		this.gmodelMood = gmodelMood;
	}
	

	public String  getGname() {
		return gname;
	}
	
	public void setGname(String gname) {
		this.gname = gname;
	}
	

	public String  getGno() {
		return gno;
	}
	
	public void setGno(String gno) {
		this.gno = gno;
	}
	

	public String  getGqty() {
		return gqty;
	}
	
	public void setGqty(String gqty) {
		this.gqty = gqty;
	}
	

	public String  getGunit() {
		return gunit;
	}
	
	public void setGunit(String gunit) {
		this.gunit = gunit;
	}
	

	public String  getGunitValue() {
		return gunitValue;
	}
	
	public void setGunitValue(String gunitValue) {
		this.gunitValue = gunitValue;
	}
	

	public String  getHeadid() {
		return headid;
	}
	
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	

	public String  getJgzj() {
		return jgzj;
	}
	
	public void setJgzj(String jgzj) {
		this.jgzj = jgzj;
	}
	

	public String  getOrigincountry() {
		return origincountry;
	}
	
	public void setOrigincountry(String origincountry) {
		this.origincountry = origincountry;
	}
	

	public String  getOrigincountryValue() {
		return origincountryValue;
	}
	
	public void setOrigincountryValue(String origincountryValue) {
		this.origincountryValue = origincountryValue;
	}
	

	public String  getSecondqty() {
		return secondqty;
	}
	
	public void setSecondqty(String secondqty) {
		this.secondqty = secondqty;
	}
	

	public String  getSecondqtyValue() {
		return secondqtyValue;
	}
	
	public void setSecondqtyValue(String secondqtyValue) {
		this.secondqtyValue = secondqtyValue;
	}
	

	public String  getSecondunit() {
		return secondunit;
	}
	
	public void setSecondunit(String secondunit) {
		this.secondunit = secondunit;
	}
	

	public String  getSecondunitValue() {
		return secondunitValue;
	}
	
	public void setSecondunitValue(String secondunitValue) {
		this.secondunitValue = secondunitValue;
	}
	

	public String  getTradecurr() {
		return tradecurr;
	}
	
	public void setTradecurr(String tradecurr) {
		this.tradecurr = tradecurr;
	}
	

	public String  getTradecurrValue() {
		return tradecurrValue;
	}
	
	public void setTradecurrValue(String tradecurrValue) {
		this.tradecurrValue = tradecurrValue;
	}
	

	public String  getUseto() {
		return useto;
	}
	
	public void setUseto(String useto) {
		this.useto = useto;
	}
	

	public String  getWorkusd() {
		return workusd;
	}
	
	public void setWorkusd(String workusd) {
		this.workusd = workusd;
	}
	

	public String  getCiqcode() {
		return ciqcode;
	}
	
	public void setCiqcode(String ciqcode) {
		this.ciqcode = ciqcode;
	}
	

	public String  getCiqname() {
		return ciqname;
	}
	
	public void setCiqname(String ciqname) {
		this.ciqname = ciqname;
	}
	

	public String  getDangname() {
		return dangname;
	}
	
	public void setDangname(String dangname) {
		this.dangname = dangname;
	}
	

	public String  getDangpackspec() {
		return dangpackspec;
	}
	
	public void setDangpackspec(String dangpackspec) {
		this.dangpackspec = dangpackspec;
	}
	

	public String  getDangpacktype() {
		return dangpacktype;
	}
	
	public void setDangpacktype(String dangpacktype) {
		this.dangpacktype = dangpacktype;
	}
	

	public String  getDangpacktypeValue() {
		return dangpacktypeValue;
	}
	
	public void setDangpacktypeValue(String dangpacktypeValue) {
		this.dangpacktypeValue = dangpacktypeValue;
	}
	

	public String  getDangerflag() {
		return dangerflag;
	}
	
	public void setDangerflag(String dangerflag) {
		this.dangerflag = dangerflag;
	}
	

	public String  getDeclgoodsename() {
		return declgoodsename;
	}
	
	public void setDeclgoodsename(String declgoodsename) {
		this.declgoodsename = declgoodsename;
	}
	

	public String  getDestcode() {
		return destcode;
	}
	
	public void setDestcode(String destcode) {
		this.destcode = destcode;
	}
	

	public String  getDestcodeValue() {
		return destcodeValue;
	}
	
	public void setDestcodeValue(String destcodeValue) {
		this.destcodeValue = destcodeValue;
	}
	

	public String  getDistrictcode() {
		return districtcode;
	}
	
	public void setDistrictcode(String districtcode) {
		this.districtcode = districtcode;
	}
	

	public String  getDistrictcodeValue() {
		return districtcodeValue;
	}
	
	public void setDistrictcodeValue(String districtcodeValue) {
		this.districtcodeValue = districtcodeValue;
	}
	

	public String  getEngmanentcnm() {
		return engmanentcnm;
	}
	
	public void setEngmanentcnm(String engmanentcnm) {
		this.engmanentcnm = engmanentcnm;
	}
	

	public String  getGoodsattr() {
		return goodsattr;
	}
	
	public void setGoodsattr(String goodsattr) {
		this.goodsattr = goodsattr;
	}
	

	public String  getGoodsattrValue() {
		return goodsattrValue;
	}
	
	public void setGoodsattrValue(String goodsattrValue) {
		this.goodsattrValue = goodsattrValue;
	}
	

	public String  getGoodsbrand() {
		return goodsbrand;
	}
	
	public void setGoodsbrand(String goodsbrand) {
		this.goodsbrand = goodsbrand;
	}
	

	public String  getGoodsmodel() {
		return goodsmodel;
	}
	
	public void setGoodsmodel(String goodsmodel) {
		this.goodsmodel = goodsmodel;
	}
	

	public String  getGoodsspec() {
		return goodsspec;
	}
	
	public void setGoodsspec(String goodsspec) {
		this.goodsspec = goodsspec;
	}
	

	public String  getJyjyggxh() {
		return jyjyggxh;
	}
	
	public void setJyjyggxh(String jyjyggxh) {
		this.jyjyggxh = jyjyggxh;
	}
	

	public String  getMnufctrregname() {
		return mnufctrregname;
	}
	
	public void setMnufctrregname(String mnufctrregname) {
		this.mnufctrregname = mnufctrregname;
	}
	

	public String  getMnufctrregno() {
		return mnufctrregno;
	}
	
	public void setMnufctrregno(String mnufctrregno) {
		this.mnufctrregno = mnufctrregno;
	}
	

	public String  getNodangflag() {
		return nodangflag;
	}
	
	public void setNodangflag(String nodangflag) {
		this.nodangflag = nodangflag;
	}
	

	public String  getNodangflagValue() {
		return nodangflagValue;
	}
	
	public void setNodangflagValue(String nodangflagValue) {
		this.nodangflagValue = nodangflagValue;
	}
	

	public String  getOrigplacecode() {
		return origplacecode;
	}
	
	public void setOrigplacecode(String origplacecode) {
		this.origplacecode = origplacecode;
	}
	

	public String  getOrigplacecodeValue() {
		return origplacecodeValue;
	}
	
	public void setOrigplacecodeValue(String origplacecodeValue) {
		this.origplacecodeValue = origplacecodeValue;
	}
	

	public String  getProdbatchno() {
		return prodbatchno;
	}
	
	public void setProdbatchno(String prodbatchno) {
		this.prodbatchno = prodbatchno;
	}
	

	public String  getProdqgp() {
		return prodqgp;
	}
	
	public void setProdqgp(String prodqgp) {
		this.prodqgp = prodqgp;
	}
	

	public String  getProdvaliddt() {
		return prodvaliddt;
	}
	
	public void setProdvaliddt(String prodvaliddt) {
		this.prodvaliddt = prodvaliddt;
	}
	

	public String  getProducedate() {
		return producedate;
	}
	
	public void setProducedate(String producedate) {
		this.producedate = producedate;
	}
	

	public String  getPurpose() {
		return purpose;
	}
	
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	

	public String  getPurposeValue() {
		return purposeValue;
	}
	
	public void setPurposeValue(String purposeValue) {
		this.purposeValue = purposeValue;
	}
	

	public String  getStuff() {
		return stuff;
	}
	
	public void setStuff(String stuff) {
		this.stuff = stuff;
	}
	

	public String  getUncode() {
		return uncode;
	}
	
	public void setUncode(String uncode) {
		this.uncode = uncode;
	}
	


}