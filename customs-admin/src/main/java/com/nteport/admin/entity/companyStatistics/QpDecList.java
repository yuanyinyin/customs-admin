package com.nteport.admin.entity.companyStatistics;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("QP_DECLIST")
public class QpDecList implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("ID")
    private String id;

    @TableField("CREATETIME")
    private Date createTime;

    @TableField("UPDATETIME")
    private Date updateTime;


    @TableField("ENGMANENTCNM")
    private String engmanentcnm;

    @TableField("GOODSATTR")
    private String goodsattr;

    @TableField("GOODSATTR_VALUE")
    private String goodsattrValue;

    @TableField("GOODSBRAND")
    private String goodsbrand;

    @TableField("GOODSMODEL")
    private String goodsmodel;

    @TableField("GOODSSPEC")
    private String goodsspec;

    @TableField("JYJYGGXH")
    private String jyjyggxh;

    @TableField("MNUFCTRREGNAME")
    private String mnufctrregname;

    @TableField("MNUFCTRREGNO")
    private String mnufctrregno;

    @TableField("NODANGFLAG")
    private String nodangflag;

    @TableField("NODANGFLAG_VALUE")
    private String nodangflagValue;

    @TableField("ORIGPLACECODE")
    private String origplacecode;

    @TableField("ORIGPLACECODE_VALUE")
    private String origplacecodeValue;

    @TableField("PRODBATCHNO")
    private String prodbatchno;

    @TableField("PRODQGP")
    private String prodqgp;

    @TableField("PRODVALIDDT")
    private String prodvaliddt;

    @TableField("PRODUCEDATE")
    private String producedate;

    @TableField("PURPOSE")
    private String purpose;

    @TableField("PURPOSE_VALUE")
    private String purposeValue;

    @TableField("STUFF")
    private String stuff;

    @TableField("UNCODE")
    private String uncode;

    @TableField("TRADECURR_VALUE")
    private String tradecurrValue;

    @TableField("USETO")
    private String useto;

    @TableField("WORKUSD")
    private String workusd;

    @TableField("CIQCODE")
    private String ciqcode;

    @TableField("CIQNAME")
    private String ciqname;

    @TableField("DANGNAME")
    private String dangname;

    @TableField("DANGPACKSPEC")
    private String dangpackspec;

    @TableField("DANGPACKTYPE")
    private String dangpacktype;

    @TableField("DANGPACKTYPE_VALUE")
    private String dangpacktypeValue;

    @TableField("DANGERFLAG")
    private String dangerflag;

    @TableField("DECLGOODSENAME")
    private String declgoodsename;

    @TableField("DESTCODE")
    private String destcode;

    @TableField("DESTCODE_VALUE")
    private String destcodeValue;

    @TableField("DISTRICTCODE")
    private String districtcode;

    @TableField("DISTRICTCODE_VALUE")
    private String districtcodeValue;

    @TableField("CREATETIME")
    private String createtime;

    @TableField("UPDATETIME")
    private String updatetime;

    @TableField("AGREEMENTID")
    private String agreementid;

    @TableField("AGREEMENTID_VALUE")
    private String agreementidValue;

    @TableField("CLASSMARK")
    private String classmark;

    @TableField("CODETS")
    private String codets;

    @TableField("CODETS_VALUE")
    private String codetsValue;

    @TableField("CONTRITEM")
    private String contritem;

    @TableField("DECLPRICE")
    private String declprice;

    @TableField("DECLTOTAL")
    private String decltotal;

    @TableField("DESTINATIONCOUNTRY")
    private String destinationcountry;

    @TableField("DESTINATIONCOUNTRY_VALUE")
    private String destinationcountryValue;

    @TableField("DUTYMODE")
    private String dutymode;

    @TableField("DUTYMODE_VALUE")
    private String dutymodeValue;

    @TableField("EXGNO")
    private String exgno;

    @TableField("EXGVERSION")
    private String exgversion;

    @TableField("EXTRACODE")
    private String extracode;

    @TableField("FACTOR")
    private String factor;

    @TableField("FIRSTQTY")
    private String firstqty;

    @TableField("FIRSTQTY_VALUE")
    private String firstqtyValue;

    @TableField("FIRSTUNIT")
    private String firstunit;

    @TableField("FIRSTUNIT_VALUE")
    private String firstunitValue;

    @TableField("GMODEL")
    private String gmodel;

    @TableField("GMODEL_MOOD")
    private String gmodelMood;

    @TableField("GNAME")
    private String gname;

    @TableField("GNO")
    private String gno;

    @TableField("GQTY")
    private String gqty;

    @TableField("GUNIT")
    private String gunit;

    @TableField("GUNIT_VALUE")
    private String gunitValue;

    @TableField("HEADID")
    private String headid;

    @TableField("JGZJ")
    private String jgzj;

    @TableField("ORIGINCOUNTRY")
    private String origincountry;

    @TableField("ORIGINCOUNTRY_VALUE")
    private String origincountryValue;

    @TableField("SECONDQTY")
    private String secondqty;

    @TableField("SECONDQTY_VALUE")
    private String secondqtyValue;

    @TableField("SECONDUNIT")
    private String secondunit;

    @TableField("SECONDUNIT_VALUE")
    private String secondunitValue;

    @TableField("TRADECURR")
    private String tradecurr;

    @TableField("CURRENTRATE")
    private String currentRate;

}
