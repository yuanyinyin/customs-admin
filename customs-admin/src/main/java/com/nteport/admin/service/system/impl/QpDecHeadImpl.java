package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.dto.PrintTag;
import com.nteport.admin.dto.QpDecHeadDto;
import com.nteport.admin.entity.*;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.QpDecHeadMapper;
import com.nteport.admin.mapper.QpDecLicenseDocuMapper;
import com.nteport.admin.service.*;
import com.nteport.admin.service.system.IPageHelper;

import com.nteport.admin.service.system.IQpDecRequestCertService;
import com.nteport.admin.service.system.IQpDecUserService;
import com.nteport.admin.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ??????????????????
 */
@Service
public class QpDecHeadImpl extends ServiceImpl<QpDecHeadMapper, QpDecHead> implements IQpDecHeadService {

    @Autowired
    private QpDecHeadMapper qpDecHeadMapper;
    @Autowired
    private IQpDecListService  qpDecListService;

    @Autowired
    private IQpDecLicenseDocuService qpDecLicenseDocuService;

    @Autowired
    private IQpDecFreeTxtService qpDecFreeTxtService;
    @Autowired
    private IQpDecContainerService qpDecContainerService;


    @Autowired
    private IQpDecRequestCertService qpDecRequestCertService;


    @Autowired
    private IQpDecOtherPackService qpDecOtherPackService;
    @Autowired
    private IQpDecCopLimitService qpDecCopLimitService;
    @Autowired
    private IQpDecCopPromiseService qpDecCopPromiseService;


    @Autowired
    private IQpDecMarkLobService qpDecMarkLobService;
    @Autowired
    private IQpDecResultService qpDecResultService;





    @Autowired
    private IQpDecUserService qpDecUserService;





    @Value("${printServer}")
    private String printServer;







    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf2M = new SimpleDateFormat("yyyy???MM???dd???");
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");




    SimpleDateFormat simformat = new SimpleDateFormat("yyMMddhhmmss");


    /**
     * ??????????????????????????????
     *
     * @param params Map[pageNum ????????????, pageSize ????????????]
     * @return Json [list ???????????????total ????????????]
     * ??? ?????????
     */
    @Override
    public ApiResponse listPage(Map<String, String> params, UserEntity user) {
        if (!params.containsKey("page") || !params.containsKey("limit")) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        // ??????????????????
        long pageNum = Long.parseLong(params.get("page"));
        long pageSize = Long.parseLong(params.get("limit"));
        IPage<QpDecHead> userPage = new Page<>(pageNum, pageSize);
//        Map params = json.getInnerMap();

        // ??????????????????
        IPageHelper<QpDecHead> pageHelper = new PageHelperImpl<>();

        String startTime =  params.get("startTime");
        params.remove("startTime");
        String endTime =  params.get("endTime");
        params.remove("endTime");

        QueryWrapper<QpDecHead> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("orgid", user.getDeptId());

        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            queryWrapper.ge("declarationdata",  simpleDateFormat.format(new Date(Timestamp.valueOf(startTime).getTime())));
            queryWrapper.le("declarationdata",  simpleDateFormat.format(new Date(Timestamp.valueOf(endTime).getTime())));
        }
        String ieFlag =  params.get("ieFlag");
        params.remove("ieFlag");
        if(StringUtils.isNotBlank(ieFlag)){
            queryWrapper.eq("ieflag", ieFlag);
        }

        String entryId =  params.get("entryId");
        params.remove("entryId");
        if(StringUtils.isNotBlank(entryId)){
            queryWrapper.like("entryid", entryId);
        }


//        String startTime = (String) params.get("startTime");
//        String endTime = (String) params.get("endTime");
//        params.remove("startTime");
//        params.remove("endTime");


//        queryWrapper.apply("CREATETIME = TO_TIMESTAMP({0}, 'YYYY-MM-DD HH24:MI:SS:FF6')", createtime);
//        if(StringUtils.isNotBlank(createtime)){
//            queryWrapper.eq("createtime", Timestamp.valueOf(createtime));
//        }

//        queryWrapper.e
        //??????????????????????????????
//        if(user.getRoleCodes().contains(ConstantUtil.ROLE_ADMIN) ||user.getRoleCodes().contains(ConstantUtil.ROLE_CITYADMIN)){
//        }else if(user.getRoleCodes().contains(ConstantUtil.ROLE_QXDSBADMIN)){
//            List<String> deptIds = deptService.queryAreaDept(user.getDeptId());
//            queryWrapper.in("dept_id",deptIds);
//        }

//        queryWrapper.orderByAsc("dept_id");

        // ????????????

        userPage = qpDecHeadMapper.selectPage(userPage, queryWrapper);
        List<QpDecHead> list = userPage.getRecords();
        Long total = qpDecHeadMapper.selectCount(queryWrapper);

