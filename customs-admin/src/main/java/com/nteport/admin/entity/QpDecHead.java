package com.nteport.admin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 表头
 */
@TableName(value = "NSW.QP_DECHEAD")
//@Data
public class QpDecHead  implements Serializable {

        @TableId(type = IdType.ASSIGN_UUID)
        private String id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField("CREATETIME")

    private LocalDateTime createTime;

//    private Date createTime;


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

    private String tradecountryValue;


        private String trademode;


        private String trademodeValue;


        private String tradename;


        private String trafmode;


        private String trafmodeValue;


        private String trafname;


        private String transmode;


        private String transmodeValue;


        private String type;


        private String typeValue;


        private String typistno;


        private String userid;


        private String username;


        private String voyno;


        private String wraptype;


        private String wraptypeValue;


        private String decfreetxtid;


        private String trnheadid;


        private String blno;


        private String checkflow;


        private String ciqdecstatus;


        private String ciqdecstatusname;


        private String cmpldschrgdt;


        private String consigneecode;


        private String coplimitsnames;


        private String correlationno;


        private String correlationreasonflag;


        private String correlationreasonflagValue;


        private String datatype;


        private String declgoodsenames;


        private String declregno;


        private String declarename;


        private String despdate;


        private String despportcode;


        private String despportcodeValue;


        private String domesticconsigneeename;


        private String ediremark;


        private String ediremark2;


        private String entyportcode;


        private String entyportcodeValue;


        private String goodsplace;


        private String insporgcode;


        private String insporgcodeValue;


        private String markno;


        private String nootherpack;


        private String orgcode;


        private String orgcodeValue;


        private String origboxflag;


        private String origboxflagValue;


        private String overseasconsigneecode;


        private String overseasconsigneeename;


        private String overseasconsignoraddr;


        private String overseasconsignorcname;


        private String overseasconsignorcode;


        private String overseasconsignorename;


        private String ownerciqcode;


        private String partenerid;


        private String pdate;


        private String purporgcode;


        private String purporgcodeValue;


        private String specdeclflag;


        private String specdeclflagValue;


        private String sxdz;


        private String taxaaminmark;


        private String viewmodel;


        private String vsaorgcode;


        private String vsaorgcodeValue;


        private String nosyncmark;


        private String spdecseqno;


        private String synctype;


        private String agentcode;


        private String agentcodescc;


        private String agentname;


        private String apprno;


        private String billno;


        private String billtype;


        private String billtypeValue;


        private String bussSw;


        private String bussSyzz;


        private String bussZb;


        private String bussZbzj;


        private String chkshunt;


        private String chksurety;


        private String chksuretyValue;


        private String clientno;


        private String companyType;


        private String contrno;


        private String copcode;


        private String copcodescc;


        private String copname;


        private String custype;


        private String custommaster;


        private String custommasterValue;


        private String customstate;


        private String customstatename;


        private String cutmode;


        private String cutmodeValue;


        private String datasource;


        private String deccontainernum;


        private String decltrnrel;


        private String decltrnrelValue;


        private String declarationdata;


        private String distinateport;


        private String distinateportValue;


        private String districtcode;


        private String districtcodeValue;


        private String ediid;


        private String entryid;


        private String entrytype;


        private String entrytypeValue;


        private String feecurr;


        private String feecurrValue;


        private String feemark;


        private String feemarkValue;


        private String feerate;


        private String grosswet;


        private String iedate;


        private String ieflag;


        private String ieport;


        private String ieportValue;


        private String inratio;


        private String inputername;


        private String insurcurr;


        private String insurcurrValue;


        private String insurmark;


        private String insurmarkValue;


        private String insurrate;


        private String isdelete;


        private String licenseno;


        private String manualno;


        private String netwt;


        private String notes;


        private String odttype;


        private String odttypeValue;


        private String operatecode;


        private String operatecodescc;


        private String operatename;


