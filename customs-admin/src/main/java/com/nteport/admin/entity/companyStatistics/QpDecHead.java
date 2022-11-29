package com.nteport.admin.entity.companyStatistics;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("QP_DECHEAD")
public class QpDecHead implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("ID")
    private String id;

    @TableField("CREATETIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @TableField("UPDATETIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;


    @TableField("SYNCTYPE")
    private String synctype;

    @TableField("CMPLDSCHRGDT")
    private String cmpldschrgdt;

    @TableField("CONSIGNEECODE")
    private String consigneecode;

    @TableField("COPLIMITSNAMES")
    private String coplimitsnames;

    @TableField("CORRELATIONNO")
    private String correlationno;

    @TableField("CORRELATIONREASONFLAG")
    private String correlationreasonflag;

    @TableField("CORRELATIONREASONFLAG_VALUE")
    private String correlationreasonflagValue;

    @TableField("DATATYPE")
    private String datatype;

    @TableField("DECLGOODSENAMES")
    private String declgoodsenames;

    @TableField("DECLREGNO")
    private String declregno;

    @TableField("DECLARENAME")
    private String declarename;

    @TableField("DESPDATE")
    private String despdate;

    @TableField("DESPPORTCODE")
    private String despportcode;

    @TableField("DESPPORTCODE_VALUE")
    private String despportcodeValue;

    @TableField("DOMESTICCONSIGNEEENAME")
    private String domesticconsigneeename;

    @TableField("EDIREMARK")
    private String ediremark;

    @TableField("EDIREMARK2")
    private String ediremark2;

    @TableField("ENTYPORTCODE")
    private String entyportcode;

    @TableField("ENTYPORTCODE_VALUE")
    private String entyportcodeValue;

    @TableField("GOODSPLACE")
    private String goodsplace;

    @TableField("INSPORGCODE")
    private String insporgcode;

    @TableField("INSPORGCODE_VALUE")
    private String insporgcodeValue;

    @TableField("MARKNO")
    private String markno;

    @TableField("NOOTHERPACK")
    private String nootherpack;

    @TableField("ORGCODE")
    private String orgcode;

    @TableField("ORGCODE_VALUE")
    private String orgcodeValue;

    @TableField("ORIGBOXFLAG")
    private String origboxflag;

    @TableField("ORIGBOXFLAG_VALUE")
    private String origboxflagValue;

    @TableField("OVERSEASCONSIGNEECODE")
    private String overseasconsigneecode;

    @TableField("OVERSEASCONSIGNEEENAME")
    private String overseasconsigneeename;

    @TableField("OVERSEASCONSIGNORADDR")
    private String overseasconsignoraddr;

    @TableField("OVERSEASCONSIGNORCNAME")
    private String overseasconsignorcname;

    @TableField("OVERSEASCONSIGNORCODE")
    private String overseasconsignorcode;

    @TableField("OVERSEASCONSIGNORENAME")
    private String overseasconsignorename;

    @TableField("OWNERCIQCODE")
    private String ownerciqcode;

    @TableField("PARTENERID")
    private String partenerid;

    @TableField("PDATE")
    private String pdate;

    @TableField("PURPORGCODE")
    private String purporgcode;

    @TableField("PURPORGCODE_VALUE")
    private String purporgcodeValue;

    @TableField("SPECDECLFLAG")
    private String specdeclflag;

    @TableField("SPECDECLFLAG_VALUE")
    private String specdeclflagValue;

    @TableField("SXDZ")
    private String sxdz;

    @TableField("TAXAAMINMARK")
    private String taxaaminmark;

    @TableField("VIEWMODEL")
    private String viewmodel;

    @TableField("VSAORGCODE")
    private String vsaorgcode;

    @TableField("VSAORGCODE_VALUE")
    private String vsaorgcodeValue;

    @TableField("NOSYNCMARK")
    private String nosyncmark;

    @TableField("SPDECSEQNO")
    private String spdecseqno;

    @TableField("CUSTOMSTATENAME")
    private String customstatename;

    @TableField("CUTMODE")
    private String cutmode;

    @TableField("CUTMODE_VALUE")
    private String cutmodeValue;

    @TableField("DATASOURCE")
    private String datasource;

    @TableField("DECCONTAINERNUM")
    private String deccontainernum;

    @TableField("DECLTRNREL")
    private String decltrnrel;

    @TableField("DECLTRNREL_VALUE")
    private String decltrnrelValue;

    @TableField("DECLARATIONDATA")
    private String declarationdata;

    @TableField("DISTINATEPORT")
    private String distinateport;

    @TableField("DISTINATEPORT_VALUE")
    private String distinateportValue;

    @TableField("DISTRICTCODE")
    private String districtcode;

    @TableField("DISTRICTCODE_VALUE")
    private String districtcodeValue;

    @TableField("EDIID")
    private String ediid;

    @TableField("ENTRYID")
    private String entryid;

    @TableField("ENTRYTYPE")
    private String entrytype;

    @TableField("ENTRYTYPE_VALUE")
    private String entrytypeValue;

    @TableField("FEECURR")
    private String feecurr;

    @TableField("FEECURR_VALUE")
    private String feecurrValue;

    @TableField("FEEMARK")
    private String feemark;

    @TableField("FEEMARK_VALUE")
    private String feemarkValue;

    @TableField("FEERATE")
    private String feerate;

    @TableField("GROSSWET")
    private String grosswet;

    @TableField("IEDATE")
    private String iedate;

    @TableField("IEFLAG")
    private String ieflag;

    @TableField("IEPORT")
    private String ieport;

    @TableField("IEPORT_VALUE")
    private String ieportValue;

    @TableField("INRATIO")
    private String inratio;

    @TableField("INPUTERNAME")
    private String inputername;

    @TableField("INSURCURR")
    private String insurcurr;

    @TableField("INSURCURR_VALUE")
    private String insurcurrValue;

    @TableField("INSURMARK")
    private String insurmark;

    @TableField("INSURMARK_VALUE")
    private String insurmarkValue;

    @TableField("INSURRATE")
    private String insurrate;

    @TableField("ISDELETE")
    private String isdelete;

    @TableField("LICENSENO")
    private String licenseno;

    @TableField("MANUALNO")
    private String manualno;

    @TableField("NETWT")
    private String netwt;

    @TableField("NOTES")
    private String notes;

    @TableField("ODTTYPE")
    private String odttype;

    @TableField("ODTTYPE_VALUE")
    private String odttypeValue;

    @TableField("OPERATECODE")
    private String operatecode;

    @TableField("OPERATECODESCC")
    private String operatecodescc;

    @TableField("OPERATENAME")
    private String operatename;

    @TableField("OPERATERUSERID")
    private String operateruserid;

    @TableField("OPERATERUSERNAME")
    private String operaterusername;

    @TableField("ORGID")
    private String orgid;

    @TableField("ORGNAME")
    private String orgname;

    @TableField("OTHERCURR")
    private String othercurr;

    @TableField("OTHERCURR_VALUE")
    private String othercurrValue;

    @TableField("OTHERMARK")
    private String othermark;

    @TableField("OTHERMARK_VALUE")
    private String othermarkValue;

    @TableField("OTHERRATE")
    private String otherrate;

    @TableField("OWNERCODE")
    private String ownercode;

    @TableField("OWNERCODESCC")
    private String ownercodescc;

    @TableField("OWNERNAME")
    private String ownername;

    @TableField("PACKNO")
    private String packno;

    @TableField("PAYWAY")
    private String payway;

    @TableField("PAYWAY_VALUE")
    private String paywayValue;

    @TableField("PAYMENTMARK")
    private String paymentmark;

    @TableField("PAYMENTMARK_VALUE")
    private String paymentmarkValue;

    @TableField("PREENTRYID")
    private String preentryid;

    @TableField("PRINTDATE")
    private String printdate;

    @TableField("PROMISEITMES")
    private String promiseitmes;

    @TableField("RISK")
    private String risk;

    @TableField("SENDROATE")
    private String sendroate;

    @TableField("SENDSTATE")
    private String sendstate;

    @TableField("SENDTIME")
    private String sendtime;

    @TableField("SEQNO")
    private String seqno;

    @TableField("SFDZ")
    private String sfdz;

    @TableField("TGDNO")
    private String tgdno;

    @TableField("TRADEAREACODE")
    private String tradeareacode;

    @TableField("TRADEAREACODE_VALUE")
    private String tradeareacodeValue;

    @TableField("TRADECODE")
    private String tradecode;

    @TableField("TRADECODESCC")
    private String tradecodescc;

    @TableField("TRADECOUNTRY")
    private String tradecountry;

    @TableField("TRADECOUNTRY_VALUE")
    private String tradecountryValue;

    @TableField("TRADEMODE")
    private String trademode;

    @TableField("TRADEMODE_VALUE")
    private String trademodeValue;

    @TableField("TRADENAME")
    private String tradename;

    @TableField("TRAFMODE")
    private String trafmode;

    @TableField("TRAFMODE_VALUE")
    private String trafmodeValue;

    @TableField("TRAFNAME")
    private String trafname;

    @TableField("TRANSMODE")
    private String transmode;

    @TableField("TRANSMODE_VALUE")
    private String transmodeValue;

    @TableField("TYPE")
    private String type;

    @TableField("TYPE_VALUE")
    private String typeValue;

    @TableField("TYPISTNO")
    private String typistno;

    @TableField("USERID")
    private String userid;

    @TableField("USERNAME")
    private String username;

    @TableField("VOYNO")
    private String voyno;

    @TableField("WRAPTYPE")
    private String wraptype;

    @TableField("WRAPTYPE_VALUE")
    private String wraptypeValue;

    @TableField("DECFREETXTID")
    private String decfreetxtid;

    @TableField("TRNHEADID")
    private String trnheadid;

    @TableField("BLNO")
    private String blno;

    @TableField("CHECKFLOW")
    private String checkflow;

    @TableField("CIQDECSTATUS")
    private String ciqdecstatus;

    @TableField("CIQDECSTATUSNAME")
    private String ciqdecstatusname;

    @TableField("CREATETIME")
    private String createtime;

    @TableField("UPDATETIME")
    private String updatetime;

    @TableField("AGENTCODE")
    private String agentcode;

    @TableField("AGENTCODESCC")
    private String agentcodescc;

    @TableField("AGENTNAME")
    private String agentname;

    @TableField("APPRNO")
    private String apprno;

    @TableField("BILLNO")
    private String billno;

    @TableField("BILLTYPE")
    private String billtype;

    @TableField("BILLTYPE_VALUE")
    private String billtypeValue;

    @TableField("BUSS_SW")
    private String bussSw;

    @TableField("BUSS_SYZZ")
    private String bussSyzz;

    @TableField("BUSS_ZB")
    private String bussZb;

    @TableField("BUSS_ZBZJ")
    private String bussZbzj;

    @TableField("CHKSHUNT")
    private String chkshunt;

    @TableField("CHKSURETY")
    private String chksurety;

    @TableField("CHKSURETY_VALUE")
    private String chksuretyValue;

    @TableField("CLIENTNO")
    private String clientno;

    @TableField("COMPANY_TYPE")
    private String companyType;

    @TableField("CONTRNO")
    private String contrno;

    @TableField("COPCODE")
    private String copcode;

    @TableField("COPCODESCC")
    private String copcodescc;

    @TableField("COPNAME")
    private String copname;

    @TableField("CUSTYPE")
    private String custype;

    @TableField("CUSTOMMASTER")
    private String custommaster;

    @TableField("CUSTOMMASTER_VALUE")
    private String custommasterValue;

    @TableField("CUSTOMSTATE")
    private String customstate;

}