        JSONObject data = new JSONObject();
        // ??????json??????
        data.put("items", list);
        data.put("total", total);
        return ApiResponse.success(data);
    }

    @Override
    public ApiResponse showOne(String headId, UserEntity user) {
        if(StringUtils.isBlank(headId)) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        QpDecHead qpDecHead =   qpDecHeadMapper.selectById(headId);
        QpDecHeadDto qpDecHeadDto = new QpDecHeadDto();
        BeanUtils.copyProperties(qpDecHead, qpDecHeadDto);
        //--??????????????????
        QueryWrapper<QpDecList> decListQueryWrapper = new QueryWrapper<>();
        decListQueryWrapper.eq("headid", headId);
        List<QpDecList>  qpDecLists =  qpDecListService.list(decListQueryWrapper);
        if(!CollectionUtils.isEmpty(qpDecLists)){
            Collections.sort(qpDecLists, new Comparator<QpDecList>() {
                public int compare(QpDecList arg0, QpDecList arg1) {
                    int hits0 = arg0.getGno() == null ? 0 : Integer.parseInt(arg0.getGno());
                    int hits1 = arg1.getGno() == null ? 0 : Integer.parseInt(arg1.getGno());
                    if (hits1 < hits0) {
                        return 1;
                    } else if (hits1 == hits0) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
            qpDecHeadDto.setQpDecLists(qpDecLists);
        }

//        --?????????????????????
        QueryWrapper<QpDecContainer> qpDecContainerQueryWrapper = new QueryWrapper<>();
        qpDecContainerQueryWrapper.eq("headid", headId);
        List<QpDecContainer>  qpDecContainerList =  qpDecContainerService.list(qpDecContainerQueryWrapper);
        if(!CollectionUtils.isEmpty(qpDecContainerList)) {
            if(qpDecContainerList.size()>8){
                Collections.sort(qpDecContainerList, new Comparator<QpDecContainer>() {
                    public int compare(QpDecContainer arg0, QpDecContainer arg1) {
                        return arg0.getContainerid().compareTo(arg1.getContainerid());
                    }
                });
            }
            qpDecHeadDto.setQpDecContainerList(qpDecContainerList);
        }

//        --????????????
        QueryWrapper<QpDecLicenseDocu> qpDecLicenseDocuQueryWrapper = new QueryWrapper<>();
        qpDecLicenseDocuQueryWrapper.eq("headid", headId);
        List<QpDecLicenseDocu>  qpDecLicenseDocuList =  qpDecLicenseDocuService.list(qpDecLicenseDocuQueryWrapper);
        if(!CollectionUtils.isEmpty(qpDecLicenseDocuList)) {
            qpDecHeadDto.setQpDecLicenseDocuList(qpDecLicenseDocuList);
        }

//        --??????????????????  ???????????????
        QueryWrapper<QpDecFreeTxt> qpDecFreeTxtQueryWrapper = new QueryWrapper<>();
        qpDecFreeTxtQueryWrapper.eq("headid", headId);
        QpDecFreeTxt  qpDecFreeTxt =  qpDecFreeTxtService.getOne(qpDecFreeTxtQueryWrapper);
        if(!ObjectUtils.isEmpty(qpDecFreeTxt)) {
            qpDecHeadDto.setQpDecFreeTxt(qpDecFreeTxt);
        }

        return ApiResponse.success(qpDecHeadDto);
    }


    @Override
    public void exportExcel(Map<String, String> params, HttpServletRequest request ,HttpServletResponse response,UserEntity user) {
        try {
            //??????excel??????
            HashMap param = new HashMap<>();

            //
            params.put("orgid", user.getDeptId());


            String startTime =  params.get("startTime");
            params.remove("startTime");
            String endTime =  params.get("endTime");
            params.remove("endTime");
            if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                params.put("startTime",simpleDateFormat.format(new Date(Timestamp.valueOf(startTime).getTime())));
                params.put("endTime",simpleDateFormat.format(new Date(Timestamp.valueOf(endTime).getTime())));
            }
            String isMerge =  params.get("isMerge");

            List<HashMap<String ,Object>> lists = qpDecHeadMapper.exportExcel(params);
            DecHeadDownBySXXFExcelHelper decHeadDownExcelHelper = new DecHeadDownBySXXFExcelHelper();
            decHeadDownExcelHelper.init(request);
            List<ExportHeads> exportHeads = decHeadDownExcelHelper.getExportRalation(params.get("ieFlag"), request);
            decHeadDownExcelHelper.setTitle(params.get("ieFlag"),exportHeads);
            decHeadDownExcelHelper.setHeader(params.get("ieFlag"));
            decHeadDownExcelHelper.setDateContent(startTime,endTime);
            decHeadDownExcelHelper.setColumnContent(lists,exportHeads,isMerge);
            decHeadDownExcelHelper.writeToExcel();  //
            decHeadDownExcelHelper.closeInputStream();  //????????????

            if (decHeadDownExcelHelper.wb != null) {
                // ??????
                InputStream fis = new BufferedInputStream(new FileInputStream(decHeadDownExcelHelper.newFile));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                OutputStream toClient = new BufferedOutputStream(
                        response.getOutputStream());
                response.setContentType("application/ms-excel");
                String newName = URLEncoder.encode(
                        "???????????????" + simformat.format(new Date()) + ".xlsx",
                        "UTF-8");
                response.addHeader("Content-Disposition",
                        "attachment;filename=\"" + newName + "\"");
                response.addHeader("Content-Length", "" + decHeadDownExcelHelper.newFile.length());
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            }




            //?????????
//            List<List<String>> dataList = new ArrayList<>();
////            for (int i = 0; i < list.size(); i++) {
////                List<String> stringList = new ArrayList<>();
////                stringList.add(list.get(i).getYearAndMonth());
////                stringList.add(String.format("%.5f",list.get(i).getContractAmount()));
////                stringList.add(String.format("%.5f",list.get(i).getInvoiceAmount()));
////                stringList.add(String.format("%.5f",list.get(i).getWithdrawalFunds()));
////                stringList.add(String.format("%.5f",list.get(i).getMonthlyIncome()));
////                stringList.add(String.format("%.5f",list.get(i).getMonthlyProfit()));
////                stringList.add(String.format("%.5f",list.get(i).getCurrentProfitMargin()));
////                stringList.add(String.format("%.5f",list.get(i).getMonthCashFlow()));
////                dataList.add(stringList);
////            }
//            ExcelUtil.exportExcel(response, "???????????????.xlsx", titleList, dataList, 5000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public ApiResponse printDec(Map<String, String> params) {
        String id = params.get("id");
        String printType = params.get("printType");
        String ieFlag = params.get("ieFlag");
        String cusType = params.get("cusType");
        try {
          return  ApiResponse.success(decPrint(id, printType, ieFlag, cusType)) ;
        }catch  (Exception e){
            return  ApiResponse.fail(e.getMessage()) ;
        }



    }


    public String rePlaceMoreEmpty(String value){
        while (true){
            if(value.indexOf("\n") > -1){
                value = value.replace("\n"," ");
            } else{
                break;
            }
        }
        while (true){
            if(value.indexOf("  ") > -1){
                value = value.replace("  "," ");
            } else{
                break;
            }
        }
        return value;
    }



    /**
     *@Author : xw
     *@Description : ??????????????????????????????????????????List
     *@Date : 2018/7/18 15:55
     */
    public  boolean checkExsitByList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    public static String[] CONTAINER_L = new String[] { "11", "12", "13", "32"};


    /***
     * ??????map
     * @param decHead
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Map<String, Object> modelToMap(QpDecHead decHead) throws Exception {
        Map<String, Object> result = new HashMap(JSON.parseObject(JSON.toJSONString(decHead)));

//        JSON.parseObject(JSON.toJSONString(decHead));
//        BeanInfo beanInfo = Introspector.getBeanInfo(decHead.getClass());
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        for (PropertyDescriptor property : propertyDescriptors) {
//            String key = property.getName();
//
//            if (key.compareToIgnoreCase("class") == 0) {
//                continue;
//            }
//            Method getter = property.getReadMethod();
//            Object value = getter != null ? getter.invoke(decHead) : null;
//            result.put(key, value);
//        }

        //???????????????
        String useOrgPersonCode = "";
        String useOrgPersonTel = "";

        String  id = decHead.getId();
        QueryWrapper<QpDecUser> qpDecUserQueryWrapper = new QueryWrapper<>();
        qpDecUserQueryWrapper.eq("headid", id);
        List<QpDecUser>  qpDecUserList =  qpDecUserService.list(qpDecUserQueryWrapper);

        //




        QueryWrapper<QpDecCopLimit> qpDecCopLimitQueryWrapper = new QueryWrapper<>();
        qpDecCopLimitQueryWrapper.eq("headid", id);
        List<QpDecCopLimit>  decCopLimits =  qpDecCopLimitService.list(qpDecCopLimitQueryWrapper);
        if(!CollectionUtils.isEmpty(decCopLimits)){
            result.put("decCopLimits", JSONArray.parseArray(JSON.toJSONString(decCopLimits)));
        }


        QueryWrapper<QpDecCopPromise> qpDecCopPromiseQueryWrapper = new QueryWrapper<>();
        qpDecCopPromiseQueryWrapper.eq("headid", id);
        List<QpDecCopPromise>  decCopPromises =  qpDecCopPromiseService.list(qpDecCopPromiseQueryWrapper);
        if(!CollectionUtils.isEmpty(decCopPromises)){
            result.put("decCopPromises", JSONArray.parseArray(JSON.toJSONString(decCopPromises)));
        }


        QueryWrapper<QpDecMarkLob> qpDecMarkLobQueryWrapper = new QueryWrapper<>();
        qpDecMarkLobQueryWrapper.eq("headid", id);
        List<QpDecMarkLob>  decMarkLobs =  qpDecMarkLobService.list(qpDecMarkLobQueryWrapper);
        if(!CollectionUtils.isEmpty(decMarkLobs)){
            result.put("decMarkLobs", JSONArray.parseArray(JSON.toJSONString(decMarkLobs)));
        }


        QueryWrapper<QpDecRequestCert> qpDecRequestCertQueryWrapper = new QueryWrapper<>();
        qpDecRequestCertQueryWrapper.eq("headid", id);
        List<QpDecRequestCert>  decRequestCerts =  qpDecRequestCertService.list(qpDecRequestCertQueryWrapper);
        if(!CollectionUtils.isEmpty(decRequestCerts)){
            result.put("decRequestCerts", JSONArray.parseArray(JSON.toJSONString(decRequestCerts)));
        }

//        QueryWrapper<QpDecCopLimit> qpDecCopLimitQueryWrapper = new QueryWrapper<>();
//        qpDecCopLimitQueryWrapper.eq("headid", id);
//        List<QpDecCopLimit>  decCopLimits =  qpDecCopLimitService.list(qpDecCopLimitQueryWrapper);
//        if(!CollectionUtils.isEmpty(decCopLimits)){
//            result.put("decCopLimits", JSONArray.parseArray(JSON.toJSONString(decCopLimits)));
//        }

        //////




        if(!CollectionUtils.isEmpty(qpDecUserList)){
            result.put("decUsers", JSONArray.parseArray(JSON.toJSONString(qpDecUserList)));
            useOrgPersonCode = qpDecUserList.get(0).getUseorgpersoncode()  == null ? "" : qpDecUserList.get(0).getUseorgpersoncode();
            useOrgPersonTel = qpDecUserList.get(0).getUseorgpersontel() == null ? "" : qpDecUserList.get(0).getUseorgpersontel();
        }
        result.put("useOrgPersonCode", useOrgPersonCode);
        result.put("useOrgPersonTel", useOrgPersonTel);




        //??????
        if (StringUtils.isEmpty(decHead.getFeecurr())) {//_value
            decHead.setFeecurr("");
        }
        if (StringUtils.isEmpty(decHead.getFeemark())) {
            decHead.setFeemark("");
        }
        if (StringUtils.isEmpty(decHead.getFeerate())) {
            decHead.setFeerate("");
        }
        String yunFei =  decHead.getFeecurr()+ "/" + decHead.getFeerate() + "/" + decHead.getFeemark();
        if ("//".equals(yunFei)) {
            yunFei = "";
        }
        result.put("yf", yunFei);

        //??????
        if (StringUtils.isEmpty(decHead.getInsurcurr())) {
            decHead.setInsurcurr("");
        }
        if (StringUtils.isEmpty(decHead.getInsurmark())) {
            decHead.setInsurmark("");
        }
        if (StringUtils.isEmpty(decHead.getInsurrate())) {
            decHead.setInsurrate("");
        }
        String baofei =  decHead.getInsurcurr()+ "/" + decHead.getInsurrate() + "/" + decHead.getInsurmark();
        if ("//".equals(baofei)) {
            baofei = "";
        }
        result.put("bf", baofei);

        //??????
        if (StringUtils.isEmpty(decHead.getOthercurr())) {
            decHead.setOthercurr("");
        }
        if (StringUtils.isEmpty(decHead.getOthermark())) {
            decHead.setOthermark("");
        }
        if (StringUtils.isEmpty(decHead.getOtherrate())) {
            decHead.setOtherrate("");
        }
        String zaFei =  decHead.getOthercurr() + "/" + decHead.getOtherrate() + "/" + decHead.getOthermark();
        if ("//".equals(zaFei)) {
            zaFei = "";
        }
        result.put("zf", zaFei);

        //???????????????????????????????????????????????????????????????????????????
        String promiseItmes = decHead.getPromiseitmes();
        String[] itmes =null;
        if(StringUtils.isNotEmpty(promiseItmes)){
            itmes = promiseItmes.split("");
        }
        if(null != itmes){
            Map<String, String> commit = new HashMap<String, String>();
            commit.put("0", "???");
            commit.put("1", "???");
            commit.put("9", "");
            commit.put("", "???");

            for (int mm = 0; mm < promiseItmes.length(); mm++) {
                result.put("jgxh"+mm, commit.get(promiseItmes.substring(mm, mm+1)));
            }

            //????????????
            String buss_zbzj = decHead.getBussZbzj();
            result.put("zbzj", commit.get(buss_zbzj));
        }


        /*****????????????*****
         * wrapType_valueMo
         * wrapTypeMo
         */
        String wrapTypeMo="", wrapType_valueMo="";
        if(StringUtils.isNotEmpty(decHead.getWraptype())){
            wrapTypeMo+= decHead.getWraptype();
            wrapType_valueMo += decHead.getWraptypeValue();
        }


        QueryWrapper<QpDecOtherPack> qpDecOtherPackQueryWrapper = new QueryWrapper<>();
        qpDecOtherPackQueryWrapper.eq("headid", id);
        List<QpDecOtherPack>  decOtherPacks =  qpDecOtherPackService.list(qpDecOtherPackQueryWrapper);


//        List<QpDecOtherPack> decOtherPacks = decHead.getDecOtherPacks();
        if(null != decOtherPacks && decOtherPacks.size()>0){
            result.put("decOtherPacks", JSONArray.parseArray(JSON.toJSONString(decOtherPacks)));
            for (int i = 0; i < decOtherPacks.size(); i++) {
                wrapTypeMo +="/"+ decOtherPacks.get(i).getPacktype();
                wrapType_valueMo += "/" + decOtherPacks.get(i).getPacktypeValue();
            }
        }
        if(StringUtils.isNotEmpty(wrapTypeMo)){
            wrapTypeMo="("+ wrapTypeMo+")";
        }
        result.put("wrapTypeMo", wrapTypeMo);
        result.put("wrapType_valueMo", wrapType_valueMo);


        /***???????????????*{{glbgdh}}        {{glbah}}
         */
        QueryWrapper<QpDecFreeTxt> qpDecFreeTxtQueryWrapper = new QueryWrapper<>();
        qpDecFreeTxtQueryWrapper.eq("headid", id);
        QpDecFreeTxt  decFreeTxt =  qpDecFreeTxtService.getOne(qpDecFreeTxtQueryWrapper);

//        QpDecFreeTxt decFreeTxt = decHead.getDecFreetxt();
        String glbgdh="", glbah="";
        if(null!= decFreeTxt){
            result.put("decFreeTxt", JSON.toJSONString(decOtherPacks));
            if(StringUtils.isNotEmpty(decFreeTxt.getRelid())){//??????????????????
                glbgdh= "??????????????????:"+ decFreeTxt.getRelid();
            }
            if (StringUtils.isNotEmpty(decFreeTxt.getRelmanno())) {//???????????????
                glbah="???????????????"+ decFreeTxt.getRelmanno();
            }
        }
        result.put("glbgdh", glbgdh);
        result.put("glbah", glbah);


        /****????????? ????????? ****/
        String bjmm_noteS = "??????: ", bzmm= "??????: ";
        if(StringUtils.isNotEmpty(decHead.getNotes())){
            bjmm_noteS+= rePlaceMoreEmpty(decHead.getNotes());
        }
        if(StringUtils.isNotEmpty(decHead.getMarkno())){
            bjmm_noteS+=" "+ rePlaceMoreEmpty(decHead.getMarkno());
        }
        bzmm= bjmm_noteS;

        QueryWrapper<QpDecContainer> qpDecContainerQueryWrapper = new QueryWrapper<>();
        qpDecContainerQueryWrapper.eq("headid", id);
        List<QpDecContainer>  containers =  qpDecContainerService.list(qpDecContainerQueryWrapper);


//        List<QpDecContainer> containers = decHead.getDecContainers();
        if(null!=containers && containers.size()>0){
            result.put("decContainers", JSONArray.parseArray(JSON.toJSONString(containers)));
            bjmm_noteS +="  ??????????????????????????????"+decHead.getDeccontainernum()+";";

            for (int k = 0; k < containers.size(); k++) {
                QpDecContainer decContainer = containers.get(k);
                bjmm_noteS+= decContainer.getContainerid()+"*";
                if (checkExsitByList(CONTAINER_L, decContainer.getContainermd())) {
                    bjmm_noteS+="2;";
                }else{
                    bjmm_noteS += "1;";
                }
            }
        }
        result.put("bjmm_noteS", bjmm_noteS);
        result.put("bzmm", bzmm);

        /***
         * ??????????????????????????????
         {{trafName_voyNo}}
         * */
        String trafName_voyNo ="";
        if(StringUtils.isNotEmpty(decHead.getTrafname())){
            trafName_voyNo+= decHead.getTrafname();
        }
        if (StringUtils.isNotEmpty(decHead.getVoyno())) {
            trafName_voyNo += "/"+decHead.getVoyno();
        }
        result.put("trafName_voyNo", trafName_voyNo);


        /****{{dz_bh}}****/
        String dz_bh= "";
        String dz="";

        QueryWrapper<QpDecLicenseDocu> qpDecLicenseDocuQueryWrapper = new QueryWrapper<>();
        qpDecLicenseDocuQueryWrapper.eq("headid", id);
        List<QpDecLicenseDocu>  decLicenseDocus =  qpDecLicenseDocuService.list(qpDecLicenseDocuQueryWrapper);


//        List<DecLicenseDocu> decLicenseDocus = decHead.getDecLicenseDocus();
        if(null!=decLicenseDocus&& decLicenseDocus.size()>0){
            result.put("decLicenseDocus", JSONArray.parseArray(JSON.toJSONString(decLicenseDocus)));
            for (int i = 0; i < decLicenseDocus.size(); i++) {
                dz+= decLicenseDocus.get(i).getDocucodeValue()+ decLicenseDocus.get(i).getCertcode()+" ";
            }
        }
        /**??????????????? ?????????????????? begin */
        QueryWrapper<QpDecList> qpDecListQueryWrapper = new QueryWrapper<>();
        qpDecListQueryWrapper.eq("headid", id);
        qpDecListQueryWrapper.orderByAsc("gno");

        List<QpDecList>  decLists =  qpDecListService.list(qpDecListQueryWrapper);


//        List<DecList> decLists = decHead.getDecLists();
        String sfdz1 = "";
        if(!CollectionUtils.isEmpty(decLists)){
            result.put("decLists", JSONArray.parseArray(JSON.toJSONString(decLists)));
            for (int i = 0; i < decLists.size(); i++) {
                QpDecList decList = decLists.get(i);
                //TODO  ???????????????
                if(StringUtils.isNotEmpty(decList.getCertType())){
                    sfdz1="???????????????"+"<"+decList.getCertType()+">"+decList.getEcoCertNo()+" ";
                    break;
                }
            }
        }

        /**??????????????? ?????????????????? end*/
        if(StringUtils.isNotEmpty(dz)||StringUtils.isNotEmpty(sfdz1)){
            dz_bh="????????????1:"+sfdz1+dz;
        }

//        QueryWrapper<DecFile> decFileQueryWrapper = new QueryWrapper<>();
//        decFileQueryWrapper.eq("headid", id);
//        List<DecFile>  decFiles =  decFileService.list(decFileQueryWrapper);

//        List<DecFile> decFiles = decHead.getDecFiles();
//        if (null != decFiles && decFiles.size() > 0) {
//            dz_bh += "????????????2:";
//            for (int i = 0; i < decFiles.size(); i++) {
////                dz_bh += decFiles.get(i).getFiletypeCode_value()==null?"": decFiles.get(i).getFileTypeCode_value() + " ";
//            }
//        }

        result.put("dz_bh", dz_bh);

        //???????????????
        String count = String.valueOf(CollectionUtils.isEmpty(containers) ?"": containers.size());
        result.put("num", count);

        //??????
        if(!CollectionUtils.isEmpty(decLists)){
            String purpose = decLists.get(0).getPurpose() == null ? "" : decLists.get(0).getPurpose();
            String purpose_value = decLists.get(0).getPurposeValue()  == null ? "" : decLists.get(0).getPurposeValue();
            if (StringUtils.isNotEmpty(purpose) && StringUtils.isNotEmpty(purpose_value)) {
                String pur = purpose_value + "(" + purpose + ")";
                String ppose = pur.replace(" ", "");
                result.put("pur", ppose);
            }
        }

        //?????????????????????
        String docuCode = "";
        String certCode = "";//decLicenseDocu.getCertCode();
        String ycddygx="";
        boolean isExsitAB=false;

//        QueryWrapper<DecNative> decNativeQueryWrapper = new QueryWrapper<>();
//        decNativeQueryWrapper.eq("headid", id);
//        List<DecNative>  decNatives =  decNativeService.list(decNativeQueryWrapper);



//        List<DecNative> DecNatives = decHead.getDecNatives();
        if (null != decLicenseDocus && decLicenseDocus.size() > 0) {
            for (int k = 0; k < decLicenseDocus.size(); k++) {
                QpDecLicenseDocu decLicenseDocu = decLicenseDocus.get(k);
                certCode= decLicenseDocu.getCertcode();
                docuCode = decLicenseDocu.getDocucode();

                if("Y/E/R/F/J".contains(docuCode)){
                    ycddygx = docuCode + certCode + ":";
//                    for (int i = 0; i < decNatives.size(); i++) {
//                        DecNative decNative = decNatives.get(i);
//                        if (i == (decNatives.size() - 1)) {
//                            ycddygx += decNative.getBgdgoodsno() + "-" + decNative.getCertificateno() + " ";
//                        } else {
//                            ycddygx += decNative.getBgdgoodsno() + "-" + decNative.getCertificateno() + ",";
//                        }
//                    }
                }
                if("A".equals(docuCode) || "B".equals(docuCode)){
                    isExsitAB = true;
                }
            }
        }

        String ycdGoodsRelation="";
        for (int i = 0; i < decLists.size(); i++) {
            QpDecList decList = decLists.get(i);
            //TODO
            if(StringUtils.isNotEmpty(decList.getCertType())){
                ycdGoodsRelation+=decList.getGno()+"-"+decList.getCertificateNo()+",";
            }
        }
        if(StringUtils.isNotEmpty(ycdGoodsRelation)){
            ycdGoodsRelation=ycdGoodsRelation.substring(0,ycdGoodsRelation.length()-1);
        }
        ycddygx+=sfdz1+":"+ycdGoodsRelation;

        if(isExsitAB){
            result.put("docuCode_value_fxtzs", "????????????");
        }else{
            result.put("docuCode_value_fxtzs", "");
        }
        result.put("ycddygx", ycddygx);
        //????????????
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");// ????????????????????????,?????????yyyy-MM-dd
        String dateStr = df.format(calendar.getTime());

//            Date now=new Date();
//            String dateStr = df.format(now);
        result.put("printDate", dateStr);
        result.put("printDateChn", sdf2M.format(calendar.getTime()));

        /***
         * ??????????????? ????????????
         */
        String declarationData = decHead.getDeclarationdata();
        if(StringUtils.isNotEmpty(declarationData)){
            declarationData= declarationData.replace("-","");
            Date newDate = sdf1.parse(declarationData);
            result.put("declarationDataChn",sdf2M.format(newDate) );
        }

        result.put("customMaster_value2", decHead.getCustommasterValue());



        /***???????????? ?????????????????????***/
        String needKuoHao= "customMaster_value;tradeCode;tradeCodeScc;iePort;overseasConsignorCode;trafMode;ownerCode;" +
                "ownerCodeScc;tradeMode;cutMode;despPortCode;tradeAreaCode;tradeCountry;distinatePort;entyPortCode;" +
                "wrapType;transMode;agentCodeScc;overseasConsigneeCode;orgCode;vsaOrgCode;inspOrgCode;purpOrgCode;" +
                "origBoxFlag;correlationReasonFlag";
        String[] needArr = needKuoHao.split(";");
        for (int l = 0; l < needArr.length; l++) {
            String val= result.get(needArr[l])==null?"": (String) result.get(needArr[l]);
            if(StringUtils.isNotEmpty(val)){
                result.put(needArr[l],"("+ val +")");
            }
        }



        return result;
    }

    /**
     *
     * @param id
     * @param printType bgdHdd;goodsFjy;jzx
     *
     * @param ieFlag i e
     * @param cusType
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Map decPrint(String id, String printType, String ieFlag, String cusType) throws Exception {
//        String pageTy = getRequest().getParameter("pageTy");
//        String nameTypeValue = getRequest().getParameter("nameTypeValue");

        String pageTy = null;
        String nameTypeValue = null;
//        String nameType = getRequest().getParameter("nameType");
//        String nameM = getRequest().getParameter("nameM");
//        String isDzz = getRequest().getParameter("isDzz");
        String isPl =  null;
        String customHeadId = null;

//        String customHeadId = getRequest().getParameter("customHeadId");
//        UserInfo user = (UserInfo) Struts2Utils.getSession().getAttribute(SessionUtil.loginInfo);
        String customNo="3206980004";
//        if(null!=user){
//            OrgInfo orgInfo = user.getOrgInfo();
//            customNo = orgInfo.getCustomNo();
//        }
        Map paramMap = new HashMap();
        paramMap.put("pageTy",pageTy);
        paramMap.put("nameTypeValue",nameTypeValue);
//        paramMap.put("nameType",nameType);
//        paramMap.put("nameM",nameM);
//        paramMap.put("isDzz",isDzz);
        paramMap.put("isPl",isPl);
        paramMap.put("customNo",customNo);
        paramMap.put("printType",printType);
        paramMap.put("ieFlag",ieFlag);
        paramMap.put("cusType",cusType);
        paramMap.put("id",id);

//        DecHead decHead = getDecHeadById(id);
        QpDecHead qpDecHead =   qpDecHeadMapper.selectById(id);
        if(null == qpDecHead){
            return null;
        }



//        String cusSql = "select t.id from CUSTOMSHEAD t where t.headid='"+id+"'  ";
//        List cusIds=  daoFacade.query4Sql(cusSql,null);
//        if(cusIds.size()==1){
//            customHeadId=cusIds.get(0).toString();
//        }
//        System.out.println(customHeadId);
        if (StringUtils.isEmpty(pageTy)){
            pageTy = "0";
        }
        //region newFileName
        /**
         *@Author : xw
         *@Description : ??????????????????newFileName??????
         *@Date : 2019/3/11 14:04
         */
        String newFileName = "";
        if("0".equals(pageTy)){
            newFileName = qpDecHead.getClientno() + "_" + System.currentTimeMillis();
        }else{
            if(StringUtils.isNotEmpty(nameTypeValue)){
                if (nameTypeValue.equals("auto")) {
                    String packNo = qpDecHead.getPackno();
                    String custom = qpDecHead.getIeportValue();
                    if (StringUtils.isNotEmpty(custom)) {
                        custom = custom.substring(0, 2);
                    }
                    if (printType.equals("bgdHdd")) {
                        newFileName = "??????-" + custom + packNo;
                    } else if (printType.equals("bgdm")) {
                        newFileName = "??????-" + custom + packNo;
                    } else if (printType.equals("fxtzs")) {
                        newFileName = "??????-" + custom + packNo;
                    } else if (printType.equals("fxtzs,bgdm")) {
                        newFileName = "????????????-" + custom + packNo;
                    } else {
                        newFileName = custom + packNo;
                    }
                } else if(nameTypeValue.equals("billNo")){
                    //?????????billno  ?????????????????? billno
//                    if(StringUtils.isNotBlank(qpDecHead.getBillno()) ){
//                        newFileName = qpDecHead.getBillno();
//                    }else {
//                        String reHql = "select o from ReceiptSignHead o" +
//                                " where o.id= (select c.receiptSignHeadId from  CustomsHead  c where c.id='"+customHeadId+"'  ) ";
//                        List<ReceiptSignHead> reIds=  daoFacade.customQuery(reHql,null);
//                        //???????????? ?????????????????????
//                        if(reIds.size()==1){
//                            String billNo = reIds.get(0).getBillNo();
//                            //  customHeadId=cusIds.get(0).toString();
//                            if(StringUtils.isNotBlank(billNo)){
//                                newFileName=billNo;
//                            }else{
//                                //???????????????
//                                newFileName = decHead.getClientNo() + "_" + System.currentTimeMillis();
//                            }
//                        }else{
//                            newFileName = decHead.getClientNo() + "_" + System.currentTimeMillis();
//                        }
//                    }
                }

                // String cusSql = "select t.id from CUSTOMSHEAD t where t.headid='"+id+"'  ";
                //        List cusIds=  daoFacade.query4Sql(cusSql,null);
                //        if(cusIds.size()==1){
                //            customHeadId=cusIds.get(0).toString();
                //        }
                //        System.out.println(customHeadId);
                else {
//                    Class userCla = (Class) qpDecHead.getClass();
//                    Field[] fs = userCla.getDeclaredFields();
//                    for (int i = 0; i < fs.length; i++) {
//                        Field f = fs[i];
//                        f.setAccessible(true); // ?????????????????????????????????
//                        try {
//                            if (f.getName().endsWith(nameTypeValue)) {
//                                if(f.get(qpDecHead) == null || ObjectUtils.isEmpty(f.get(qpDecHead).toString())){
//                                    newFileName = qpDecHead.getClientno() + "_" + System.currentTimeMillis();
//                                }else{
//                                    String n = f.get(qpDecHead).toString();
//                                    if("1".equals(isPl)){
//                                        n = containMapkey(nameMap,n);
//                                        nameMap.put(n,n);
//                                    }
//                                    newFileName = nameM.replace(nameType,n).replace("<","").replace(">","");
//                                    newFileName= newFileName.replace("*","-");
//                                    break;
//                                }
//                            }
//                        } catch (IllegalArgumentException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
            }else{
                newFileName = qpDecHead.getClientno() + "_" + System.currentTimeMillis();
            }
        }
        paramMap.put("newFileName",newFileName);

        //endregion

        Map<String, Object> result = modelToMap(qpDecHead);
        //???????????????????????????
        //
//        QueryWrapper<DecReceiptLog> decReceiptLogQueryWrapper = new QueryWrapper<>();
//        decReceiptLogQueryWrapper.eq("headid", id);
//        decReceiptLogQueryWrapper.eq("customState", "H2000P");
//        List<DecReceiptLog>  decReceiptLogs =  decReceiptLogService.list(decReceiptLogQueryWrapper);


//        String  decReceiptLogHql = " from DecReceiptLog o where o.customState='H2000P' and o.headId='"+id+"'  " +
//                " order by o.createTime desc " ;
//        List<DecReceiptLog> decReceiptLogs = daoFacade.customQuery(decReceiptLogHql,null);
//        if(!CollectionUtils.isEmpty(decReceiptLogs)){
//            String fxsj = decReceiptLogs.get(0).getNoticedate();
////            if(null!=fxsj){
////                result.put("fxjg_sj",sdf4.format(fxsj))  ;
////            }
//            if(null!=fxsj){
//                result.put("fxjg_sj", fxsj)  ;
//            }
//        }

        if(StringUtils.isNotBlank(customHeadId)){
//            CustomsHead c = daoFacade.retrieve(CustomsHead.class,customHeadId);
//            if(c != null){
//                result.put("c_clientNo", c.getClientNo());
//            }
        }
        PrintTag printTag = new PrintTag();
        printTag.putTagByType(result,ieFlag,cusType,printType);

        if(StringUtils.isEmpty(printType)){
            printType="bgdHdd";
        }

        //tips
        //??????
        BigDecimal zjTotalNum = BigDecimal.ZERO;
        //??????????????????
        BigDecimal cjslTotalNum = BigDecimal.ZERO;
        //??????????????????
        BigDecimal fdslTotalNum = BigDecimal.ZERO;
        //??????????????????
        BigDecimal deslTotalNum = BigDecimal.ZERO;


        QueryWrapper<QpDecList> decListQueryWrapper = new QueryWrapper<>();
        decListQueryWrapper.eq("headid", qpDecHead.getId());
        List<QpDecList>  decLists =  qpDecListService.list(decListQueryWrapper);
        if(!CollectionUtils.isEmpty(decLists)){
            Collections.sort(decLists, new Comparator<QpDecList>() {
                public int compare(QpDecList arg0, QpDecList arg1) {
                    int hits0 = arg0.getGno() == null ? 0 : Integer.parseInt(arg0.getGno());
                    int hits1 = arg1.getGno() == null ? 0 : Integer.parseInt(arg1.getGno());
                    if (hits1 < hits0) {
                        return 1;
                    } else if (hits1 == hits0) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
//            qpDecHeadDto.setQpDecLists(qpDecLists);
        }
//        List<DecList> decLists = decHead.getDecLists();
        for(int i = 0; i < decLists.size(); i++){
            QpDecList decList = decLists.get(i);
            BigDecimal declTotal = new BigDecimal(decList.getDecltotal() == null ? "0" : decList.getDecltotal());
            BigDecimal gqty = new BigDecimal(decList.getGqty() == null ? "0" : decList.getGqty());
            BigDecimal firstQty = new BigDecimal(decList.getFirstqty() == null ? "0" : decList.getFirstqty());
            BigDecimal secondQty = new BigDecimal(decList.getSecondqty() == null ? "0" : decList.getSecondqty());

            zjTotalNum = zjTotalNum.add(declTotal);
            cjslTotalNum = cjslTotalNum.add(gqty);
            fdslTotalNum = fdslTotalNum.add(firstQty);
            deslTotalNum = deslTotalNum.add(secondQty);
        }
        BigDecimal zjTotal = zjTotalNum.setScale(5, BigDecimal.ROUND_HALF_UP);
        BigDecimal cjslTotalInt = cjslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
        BigDecimal fdslTotalInt = fdslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
        BigDecimal deslTotalInt = deslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);

        String zjTotalString = String.valueOf(zjTotal);
        String cjslTotalString = cjslTotalInt.toString();
        String fdslTotalString = fdslTotalInt.toString();
        String deslTotalString = deslTotalInt.toString();

        String tips = "??????" + zjTotalString + ",??????????????????" + cjslTotalString + ",??????????????????" + fdslTotalString + ",??????????????????" + deslTotalString;
        result.put("tips", tips);


        //endregion
        Map httpParam=new HashMap();
        httpParam.put("data",result);
        httpParam.put("param",paramMap);
        String str = toJSONString(httpParam);
        System.out.println(str);
//        getDaoFacade().getEntityManager().detach(decHead);//????????????
        return HttpClientUtil.HttpPost(str, printServer);
    }



    public  String toJSONString(Object obj){
        final Set<String> excludes = new HashSet<String>();
        excludes.add("allModelAttr");
        excludes.add("allModelSearchableAttr");
        excludes.add("allRenderModelAttr");
        excludes.add("modelAttr");
        excludes.add("searchProperties");
        excludes.add("modelSearchableAttr");
        SerializeFilter filter = new PropertyFilter() {
            @Override
            public boolean apply(Object o, String s, Object o2) {
                for (String exclude : excludes) {
                    if (exclude.equals(s)) {
                        return false;
                    }
                }
                return true;
            }
        };
        //SerializerFeature.DisableCircularReferenceDetect -- ???????????????????????????
        return JSON.toJSONString(obj, filter, SerializerFeature.DisableCircularReferenceDetect);
        // ???????????????????????????
        //JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd HH:mm:ss.SSS")
    }






    /**
     *
     * @param decHead
     * @param printType bgdHdd;goodsFjy;jzx
     *
     * @param ieFlag i e
     * @param cusType
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
//    public Map decPrint(DecHead decHead, String printType, String ieFlag, String cusType) throws Exception {
//        //DecHead decHead = getDecHeadById(id);
//        if(null==decHead){
//            return null;
//        }
//        Map<String, Object> result = modelToMap(decHead);
//
//        PrintTag printTag = new PrintTag();
//        printTag.putTagByType(result,ieFlag,cusType,printType);
//
//        if(StringUtils.isEmpty(printType)){
//            printType="bgdHdd";
//        }
//        List<XWPFTemplate> xwpfTemplates = new ArrayList<>();
//        //  XWPFTemplate template = null;
//        //
//        if(printType.contains("bgdHdd")){
//            //?????????
//            int goodSize = decHead.getDecLists() == null ? 0 : decHead.getDecLists().size();
//            int pageSize = 1;
//            if (goodSize > 6) {//?????????????????????
//                pageSize = pageSize + (int) Math.ceil((double) (goodSize - 6) / 14);
//            }
//            result.put("page", "1/" + pageSize);
//            result.put("goods_table", decHead.getDecLists());
//            BigDecimal zjTotalNum = BigDecimal.ZERO;
//            //??????????????????
//            BigDecimal cjslTotalNum = BigDecimal.ZERO;
//            //??????????????????
//            BigDecimal fdslTotalNum = BigDecimal.ZERO;
//            //??????????????????
//            BigDecimal deslTotalNum = BigDecimal.ZERO;
//
//            List<DecList> decLists = decHead.getDecLists();
//            for(int i = 0; i < decLists.size(); i++){
//                DecList decList = decLists.get(i);
//                BigDecimal declTotal = new BigDecimal(decList.getDeclTotal() == null ? "0" : decList.getDeclTotal());
//                BigDecimal gqty = new BigDecimal(decList.getGqty() == null ? "0" : decList.getGqty());
//                BigDecimal firstQty = new BigDecimal(decList.getFirstQty() == null ? "0" : decList.getFirstQty());
//                BigDecimal secondQty = new BigDecimal(decList.getSecondQty() == null ? "0" : decList.getSecondQty());
//
//                zjTotalNum = zjTotalNum.add(declTotal);
//                cjslTotalNum = cjslTotalNum.add(gqty);
//                fdslTotalNum = fdslTotalNum.add(firstQty);
//                deslTotalNum = deslTotalNum.add(secondQty);
//            }
//            BigDecimal zjTotal = zjTotalNum.setScale(5, BigDecimal.ROUND_HALF_UP);
//            BigDecimal cjslTotalInt = cjslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
//            BigDecimal fdslTotalInt = fdslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
//            BigDecimal deslTotalInt = deslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
//
//            String zjTotalString = String.valueOf(zjTotal);
//            String cjslTotalString = cjslTotalInt.toString();
//            String fdslTotalString = fdslTotalInt.toString();
//            String deslTotalString = deslTotalInt.toString();
//            String tips = "??????" + zjTotalString + ",??????????????????" + cjslTotalString + ",??????????????????" + fdslTotalString + ",??????????????????" + deslTotalString;
//            result.put("tips", tips);
//
//            if("I".equals(ieFlag)&& StringUtils.isNotEmpty(decHead.getGoodsPlace())){
//                result.put("goodsPlace_4i", decHead.getGoodsPlace());
//
//            }
//            Configure config = Configure.newBuilder().customPolicy("goods_table", new GoodsTablePolicy(ieFlag, cusType)).build();
//            URL url = this.getClass().getClassLoader().getResource("");// this.getClass().getClassLoader().getResource("printTemp\\bgdHdd1.docx");
//
//            XWPFTemplate template = XWPFTemplate.compile(url.getPath()+"printTemp/bgdHdd1.docx", config).render(result);
//
//           /* if(printType.indexOf("bgdHdd")==0){//??????????????????????????????
//                template= template1;
//            }*/
//
//            NiceXWPFDocument doc = template.getXWPFDocument();
//            for (int i = 1; i < pageSize; i++) {
//                result.put("page", (i + 1) + "/" + pageSize);
//                config.customPolicy("goods_more", new GoodsMoreTablePolicy(4, 6 + (i - 1) * 14, 3, 14, ieFlag, cusType));
//                result.put("goods_more", decHead.getDecLists());
//                URL url2 = this.getClass().getClassLoader().getResource("");
//                XWPFTemplate template2 = XWPFTemplate.compile(url2.getPath()+"printTemp/bgdHdd2.docx", config).render(result);
//
//                XWPFParagraph p = doc.createParagraph();
//                p.setPageBreak(true);
//
//                doc = doc.merge(template2.getXWPFDocument());
//                template.reload(doc);
//            }
//            xwpfTemplates.add(template);
//        }
//
//        //???????????????
//        if (printType.contains("goodsFjy")) {
//            //?????????
//            int goodSize = decHead.getDecLists() == null ? 0 : decHead.getDecLists().size();
//            int pageSize = 1;
//            if (goodSize > 3) {//?????????????????????
//                pageSize = pageSize + (int) Math.ceil((double) (goodSize - 3) / 5);
//            }
//            //tips
//            //??????
//            BigDecimal zjTotalNum = BigDecimal.ZERO;
//            //??????????????????
//            BigDecimal cjslTotalNum = BigDecimal.ZERO;
//            //??????????????????
//            BigDecimal fdslTotalNum = BigDecimal.ZERO;
//            //??????????????????
//            BigDecimal deslTotalNum = BigDecimal.ZERO;
//
//            List<DecList> decLists = decHead.getDecLists();
//            for(int i = 0; i < decLists.size(); i++){
//                DecList decList = decLists.get(i);
//                BigDecimal declTotal = new BigDecimal(decList.getDeclTotal() == null ? "0" : decList.getDeclTotal());
//                BigDecimal gqty = new BigDecimal(decList.getGqty() == null ? "0" : decList.getGqty());
//                BigDecimal firstQty = new BigDecimal(decList.getFirstQty() == null ? "0" : decList.getFirstQty());
//                BigDecimal secondQty = new BigDecimal(decList.getSecondQty() == null ? "0" : decList.getSecondQty());
//
//                zjTotalNum = zjTotalNum.add(declTotal);
//                cjslTotalNum = cjslTotalNum.add(gqty);
//                fdslTotalNum = fdslTotalNum.add(firstQty);
//                deslTotalNum = deslTotalNum.add(secondQty);
//            }
//            BigDecimal zjTotal = zjTotalNum.setScale(5, BigDecimal.ROUND_HALF_UP);
//            BigDecimal cjslTotalInt = cjslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
//            BigDecimal fdslTotalInt = fdslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
//            BigDecimal deslTotalInt = deslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
//
//            String zjTotalString = String.valueOf(zjTotal);
//            String cjslTotalString = cjslTotalInt.toString();
//            String fdslTotalString = fdslTotalInt.toString();
//            String deslTotalString = deslTotalInt.toString();
//
//            String tips = "??????" + zjTotalString + ",??????????????????" + cjslTotalString + ",??????????????????" + fdslTotalString + ",??????????????????" + deslTotalString;
//            result.put("tips", tips);
//
//
//
//            result.put("page", "1/" + pageSize);
//            result.put("goods_table", decHead.getDecLists());
//
//            Configure config = Configure.newBuilder().customPolicy("goods_table", new FjyTablePolicy(ieFlag, cusType)).build();
//            URL url = this.getClass().getClassLoader().getResource("");
//
//            XWPFTemplate template = XWPFTemplate.compile(url.getPath() + "printTemp/goodsFjy1.docx", config).render(result);
//
//            /*if (printType.indexOf("goodsFjy") == 0) {//??????????????????????????????
//                template = template1;
//
//            }*/
//
//            NiceXWPFDocument doc = template.getXWPFDocument();
//           /* if (printType.indexOf("goodsFjy") > 0) {//?????????????????????????????????
//                XWPFParagraph p = doc.createParagraph();
//                p.setPageBreak(true);
//                doc = doc.merge(template1.getXWPFDocument());
//                template.reload(doc);
//            }*/
//
//            for (int i = 1; i < pageSize; i++) {
//                result.put("page", (i + 1) + "/" + pageSize);
//                config.customPolicy("goods_more", new FjyMoreTablePolicy(3, 3 + (i - 1) * 5, 6, 5, ieFlag, cusType));
//                result.put("goods_more", decHead.getDecLists());
//                URL url2 = this.getClass().getClassLoader().getResource("");
//                XWPFTemplate template2 = XWPFTemplate.compile(url2.getPath() + "printTemp/goodsFjy2.docx", config).render(result);
//                XWPFParagraph p = doc.createParagraph();
//                p.setPageBreak(true);
//
//                doc = doc.merge(template2.getXWPFDocument());
//                template.reload(doc);
//            }
//            xwpfTemplates.add(template);
//        }
//
//
//        result.put("page","");
//        //?????????
//        if (printType.contains("jzx")) {
//            result.put("detail_table", decHead.getDecContainers());
//
//            //????????????
//            Calendar calendar = Calendar.getInstance();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");// ????????????????????????,?????????yyyy-MM-dd
//            String dateStr = df.format(calendar.getTime());
//
////            Date now=new Date();
////            String dateStr = df.format(now);
//            result.put("printDate", dateStr);
//
//            Configure config = Configure.newBuilder().customPolicy("detail_table", new DecContainerPolicy()).build();
//            URL url = this.getClass().getClassLoader().getResource("");
//            XWPFTemplate template = XWPFTemplate.compile(url.getPath()+"printTemp/containers.docx", config).render(result);
//          /*  if (printType.indexOf("jzx") == 0) {//????????????????????????????????????
//                template = template1;
//            }*/
//           /* if (printType.indexOf("jzx") > 0) {//?????????bushi ????????????????????????
//                NiceXWPFDocument doc = template.getXWPFDocument();
//                //?????????????????????
//                XWPFParagraph p = doc.createParagraph();
//                p.setPageBreak(true);
//                //?????????????????????
////                XWPFRun run = doc.getLastParagraph().createRun();
////                run.addBreak(BreakType.PAGE);
//                NiceXWPFDocument  doc2= template1.getXWPFDocument();
//                doc = doc.merge(doc2);
//                template.reload(doc);
//            }*/
//            xwpfTemplates.add(template);
//        }
//
//        //????????????
//        if(printType.contains("jyjy")){
//            int goodSize = decHead.getDecLists() == null ? 0 : decHead.getDecLists().size();
//            int pageSize = 1;
//            if (goodSize > 3) {//?????????????????????
//                pageSize = pageSize + (int) Math.ceil((double) (goodSize - 3) / 5);
//            }
//            result.put("page", "1/" + pageSize);
//            result.put("detail_table", decHead.getDecLists());
//
//            Configure config = Configure.newBuilder().customPolicy("detail_table", new JyjyPolicy()).build();
//            URL url = this.getClass().getClassLoader().getResource("");
//            XWPFTemplate template = XWPFTemplate.compile(url.getPath()+"printTemp/jyjy.docx", config).render(result);
//
//            xwpfTemplates.add(template);
//        }
//
//        if(printType.contains("cytzs")){
//            //???????????????
//            int goodSize = decHead.getDecLists() == null ? 0 : decHead.getDecLists().size();
//            int pageSize = 1;
//            if (goodSize > 20) {//?????????????????????
//                pageSize = pageSize + (int) Math.ceil((double) (goodSize - 20) / 30);
//            }
//            result.put("page", "???1???/???" + pageSize + "???");
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy???MM???dd???");// ????????????????????????,?????????yyyy-MM-dd
//            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy???MM???dd??? HH:mm:ss");// ????????????????????????,?????????yyyy-MM-dd
//            //????????????
//            Date cyDate = decHead.getDecLogs().get(0).getOperDate();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(cyDate);
//            String operDate =  sdf.format(calendar.getTime());
//            result.put("operDate", operDate);
//
//            //????????????
//            Calendar calendar2 = Calendar.getInstance();
//            String dateStr = sdf.format(calendar2.getTime());
//            result.put("printDate1", dateStr);
//            String dateStr2 = sdf2.format(calendar2.getTime());
//            result.put("printDate2", dateStr2);
//
//            //????????????
//            List<DecLicenseDocu> decLicenseDocus = decHead.getDecLicenseDocus();
//            if(null!=decLicenseDocus&& decLicenseDocus.size()>0){
//                String dz_bh = "";
//                for (int i = 0; i < decLicenseDocus.size(); i++) {
//                    dz_bh+= decLicenseDocus.get(i).getDocuCode_value()+" ";
//                }
//
//                result.put("dz", dz_bh);
//            }
//            //????????????
//            if (decHead.getDecLists().size() > 0) {
//                String useTo = decHead.getDecLists().get(0).getUseTo();
//                result.put("useTo", useTo);
//            }
//
//            result.put("goods_table", decHead.getDecLists());
//
//
//
//
//            Configure config = Configure.newBuilder().customPolicy("goods_table", new CytzsTablePolicy(ieFlag, cusType)).build();
//            URL url = this.getClass().getClassLoader().getResource("");// this.getClass().getClassLoader().getResource("printTemp\\bgdHdd1.docx");
//
//            XWPFTemplate template = XWPFTemplate.compile(url.getPath()+"printTemp/cytzs.docx", config).render(result);
//
//           /* if(printType.indexOf("bgdHdd")==0){//??????????????????????????????
//                template= template1;
//            }*/
//
//            NiceXWPFDocument doc = template.getXWPFDocument();
//            for (int i = 1; i < pageSize; i++) {
//                result.put("page", (i + 1) + "/" + pageSize);
//                config.customPolicy("goods_more", new GoodsMoreTablePolicy(4, 6 + (i - 1) * 14, 3, 14, ieFlag, cusType));
//                result.put("goods_more", decHead.getDecLists());
//                URL url2 = this.getClass().getClassLoader().getResource("");
//                XWPFTemplate template2 = XWPFTemplate.compile(url2.getPath()+"printTemp/bgdHdd2.docx", config).render(result);
//
//                XWPFParagraph p = doc.createParagraph();
//                p.setPageBreak(true);
//
//                doc = doc.merge(template2.getXWPFDocument());
//                template.reload(doc);
//            }
//            xwpfTemplates.add(template);
//        }
//
//        //region ?????????
//        if(printType.contains("bgdm")){
//            //?????????
//            int goodSize = decHead.getDecLists() == null ? 0 : decHead.getDecLists().size();
//            int pageSize = 1;
//            if (goodSize > 6) {//?????????????????????
//                pageSize = pageSize + (int) Math.ceil((double) (goodSize - 6) / 14);
//            }
//            result.put("page", "1/" + pageSize);
//            result.put("goods_table", decHead.getDecLists());
//            if("I".equals(ieFlag)&& StringUtils.isNotEmpty(decHead.getGoodsPlace())){
//                result.put("goodsPlace_4i", decHead.getGoodsPlace());
//
//            }
//            Configure config = Configure.newBuilder().customPolicy("goods_table", new GoodsTablePolicy(ieFlag, cusType)).build();
//            URL url = this.getClass().getClassLoader().getResource("");// this.getClass().getClassLoader().getResource("printTemp\\bgdHdd1.docx");
//            String docName = "bgd1.docx";
//            XWPFTemplate template = XWPFTemplate.compile(url.getPath()+"printTemp/"+docName, config).render(result);
//
//            NiceXWPFDocument doc = template.getXWPFDocument();
//            for (int i = 1; i < pageSize; i++) {
//                result.put("page", (i + 1) + "/" + pageSize);
//                config.customPolicy("goods_more", new GoodsMoreTablePolicy(4, 6 + (i - 1) * 14, 3, 14, ieFlag, cusType));
//                result.put("goods_more", decHead.getDecLists());
//                URL url2 = this.getClass().getClassLoader().getResource("");
//
//                XWPFTemplate template2 = XWPFTemplate.compile(url2.getPath()+"printTemp/bgd2.docx", config).render(result);
//
//                XWPFParagraph p = doc.createParagraph();
//                p.setPageBreak(true);
//
//                doc = doc.merge(template2.getXWPFDocument());
//                template.reload(doc);
//            }
//            xwpfTemplates.add(template);
//        }
//        //endregion
////region???????????????
//        if (printType.contains("fxtzs")) {
//
//            result.put("detail_table", decHead.getDecLists());
//            if("I".equals(decHead.getIeFlag())){
//                result.put("inRatio_payWay", decHead.getInRatio());
//            }else{
//                result.put("inRatio_payWay", decHead.getPayWay_value());
//            }
//            if(decHead.getDecLists().size() >0){
//                DecList d = decHead.getDecLists().get(0);
//                result.put("fx_districtCode", d.getDistrictCode());
//                result.put("fx_districtCode_value", d.getDistrictCode_value());
//            }
//            if(decHead.getDecContainers().size() > 0){
//                DecContainer c = decHead.getDecContainers().get(0);
//                String cc= c.getContainerId() +"*" + decHead.getDecContainers().size() + "("+decHead.getDecContainerNum()+")";
//                result.put("fx_containerId", cc);
//            }
//            Configure config = Configure.newBuilder().customPolicy("detail_table", new FxtzsPolicy(ieFlag,cusType)).build();
//            URL url = this.getClass().getClassLoader().getResource("");
//            String docName = "";
//
//            docName = "fxtzs.docx";
//
//            XWPFTemplate template = XWPFTemplate.compile(url.getPath() + "printTemp/"+docName, config).render(result);
//
//            xwpfTemplates.add(template);
//        }
//        //endregion
//
//        return outPutFile(decHead, xwpfTemplates);
//    }
//
//
//
//
//    public QpDecHeadDto getDownloadDecHeadById(String id) {
//
//        QpDecHead qpDecHead =   qpDecHeadMapper.selectById(id);
//        qpDecHead.setPreentryid(qpDecHead.getSpdecseqno());
//        if(null!= qpDecHead){
//            QpDecHeadDto qpDecHeadDto = new QpDecHeadDto();
//            BeanUtils.copyProperties(qpDecHead, qpDecHeadDto);
//            //--??????????????????
//            QueryWrapper<QpDecList> decListQueryWrapper = new QueryWrapper<>();
//            decListQueryWrapper.eq("headid", id);
//            List<QpDecList>  qpDecLists =  qpDecListService.list(decListQueryWrapper);
//            if(!CollectionUtils.isEmpty(qpDecLists)){
//                Collections.sort(qpDecLists, new Comparator<QpDecList>() {
//                    public int compare(QpDecList arg0, QpDecList arg1) {
//                        int hits0 = arg0.getGno() == null ? 0 : Integer.parseInt(arg0.getGno());
//                        int hits1 = arg1.getGno() == null ? 0 : Integer.parseInt(arg1.getGno());
//                        if (hits1 < hits0) {
//                            return 1;
//                        } else if (hits1 == hits0) {
//                            return 0;
//                        } else {
//                            return -1;
//                        }
//                    }
//                });
//                qpDecHeadDto.setQpDecLists(qpDecLists);
//            }
//
//            //        --?????????????????????
//            QueryWrapper<QpDecContainer> qpDecContainerQueryWrapper = new QueryWrapper<>();
//            qpDecContainerQueryWrapper.eq("headid", id);
//            List<QpDecContainer>  qpDecContainerList =  qpDecContainerService.list(qpDecContainerQueryWrapper);
//            if(!CollectionUtils.isEmpty(qpDecContainerList)) {
//                if(qpDecContainerList.size()>8){
//                    Collections.sort(qpDecContainerList, new Comparator<QpDecContainer>() {
//                        public int compare(QpDecContainer arg0, QpDecContainer arg1) {
//                            return arg0.getContainerid().compareTo(arg1.getContainerid());
//                        }
//                    });
//                }
//                qpDecHeadDto.setQpDecContainerList(qpDecContainerList);
//            }
//
////        --????????????
//            QueryWrapper<QpDecLicenseDocu> qpDecLicenseDocuQueryWrapper = new QueryWrapper<>();
//            qpDecLicenseDocuQueryWrapper.eq("headid", id);
//            List<QpDecLicenseDocu>  qpDecLicenseDocuList =  qpDecLicenseDocuService.list(qpDecLicenseDocuQueryWrapper);
//            if(!CollectionUtils.isEmpty(qpDecLicenseDocuList)) {
//                qpDecHeadDto.setQpDecLicenseDocuList(qpDecLicenseDocuList);
//            }
//
////        --??????????????????  ???????????????
//            QueryWrapper<QpDecFreeTxt> qpDecFreeTxtQueryWrapper = new QueryWrapper<>();
//            qpDecFreeTxtQueryWrapper.eq("headid", id);
//            QpDecFreeTxt  qpDecFreeTxt =  qpDecFreeTxtService.getOne(qpDecFreeTxtQueryWrapper);
//            if(!ObjectUtils.isEmpty(qpDecFreeTxt)) {
//                qpDecHeadDto.setQpDecFreeTxt(qpDecFreeTxt);
//            }
//            //QpDecRequestCert
//            QueryWrapper<QpDecRequestCert> qpDecRequestCertQueryWrapper = new QueryWrapper<>();
//            qpDecRequestCertQueryWrapper.eq("headid", id);
//            List<QpDecRequestCert> qpDecRequestCertList  =  qpDecRequestCertService.list(qpDecRequestCertQueryWrapper);
//            if(!CollectionUtils.isEmpty(qpDecRequestCertList)) {
//                qpDecHeadDto.setQpDecRequestCertList(qpDecRequestCertList);
//            }
//
//            //QpDecOtherPack
//            QueryWrapper<QpDecOtherPack> qpDecOtherPackQueryWrapper = new QueryWrapper<>();
//            qpDecOtherPackQueryWrapper.eq("headid", id);
//            List<QpDecOtherPack> qpDecOtherPacks  =  qpDecOtherPackService.list(qpDecOtherPackQueryWrapper);
////            if(!CollectionUtils.isEmpty(qpDecRequestCertList)) {
////                qpDecHeadDto.setQpDecRequestCertList(qpDecRequestCertList);
////            }
//            //QpDecCopLimit
//            QueryWrapper<QpDecCopLimit> qpDecCopLimitQueryWrapper = new QueryWrapper<>();
//            qpDecCopLimitQueryWrapper.eq("headid", id);
//            List<QpDecCopLimit> qpDecCopLimits  =  qpDecCopLimitService.list(qpDecCopLimitQueryWrapper);
////            if(!CollectionUtils.isEmpty(qpDecRequestCertList)) {
////                qpDecHeadDto.setQpDecRequestCertList(qpDecRequestCertList);
////            }
//
//
//            //QpDecCopLimit
//            QueryWrapper<QpDecCopPromise> qpDecCopPromiseQueryWrapper = new QueryWrapper<>();
//            qpDecCopPromiseQueryWrapper.eq("headid", id);
//            List<QpDecCopPromise> qpDecCopPromises  =  qpDecCopPromiseService.list(qpDecCopPromiseQueryWrapper);
////            if(!CollectionUtils.isEmpty(qpDecRequestCertList)) {
////                qpDecHeadDto.setQpDecRequestCertList(qpDecRequestCertList);
////            }
//
//
//
//            //????????????
//            DecHead decHead = new DecHead();
//            Struts2Utils.copyNonNullProperties(qpDecHead,decHead);
//
//            //??????
//            List<DecList> decLists = new ArrayList<>();
//            for (int i = 0; i < qpdecLists.size() ; i++) {
//                DecList decList = new DecList();
//                QpDecList qpDecList = qpdecLists.get(i);
//                Struts2Utils.copyNonNullProperties(qpDecList, decList);
//
//                List<QpDecGoodsLimit> qpDecGoodsLimits = qpDecList.getQpDecGoodsLimits();
//                List<DecGoodsLimit> decGoodsLimits = new ArrayList<>();
//                for (int j = 0; j < qpDecGoodsLimits.size(); j++) {
//                    DecGoodsLimit decGoodsLimit = new DecGoodsLimit();
//                    QpDecGoodsLimit qpDecGoodsLimit = qpDecGoodsLimits.get(j);
//                    Struts2Utils.copyNonNullProperties(qpDecGoodsLimit, decGoodsLimit);
//
//                    List<QpDecGoodsLimitVin> qpDecGoodsLimitVins = qpDecGoodsLimit.getQpDecGoodsLimitVins();
//                    List<DecGoodsLimitVin> decGoodsLimitVins = new ArrayList<>();
//                    for (int k = 0; k < qpDecGoodsLimitVins.size(); k++) {
//                        QpDecGoodsLimitVin qpDecGoodsLimitVin = qpDecGoodsLimitVins.get(k);
//                        DecGoodsLimitVin decGoodsLimitVin = new DecGoodsLimitVin();
//                        Struts2Utils.copyNonNullProperties(qpDecGoodsLimitVin, decGoodsLimitVin);
//                        decGoodsLimitVins.add(decGoodsLimitVin);
//                    }
//                    decGoodsLimit.setDecGoodsLimitVins(decGoodsLimitVins);
//
//                    decGoodsLimits.add(decGoodsLimit);
//                }
//                decList.setDecGoodsLimits(decGoodsLimits);
//
//                decLists.add(decList);
//            }
//            decHead.setDecLists(decLists);
//
//            //?????????
//            List<DecContainer> decContainers= new ArrayList<>();
//            for (int i = 0; i < qpdecContainers.size(); i++) {
//                DecContainer decContainer = new DecContainer();
//                Struts2Utils.copyNonNullProperties(qpdecContainers.get(i), decContainer);
//                decContainers.add(decContainer);
//            }
//            decHead.setDecContainers(decContainers);
//
//            DecFreeTxt decFreeTxt = new DecFreeTxt();
//            Struts2Utils.copyNonNullProperties(qpDecFreeTxt, decFreeTxt);
//            decHead.setDecFreeTxt(decFreeTxt);
//
//            //????????????
//            List<DecLicenseDocu> decLicenseDocus = new ArrayList<>();
//            for (int i = 0; i < qpDecLicenseDocus.size(); i++) {
//                DecLicenseDocu decLicenseDocu = new DecLicenseDocu();
//                Struts2Utils.copyNonNullProperties(qpDecLicenseDocus.get(i), decLicenseDocu);
//                decLicenseDocus.add(decLicenseDocu);
//            }
//            decHead.setDecLicenseDocus(decLicenseDocus);
//
//
//            List<DecRequestCert> decRequestCerts = new ArrayList<>();
//            for (int i = 0; i < qpDecRequestCerts.size(); i++) {
//                DecRequestCert decLicenseDocu = new DecRequestCert();
//                Struts2Utils.copyNonNullProperties(qpDecRequestCerts.get(i), decLicenseDocu);
//                decRequestCerts.add(decLicenseDocu);
//            }
//            decHead.setDecRequestCerts(decRequestCerts);
//
//
//            List<DecOtherPack> decOtherPacks = new ArrayList<>();
//            for (int i = 0; i <qpDecOtherPacks.size() ; i++) {
//                DecOtherPack decOtherPack = new DecOtherPack();
//                Struts2Utils.copyNonNullProperties(qpDecOtherPacks.get(i), decOtherPack);
//                decOtherPacks.add(decOtherPack);
//            }
//            decHead.setDecOtherPacks(decOtherPacks);
//
//
//            List<DecCopLimit> decCopLimits = new ArrayList<>();
//            for (int i = 0; i < qpDecCopLimits.size(); i++) {
//                DecCopLimit decCopLimit = new DecCopLimit();
//                Struts2Utils.copyNonNullProperties(qpDecCopLimits.get(i), decCopLimit);
//                decCopLimits.add(decCopLimit);
//            }
//            decHead.setDecCopLimits(decCopLimits);
//
//            List<DecCopPromise> decCopPromises = new ArrayList<>();
//            for (int i = 0; i < qpDecCopPromises.size(); i++) {
//                DecCopPromise decCopPromise = new DecCopPromise();
//                Struts2Utils.copyNonNullProperties(qpDecCopPromises.get(i), decCopPromise);
//                decCopPromises.add(decCopPromise);
//            }
//            decHead.setDecCopPromises(decCopPromises);
//
//            return decHead;
//        }else {
//            return null;
//        }
//    }



}