        private String operateruserid;


        private String operaterusername;


        private String orgid;


        private String orgname;


        private String othercurr;


        private String othercurrValue;


        private String othermark;


        private String othermarkValue;


        private String otherrate;


        private String ownercode;


        private String ownercodescc;


        private String ownername;


        private String packno;


        private String payway;


        private String paywayValue;


        private String paymentmark;


        private String paymentmarkValue;


        private String preentryid;


        private String printdate;


        private String promiseitmes;


        private String risk;


        private String sendroate;


        private String sendstate;


        private String sendtime;


        private String seqno;


        private String sfdz;


        private String tgdno;


        private String tradeareacode;


        private String tradeareacodeValue;


        private String tradecode;


        private String tradecodescc;


        private String tradecountry;






        public String  getTradecountryValue() {
            return tradecountryValue;
        }

        public void setTradecountryValue(String tradecountryValue) {
            this.tradecountryValue = tradecountryValue;
        }


        public String  getTrademode() {
            return trademode;
        }

        public void setTrademode(String trademode) {
            this.trademode = trademode;
        }


        public String  getTrademodeValue() {
            return trademodeValue;
        }

        public void setTrademodeValue(String trademodeValue) {
            this.trademodeValue = trademodeValue;
        }


        public String  getTradename() {
            return tradename;
        }

        public void setTradename(String tradename) {
            this.tradename = tradename;
        }


        public String  getTrafmode() {
            return trafmode;
        }

        public void setTrafmode(String trafmode) {
            this.trafmode = trafmode;
        }


        public String  getTrafmodeValue() {
            return trafmodeValue;
        }

        public void setTrafmodeValue(String trafmodeValue) {
            this.trafmodeValue = trafmodeValue;
        }


        public String  getTrafname() {
            return trafname;
        }

        public void setTrafname(String trafname) {
            this.trafname = trafname;
        }


        public String  getTransmode() {
            return transmode;
        }

        public void setTransmode(String transmode) {
            this.transmode = transmode;
        }


        public String  getTransmodeValue() {
            return transmodeValue;
        }

        public void setTransmodeValue(String transmodeValue) {
            this.transmodeValue = transmodeValue;
        }


        public String  getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public String  getTypeValue() {
            return typeValue;
        }

        public void setTypeValue(String typeValue) {
            this.typeValue = typeValue;
        }


        public String  getTypistno() {
            return typistno;
        }

        public void setTypistno(String typistno) {
            this.typistno = typistno;
        }


        public String  getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }


        public String  getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }


        public String  getVoyno() {
            return voyno;
        }

        public void setVoyno(String voyno) {
            this.voyno = voyno;
        }


        public String  getWraptype() {
            return wraptype;
        }

        public void setWraptype(String wraptype) {
            this.wraptype = wraptype;
        }


        public String  getWraptypeValue() {
            return wraptypeValue;
        }

        public void setWraptypeValue(String wraptypeValue) {
            this.wraptypeValue = wraptypeValue;
        }


        public String  getDecfreetxtid() {
            return decfreetxtid;
        }

        public void setDecfreetxtid(String decfreetxtid) {
            this.decfreetxtid = decfreetxtid;
        }


        public String  getTrnheadid() {
            return trnheadid;
        }

        public void setTrnheadid(String trnheadid) {
            this.trnheadid = trnheadid;
        }


        public String  getBlno() {
            return blno;
        }

        public void setBlno(String blno) {
            this.blno = blno;
        }


        public String  getCheckflow() {
            return checkflow;
        }

        public void setCheckflow(String checkflow) {
            this.checkflow = checkflow;
        }


        public String  getCiqdecstatus() {
            return ciqdecstatus;
        }

        public void setCiqdecstatus(String ciqdecstatus) {
            this.ciqdecstatus = ciqdecstatus;
        }


        public String  getCiqdecstatusname() {
            return ciqdecstatusname;
        }

        public void setCiqdecstatusname(String ciqdecstatusname) {
            this.ciqdecstatusname = ciqdecstatusname;
        }


        public String  getCmpldschrgdt() {
            return cmpldschrgdt;
        }

        public void setCmpldschrgdt(String cmpldschrgdt) {
            this.cmpldschrgdt = cmpldschrgdt;
        }


        public String  getConsigneecode() {
            return consigneecode;
        }

        public void setConsigneecode(String consigneecode) {
            this.consigneecode = consigneecode;
        }


        public String  getCoplimitsnames() {
            return coplimitsnames;
        }

        public void setCoplimitsnames(String coplimitsnames) {
            this.coplimitsnames = coplimitsnames;
        }


        public String  getCorrelationno() {
            return correlationno;
        }

        public void setCorrelationno(String correlationno) {
            this.correlationno = correlationno;
        }


        public String  getCorrelationreasonflag() {
            return correlationreasonflag;
        }

        public void setCorrelationreasonflag(String correlationreasonflag) {
            this.correlationreasonflag = correlationreasonflag;
        }


        public String  getCorrelationreasonflagValue() {
            return correlationreasonflagValue;
        }

        public void setCorrelationreasonflagValue(String correlationreasonflagValue) {
            this.correlationreasonflagValue = correlationreasonflagValue;
        }


        public String  getDatatype() {
            return datatype;
        }

        public void setDatatype(String datatype) {
            this.datatype = datatype;
        }


        public String  getDeclgoodsenames() {
            return declgoodsenames;
        }

        public void setDeclgoodsenames(String declgoodsenames) {
            this.declgoodsenames = declgoodsenames;
        }


        public String  getDeclregno() {
            return declregno;
        }

        public void setDeclregno(String declregno) {
            this.declregno = declregno;
        }


        public String  getDeclarename() {
            return declarename;
        }

        public void setDeclarename(String declarename) {
            this.declarename = declarename;
        }


        public String  getDespdate() {
            return despdate;
        }

        public void setDespdate(String despdate) {
            this.despdate = despdate;
        }


        public String  getDespportcode() {
            return despportcode;
        }

        public void setDespportcode(String despportcode) {
            this.despportcode = despportcode;
        }


        public String  getDespportcodeValue() {
            return despportcodeValue;
        }

        public void setDespportcodeValue(String despportcodeValue) {
            this.despportcodeValue = despportcodeValue;
        }


        public String  getDomesticconsigneeename() {
            return domesticconsigneeename;
        }

        public void setDomesticconsigneeename(String domesticconsigneeename) {
            this.domesticconsigneeename = domesticconsigneeename;
        }


        public String  getEdiremark() {
            return ediremark;
        }

        public void setEdiremark(String ediremark) {
            this.ediremark = ediremark;
        }


        public String  getEdiremark2() {
            return ediremark2;
        }

        public void setEdiremark2(String ediremark2) {
            this.ediremark2 = ediremark2;
        }


        public String  getEntyportcode() {
            return entyportcode;
        }

        public void setEntyportcode(String entyportcode) {
            this.entyportcode = entyportcode;
        }


        public String  getEntyportcodeValue() {
            return entyportcodeValue;
        }

        public void setEntyportcodeValue(String entyportcodeValue) {
            this.entyportcodeValue = entyportcodeValue;
        }


        public String  getGoodsplace() {
            return goodsplace;
        }

        public void setGoodsplace(String goodsplace) {
            this.goodsplace = goodsplace;
        }


        public String  getInsporgcode() {
            return insporgcode;
        }

        public void setInsporgcode(String insporgcode) {
            this.insporgcode = insporgcode;
        }


        public String  getInsporgcodeValue() {
            return insporgcodeValue;
        }

        public void setInsporgcodeValue(String insporgcodeValue) {
            this.insporgcodeValue = insporgcodeValue;
        }


        public String  getMarkno() {
            return markno;
        }

        public void setMarkno(String markno) {
            this.markno = markno;
        }


        public String  getNootherpack() {
            return nootherpack;
        }

        public void setNootherpack(String nootherpack) {
            this.nootherpack = nootherpack;
        }


        public String  getOrgcode() {
            return orgcode;
        }

        public void setOrgcode(String orgcode) {
            this.orgcode = orgcode;
        }


        public String  getOrgcodeValue() {
            return orgcodeValue;
        }

        public void setOrgcodeValue(String orgcodeValue) {
            this.orgcodeValue = orgcodeValue;
        }


        public String  getOrigboxflag() {
            return origboxflag;
        }

        public void setOrigboxflag(String origboxflag) {
            this.origboxflag = origboxflag;
        }


        public String  getOrigboxflagValue() {
            return origboxflagValue;
        }

        public void setOrigboxflagValue(String origboxflagValue) {
            this.origboxflagValue = origboxflagValue;
        }


        public String  getOverseasconsigneecode() {
            return overseasconsigneecode;
        }

        public void setOverseasconsigneecode(String overseasconsigneecode) {
            this.overseasconsigneecode = overseasconsigneecode;
        }


        public String  getOverseasconsigneeename() {
            return overseasconsigneeename;
        }

        public void setOverseasconsigneeename(String overseasconsigneeename) {
            this.overseasconsigneeename = overseasconsigneeename;
        }


        public String  getOverseasconsignoraddr() {
            return overseasconsignoraddr;
        }

        public void setOverseasconsignoraddr(String overseasconsignoraddr) {
            this.overseasconsignoraddr = overseasconsignoraddr;
        }


        public String  getOverseasconsignorcname() {
            return overseasconsignorcname;
        }

        public void setOverseasconsignorcname(String overseasconsignorcname) {
            this.overseasconsignorcname = overseasconsignorcname;
        }


        public String  getOverseasconsignorcode() {
            return overseasconsignorcode;
        }

        public void setOverseasconsignorcode(String overseasconsignorcode) {
            this.overseasconsignorcode = overseasconsignorcode;
        }


        public String  getOverseasconsignorename() {
            return overseasconsignorename;
        }

        public void setOverseasconsignorename(String overseasconsignorename) {
            this.overseasconsignorename = overseasconsignorename;
        }


        public String  getOwnerciqcode() {
            return ownerciqcode;
        }

        public void setOwnerciqcode(String ownerciqcode) {
            this.ownerciqcode = ownerciqcode;
        }


        public String  getPartenerid() {
            return partenerid;
        }

        public void setPartenerid(String partenerid) {
            this.partenerid = partenerid;
        }


        public String  getPdate() {
            return pdate;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }


        public String  getPurporgcode() {
            return purporgcode;
        }

        public void setPurporgcode(String purporgcode) {
            this.purporgcode = purporgcode;
        }


        public String  getPurporgcodeValue() {
            return purporgcodeValue;
        }

        public void setPurporgcodeValue(String purporgcodeValue) {
            this.purporgcodeValue = purporgcodeValue;
        }


        public String  getSpecdeclflag() {
            return specdeclflag;
        }

        public void setSpecdeclflag(String specdeclflag) {
            this.specdeclflag = specdeclflag;
        }


        public String  getSpecdeclflagValue() {
            return specdeclflagValue;
        }

        public void setSpecdeclflagValue(String specdeclflagValue) {
            this.specdeclflagValue = specdeclflagValue;
        }


        public String  getSxdz() {
            return sxdz;
        }

        public void setSxdz(String sxdz) {
            this.sxdz = sxdz;
        }


        public String  getTaxaaminmark() {
            return taxaaminmark;
        }

        public void setTaxaaminmark(String taxaaminmark) {
            this.taxaaminmark = taxaaminmark;
        }


        public String  getViewmodel() {
            return viewmodel;
        }

        public void setViewmodel(String viewmodel) {
            this.viewmodel = viewmodel;
        }


        public String  getVsaorgcode() {
            return vsaorgcode;
        }

        public void setVsaorgcode(String vsaorgcode) {
            this.vsaorgcode = vsaorgcode;
        }


        public String  getVsaorgcodeValue() {
            return vsaorgcodeValue;
        }

        public void setVsaorgcodeValue(String vsaorgcodeValue) {
            this.vsaorgcodeValue = vsaorgcodeValue;
        }


        public String  getNosyncmark() {
            return nosyncmark;
        }

        public void setNosyncmark(String nosyncmark) {
            this.nosyncmark = nosyncmark;
        }


        public String  getSpdecseqno() {
            return spdecseqno;
        }

        public void setSpdecseqno(String spdecseqno) {
            this.spdecseqno = spdecseqno;
        }


        public String  getSynctype() {
            return synctype;
        }

        public void setSynctype(String synctype) {
            this.synctype = synctype;
        }


        public String  getAgentcode() {
            return agentcode;
        }

        public void setAgentcode(String agentcode) {
            this.agentcode = agentcode;
        }


        public String  getAgentcodescc() {
            return agentcodescc;
        }

        public void setAgentcodescc(String agentcodescc) {
            this.agentcodescc = agentcodescc;
        }


        public String  getAgentname() {
            return agentname;
        }

        public void setAgentname(String agentname) {
            this.agentname = agentname;
        }


        public String  getApprno() {
            return apprno;
        }

        public void setApprno(String apprno) {
            this.apprno = apprno;
        }


        public String  getBillno() {
            return billno;
        }

        public void setBillno(String billno) {
            this.billno = billno;
        }


        public String  getBilltype() {
            return billtype;
        }

        public void setBilltype(String billtype) {
            this.billtype = billtype;
        }


        public String  getBilltypeValue() {
            return billtypeValue;
        }

        public void setBilltypeValue(String billtypeValue) {
            this.billtypeValue = billtypeValue;
        }


        public String  getBussSw() {
            return bussSw;
        }

        public void setBussSw(String bussSw) {
            this.bussSw = bussSw;
        }


        public String  getBussSyzz() {
            return bussSyzz;
        }

        public void setBussSyzz(String bussSyzz) {
            this.bussSyzz = bussSyzz;
        }


        public String  getBussZb() {
            return bussZb;
        }

        public void setBussZb(String bussZb) {
            this.bussZb = bussZb;
        }


        public String  getBussZbzj() {
            return bussZbzj;
        }

        public void setBussZbzj(String bussZbzj) {
            this.bussZbzj = bussZbzj;
        }


        public String  getChkshunt() {
            return chkshunt;
        }

        public void setChkshunt(String chkshunt) {
            this.chkshunt = chkshunt;
        }


        public String  getChksurety() {
            return chksurety;
        }

        public void setChksurety(String chksurety) {
            this.chksurety = chksurety;
        }


        public String  getChksuretyValue() {
            return chksuretyValue;
        }

        public void setChksuretyValue(String chksuretyValue) {
            this.chksuretyValue = chksuretyValue;
        }


        public String  getClientno() {
            return clientno;
        }

        public void setClientno(String clientno) {
            this.clientno = clientno;
        }


        public String  getCompanyType() {
            return companyType;
        }

        public void setCompanyType(String companyType) {
            this.companyType = companyType;
        }


        public String  getContrno() {
            return contrno;
        }

        public void setContrno(String contrno) {
            this.contrno = contrno;
        }


        public String  getCopcode() {
            return copcode;
        }

        public void setCopcode(String copcode) {
            this.copcode = copcode;
        }


        public String  getCopcodescc() {
            return copcodescc;
        }

        public void setCopcodescc(String copcodescc) {
            this.copcodescc = copcodescc;
        }


        public String  getCopname() {
            return copname;
        }

        public void setCopname(String copname) {
            this.copname = copname;
        }


        public String  getCustype() {
            return custype;
        }

        public void setCustype(String custype) {
            this.custype = custype;
        }


        public String  getCustommaster() {
            return custommaster;
        }

        public void setCustommaster(String custommaster) {
            this.custommaster = custommaster;
        }


        public String  getCustommasterValue() {
            return custommasterValue;
        }

        public void setCustommasterValue(String custommasterValue) {
            this.custommasterValue = custommasterValue;
        }


        public String  getCustomstate() {
            return customstate;
        }

        public void setCustomstate(String customstate) {
            this.customstate = customstate;
        }


        public String  getCustomstatename() {
            return customstatename;
        }

        public void setCustomstatename(String customstatename) {
            this.customstatename = customstatename;
        }


        public String  getCutmode() {
            return cutmode;
        }

        public void setCutmode(String cutmode) {
            this.cutmode = cutmode;
        }


        public String  getCutmodeValue() {
            return cutmodeValue;
        }

        public void setCutmodeValue(String cutmodeValue) {
            this.cutmodeValue = cutmodeValue;
        }


        public String  getDatasource() {
            return datasource;
        }

        public void setDatasource(String datasource) {
            this.datasource = datasource;
        }


        public String  getDeccontainernum() {
            return deccontainernum;
        }

        public void setDeccontainernum(String deccontainernum) {
            this.deccontainernum = deccontainernum;
        }


        public String  getDecltrnrel() {
            return decltrnrel;
        }

        public void setDecltrnrel(String decltrnrel) {
            this.decltrnrel = decltrnrel;
        }


        public String  getDecltrnrelValue() {
            return decltrnrelValue;
        }

        public void setDecltrnrelValue(String decltrnrelValue) {
            this.decltrnrelValue = decltrnrelValue;
        }


        public String  getDeclarationdata() {
            return declarationdata;
        }

        public void setDeclarationdata(String declarationdata) {
            this.declarationdata = declarationdata;
        }


        public String  getDistinateport() {
            return distinateport;
        }

        public void setDistinateport(String distinateport) {
            this.distinateport = distinateport;
        }


        public String  getDistinateportValue() {
            return distinateportValue;
        }

        public void setDistinateportValue(String distinateportValue) {
            this.distinateportValue = distinateportValue;
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


        public String  getEdiid() {
            return ediid;
        }

        public void setEdiid(String ediid) {
            this.ediid = ediid;
        }


        public String  getEntryid() {
            return entryid;
        }

        public void setEntryid(String entryid) {
            this.entryid = entryid;
        }


        public String  getEntrytype() {
            return entrytype;
        }

        public void setEntrytype(String entrytype) {
            this.entrytype = entrytype;
        }


        public String  getEntrytypeValue() {
            return entrytypeValue;
        }

        public void setEntrytypeValue(String entrytypeValue) {
            this.entrytypeValue = entrytypeValue;
        }


        public String  getFeecurr() {
            return feecurr;
        }

        public void setFeecurr(String feecurr) {
            this.feecurr = feecurr;
        }


        public String  getFeecurrValue() {
            return feecurrValue;
        }

        public void setFeecurrValue(String feecurrValue) {
            this.feecurrValue = feecurrValue;
        }


        public String  getFeemark() {
            return feemark;
        }

        public void setFeemark(String feemark) {
            this.feemark = feemark;
        }


        public String  getFeemarkValue() {
            return feemarkValue;
        }

        public void setFeemarkValue(String feemarkValue) {
            this.feemarkValue = feemarkValue;
        }


        public String  getFeerate() {
            return feerate;
        }

        public void setFeerate(String feerate) {
            this.feerate = feerate;
        }


        public String  getGrosswet() {
            return grosswet;
        }

        public void setGrosswet(String grosswet) {
            this.grosswet = grosswet;
        }


        public String  getIedate() {
            return iedate;
        }

        public void setIedate(String iedate) {
            this.iedate = iedate;
        }


        public String  getIeflag() {
            return ieflag;
        }

        public void setIeflag(String ieflag) {
            this.ieflag = ieflag;
        }


        public String  getIeport() {
            return ieport;
        }

        public void setIeport(String ieport) {
            this.ieport = ieport;
        }


        public String  getIeportValue() {
            return ieportValue;
        }

        public void setIeportValue(String ieportValue) {
            this.ieportValue = ieportValue;
        }


        public String  getInratio() {
            return inratio;
        }

        public void setInratio(String inratio) {
            this.inratio = inratio;
        }


        public String  getInputername() {
            return inputername;
        }

        public void setInputername(String inputername) {
            this.inputername = inputername;
        }


        public String  getInsurcurr() {
            return insurcurr;
        }

        public void setInsurcurr(String insurcurr) {
            this.insurcurr = insurcurr;
        }


        public String  getInsurcurrValue() {
            return insurcurrValue;
        }

        public void setInsurcurrValue(String insurcurrValue) {
            this.insurcurrValue = insurcurrValue;
        }


        public String  getInsurmark() {
            return insurmark;
        }

        public void setInsurmark(String insurmark) {
            this.insurmark = insurmark;
        }


        public String  getInsurmarkValue() {
            return insurmarkValue;
        }

        public void setInsurmarkValue(String insurmarkValue) {
            this.insurmarkValue = insurmarkValue;
        }


        public String  getInsurrate() {
            return insurrate;
        }

        public void setInsurrate(String insurrate) {
            this.insurrate = insurrate;
        }


        public String  getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(String isdelete) {
            this.isdelete = isdelete;
        }


        public String  getLicenseno() {
            return licenseno;
        }

        public void setLicenseno(String licenseno) {
            this.licenseno = licenseno;
        }


        public String  getManualno() {
            return manualno;
        }

        public void setManualno(String manualno) {
            this.manualno = manualno;
        }


        public String  getNetwt() {
            return netwt;
        }

        public void setNetwt(String netwt) {
            this.netwt = netwt;
        }


        public String  getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }


        public String  getOdttype() {
            return odttype;
        }

        public void setOdttype(String odttype) {
            this.odttype = odttype;
        }


        public String  getOdttypeValue() {
            return odttypeValue;
        }

        public void setOdttypeValue(String odttypeValue) {
            this.odttypeValue = odttypeValue;
        }


        public String  getOperatecode() {
            return operatecode;
        }

        public void setOperatecode(String operatecode) {
            this.operatecode = operatecode;
        }


        public String  getOperatecodescc() {
            return operatecodescc;
        }

        public void setOperatecodescc(String operatecodescc) {
            this.operatecodescc = operatecodescc;
        }


        public String  getOperatename() {
            return operatename;
        }

        public void setOperatename(String operatename) {
            this.operatename = operatename;
        }


        public String  getOperateruserid() {
            return operateruserid;
        }

        public void setOperateruserid(String operateruserid) {
            this.operateruserid = operateruserid;
        }


        public String  getOperaterusername() {
            return operaterusername;
        }

        public void setOperaterusername(String operaterusername) {
            this.operaterusername = operaterusername;
        }


        public String  getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }


        public String  getOrgname() {
            return orgname;
        }

        public void setOrgname(String orgname) {
            this.orgname = orgname;
        }


        public String  getOthercurr() {
            return othercurr;
        }

        public void setOthercurr(String othercurr) {
            this.othercurr = othercurr;
        }


        public String  getOthercurrValue() {
            return othercurrValue;
        }

        public void setOthercurrValue(String othercurrValue) {
            this.othercurrValue = othercurrValue;
        }


        public String  getOthermark() {
            return othermark;
        }

        public void setOthermark(String othermark) {
            this.othermark = othermark;
        }


        public String  getOthermarkValue() {
            return othermarkValue;
        }

        public void setOthermarkValue(String othermarkValue) {
            this.othermarkValue = othermarkValue;
        }


        public String  getOtherrate() {
            return otherrate;
        }

        public void setOtherrate(String otherrate) {
            this.otherrate = otherrate;
        }


        public String  getOwnercode() {
            return ownercode;
        }

        public void setOwnercode(String ownercode) {
            this.ownercode = ownercode;
        }


        public String  getOwnercodescc() {
            return ownercodescc;
        }

        public void setOwnercodescc(String ownercodescc) {
            this.ownercodescc = ownercodescc;
        }


        public String  getOwnername() {
            return ownername;
        }

        public void setOwnername(String ownername) {
            this.ownername = ownername;
        }


        public String  getPackno() {
            return packno;
        }

        public void setPackno(String packno) {
            this.packno = packno;
        }


        public String  getPayway() {
            return payway;
        }

        public void setPayway(String payway) {
            this.payway = payway;
        }


        public String  getPaywayValue() {
            return paywayValue;
        }

        public void setPaywayValue(String paywayValue) {
            this.paywayValue = paywayValue;
        }


        public String  getPaymentmark() {
            return paymentmark;
        }

        public void setPaymentmark(String paymentmark) {
            this.paymentmark = paymentmark;
        }


        public String  getPaymentmarkValue() {
            return paymentmarkValue;
        }

        public void setPaymentmarkValue(String paymentmarkValue) {
            this.paymentmarkValue = paymentmarkValue;
        }


        public String  getPreentryid() {
            return preentryid;
        }

        public void setPreentryid(String preentryid) {
            this.preentryid = preentryid;
        }


        public String  getPrintdate() {
            return printdate;
        }

        public void setPrintdate(String printdate) {
            this.printdate = printdate;
        }


        public String  getPromiseitmes() {
            return promiseitmes;
        }

        public void setPromiseitmes(String promiseitmes) {
            this.promiseitmes = promiseitmes;
        }


        public String  getRisk() {
            return risk;
        }

        public void setRisk(String risk) {
            this.risk = risk;
        }


        public String  getSendroate() {
            return sendroate;
        }

        public void setSendroate(String sendroate) {
            this.sendroate = sendroate;
        }


        public String  getSendstate() {
            return sendstate;
        }

        public void setSendstate(String sendstate) {
            this.sendstate = sendstate;
        }


        public String  getSendtime() {
            return sendtime;
        }

        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }


        public String  getSeqno() {
            return seqno;
        }

        public void setSeqno(String seqno) {
            this.seqno = seqno;
        }


        public String  getSfdz() {
            return sfdz;
        }

        public void setSfdz(String sfdz) {
            this.sfdz = sfdz;
        }


        public String  getTgdno() {
            return tgdno;
        }

        public void setTgdno(String tgdno) {
            this.tgdno = tgdno;
        }


        public String  getTradeareacode() {
            return tradeareacode;
        }

        public void setTradeareacode(String tradeareacode) {
            this.tradeareacode = tradeareacode;
        }


        public String  getTradeareacodeValue() {
            return tradeareacodeValue;
        }

        public void setTradeareacodeValue(String tradeareacodeValue) {
            this.tradeareacodeValue = tradeareacodeValue;
        }


        public String  getTradecode() {
            return tradecode;
        }

        public void setTradecode(String tradecode) {
            this.tradecode = tradecode;
        }


        public String  getTradecodescc() {
            return tradecodescc;
        }

        public void setTradecodescc(String tradecodescc) {
            this.tradecodescc = tradecodescc;
        }


        public String  getTradecountry() {
            return tradecountry;
        }

        public void setTradecountry(String tradecountry) {
            this.tradecountry = tradecountry;
        }








}