package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.dto.QpDecHeadDto;
import com.nteport.admin.entity.*;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.QpDecHeadMapper;
import com.nteport.admin.service.*;
import com.nteport.admin.service.system.IPageHelper;

import com.nteport.admin.service.system.IQpDecRequestCertService;
import com.nteport.admin.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户管理服务
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



    SimpleDateFormat simformat = new SimpleDateFormat("yyMMddhhmmss");


    /**
     * 获取用户列表，带分页
     *
     * @param params Map[pageNum 当前页数, pageSize 每页大小]
     * @return Json [list 用户列表，total 结果总数]
     * 或 状态码
     */
    @Override
    public ApiResponse listPage(Map<String, String> params, UserEntity user) {
        if (!params.containsKey("page") || !params.containsKey("limit")) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        // 组装分页设置
        long pageNum = Long.parseLong(params.get("page"));
        long pageSize = Long.parseLong(params.get("limit"));
        IPage<QpDecHead> userPage = new Page<>(pageNum, pageSize);
//        Map params = json.getInnerMap();

        // 组装查询条件
        IPageHelper<QpDecHead> pageHelper = new PageHelperImpl<>();

        String startTime =  params.get("startTime");
        params.remove("startTime");
        String endTime =  params.get("endTime");
        params.remove("endTime");

        QueryWrapper<QpDecHead> queryWrapper = new QueryWrapper<>();
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
        //根据登录人员区县过滤
//        if(user.getRoleCodes().contains(ConstantUtil.ROLE_ADMIN) ||user.getRoleCodes().contains(ConstantUtil.ROLE_CITYADMIN)){
//        }else if(user.getRoleCodes().contains(ConstantUtil.ROLE_QXDSBADMIN)){
//            List<String> deptIds = deptService.queryAreaDept(user.getDeptId());
//            queryWrapper.in("dept_id",deptIds);
//        }

//        queryWrapper.orderByAsc("dept_id");

        // 查询结果

        userPage = qpDecHeadMapper.selectPage(userPage, queryWrapper);
        List<QpDecHead> list = userPage.getRecords();
        Long total = qpDecHeadMapper.selectCount(queryWrapper);

        JSONObject data = new JSONObject();
        // 组装json数据
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
        //--报关单商品表
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

//        --报关单集装箱表
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

//        --随附单据
        QueryWrapper<QpDecLicenseDocu> qpDecLicenseDocuQueryWrapper = new QueryWrapper<>();
        qpDecLicenseDocuQueryWrapper.eq("headid", headId);
        List<QpDecLicenseDocu>  qpDecLicenseDocuList =  qpDecLicenseDocuService.list(qpDecLicenseDocuQueryWrapper);
        if(!CollectionUtils.isEmpty(qpDecLicenseDocuList)) {
            qpDecHeadDto.setQpDecLicenseDocuList(qpDecLicenseDocuList);
        }

//        --自由文本信息  管联报关单
        QueryWrapper<QpDecFreeTxt> qpDecFreeTxtQueryWrapper = new QueryWrapper<>();
        qpDecFreeTxtQueryWrapper.eq("headid", headId);
        QpDecFreeTxt  qpDecFreeTxt =  qpDecFreeTxtService.getOne(qpDecFreeTxtQueryWrapper);
        if(!ObjectUtils.isEmpty(qpDecFreeTxt)) {
            qpDecHeadDto.setQpDecFreeTxt(qpDecFreeTxt);
        }

        return ApiResponse.success(qpDecHeadDto);
    }


    @Override
    public void exportExcel(Map<String, String> params, HttpServletRequest request ,HttpServletResponse response) {
        try {
            //设置excel表头
            HashMap param = new HashMap<>();

            //

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
            decHeadDownExcelHelper.closeInputStream();  //释放资源

            if (decHeadDownExcelHelper.wb != null) {
                // 下载
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
                        "报关单导出" + simformat.format(new Date()) + ".xlsx",
                        "UTF-8");
                response.addHeader("Content-Disposition",
                        "attachment;filename=\"" + newName + "\"");
                response.addHeader("Content-Length", "" + decHeadDownExcelHelper.newFile.length());
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            }




            //填入值
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
//            ExcelUtil.exportExcel(response, "报关单导出.xlsx", titleList, dataList, 5000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public ApiResponse printDec(Map<String, String> params) {


        return null;
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
//    public Map decPrint(String id, String printType, String ieFlag, String cusType) throws Exception {
////        String pageTy = getRequest().getParameter("pageTy");
////        String nameTypeValue = getRequest().getParameter("nameTypeValue");
////        String nameType = getRequest().getParameter("nameType");
////        String nameM = getRequest().getParameter("nameM");
////        String isDzz = getRequest().getParameter("isDzz");
////        String isPl = getRequest().getParameter("isPl");
////        String customHeadId = getRequest().getParameter("customHeadId");
////        UserInfo user = (UserInfo) Struts2Utils.getSession().getAttribute(SessionUtil.loginInfo);
//        String customNo="3206980004";
////        if(null!=user){
////            OrgInfo orgInfo = user.getOrgInfo();
////            customNo = orgInfo.getCustomNo();
////        }
//        Map paramMap = new HashMap();
////        paramMap.put("pageTy",pageTy);
////        paramMap.put("nameTypeValue",nameTypeValue);
////        paramMap.put("nameType",nameType);
////        paramMap.put("nameM",nameM);
////        paramMap.put("isDzz",isDzz);
////        paramMap.put("isPl",isPl);
//        paramMap.put("customNo",customNo);
//        paramMap.put("printType",printType);
//        paramMap.put("ieFlag",ieFlag);
//        paramMap.put("cusType",cusType);
//        paramMap.put("id",id);
//
//        DecHead decHead = getDecHeadById(id);
//        if(null==decHead){
//            return null;
//        }
//
//
//
//        String cusSql = "select t.id from CUSTOMSHEAD t where t.headid='"+id+"'  ";
//        List cusIds=  daoFacade.query4Sql(cusSql,null);
//        if(cusIds.size()==1){
//            customHeadId=cusIds.get(0).toString();
//        }
//        System.out.println(customHeadId);
//        if (CommonUtils.isEmpty(pageTy)){
//            pageTy = "0";
//        }
//        //region newFileName
//        /**
//         *@Author : xw
//         *@Description : 根据类型得出newFileName的值
//         *@Date : 2019/3/11 14:04
//         */
//        String newFileName = "";
//        if("0".equals(pageTy)){
//            newFileName = decHead.getClientNo() + "_" + System.currentTimeMillis();
//        }else{
//            if(CommonUtils.isNotEmpty(nameTypeValue)){
//                if (nameTypeValue.equals("auto")) {
//                    String packNo = decHead.getPackNo();
//                    String custom = decHead.getIePort_value();
//                    if (StringUtils.isNotEmpty(custom)) {
//                        custom = custom.substring(0, 2);
//                    }
//                    if (printType.equals("bgdHdd")) {
//                        newFileName = "核对-" + custom + packNo;
//                    } else if (printType.equals("bgdm")) {
//                        newFileName = "报关-" + custom + packNo;
//                    } else if (printType.equals("fxtzs")) {
//                        newFileName = "放行-" + custom + packNo;
//                    } else if (printType.equals("fxtzs,bgdm")) {
//                        newFileName = "报关放行-" + custom + packNo;
//                    } else {
//                        newFileName = custom + packNo;
//                    }
//                } else if(nameTypeValue.equals("billNo")){
//                    //文件名billno  先看报关单的 billno
//                    if(StringUtils.isNotBlank(decHead.getBillNo()) ){
//                        newFileName=decHead.getBillNo();
//                    }else {
//                        String reHql = "select o from ReceiptSignHead o" +
//                                " where o.id= (select c.receiptSignHeadId from  CustomsHead  c where c.id='"+customHeadId+"'  ) ";
//                        List<ReceiptSignHead> reIds=  daoFacade.customQuery(reHql,null);
//                        //没有先看 接单主表有没有
//                        if(reIds.size()==1){
//                            String billNo = reIds.get(0).getBillNo();
//                            //  customHeadId=cusIds.get(0).toString();
//                            if(StringUtils.isNotBlank(billNo)){
//                                newFileName=billNo;
//                            }else{
//                                //没有就随机
//                                newFileName = decHead.getClientNo() + "_" + System.currentTimeMillis();
//                            }
//                        }else{
//                            newFileName = decHead.getClientNo() + "_" + System.currentTimeMillis();
//                        }
//                    }
//                }
//
//                // String cusSql = "select t.id from CUSTOMSHEAD t where t.headid='"+id+"'  ";
//                //        List cusIds=  daoFacade.query4Sql(cusSql,null);
//                //        if(cusIds.size()==1){
//                //            customHeadId=cusIds.get(0).toString();
//                //        }
//                //        System.out.println(customHeadId);
//                else {
//                    Class userCla = (Class) decHead.getClass();
//                    Field[] fs = userCla.getDeclaredFields();
//                    for (int i = 0; i < fs.length; i++) {
//                        Field f = fs[i];
//                        f.setAccessible(true); // 设置些属性是可以访问的
//                        try {
//                            if (f.getName().endsWith(nameTypeValue)) {
//                                if(f.get(decHead) == null || CommonUtils.isEmpty(f.get(decHead).toString())){
//                                    newFileName = decHead.getClientNo() + "_" + System.currentTimeMillis();
//                                }else{
//                                    String n = f.get(decHead).toString();
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
//                }
//            }else{
//                newFileName = decHead.getClientNo() + "_" + System.currentTimeMillis();
//            }
//        }
//        paramMap.put("newFileName",newFileName);
//
//        //endregion
//
//        Map<String, Object> result = modelToMap(decHead);
//        //查询日志的放行日期
//        String  decReceiptLogHql = " from DecReceiptLog o where o.customState='H2000P' and o.headId='"+id+"'  " +
//                " order by o.createTime desc " ;
//        List<DecReceiptLog> decReceiptLogs = daoFacade.customQuery(decReceiptLogHql,null);
//        if(null!=decReceiptLogs&& decReceiptLogs.size()>0){
//            Date fxsj = decReceiptLogs.get(0).getNoticeDate();
//            if(null!=fxsj){
//                result.put("fxjg_sj",sdf4.format(fxsj))  ;
//            }
//        }
//
//        if(CommonUtils.isNotEmpty(customHeadId)){
//            CustomsHead c = daoFacade.retrieve(CustomsHead.class,customHeadId);
//            if(c != null){
//                result.put("c_clientNo", c.getClientNo());
//            }
//        }
//        PrintTag printTag = new PrintTag();
//        printTag.putTagByType(result,ieFlag,cusType,printType);
//
//        if(StringUtils.isEmpty(printType)){
//            printType="bgdHdd";
//        }
//
//        //tips
//        //总价
//        BigDecimal zjTotalNum = BigDecimal.ZERO;
//        //成交数量合计
//        BigDecimal cjslTotalNum = BigDecimal.ZERO;
//        //法定数量合计
//        BigDecimal fdslTotalNum = BigDecimal.ZERO;
//        //第二数量合计
//        BigDecimal deslTotalNum = BigDecimal.ZERO;
//
//        List<DecList> decLists = decHead.getDecLists();
//        for(int i = 0; i < decLists.size(); i++){
//            DecList decList = decLists.get(i);
//            BigDecimal declTotal = new BigDecimal(decList.getDeclTotal() == null ? "0" : decList.getDeclTotal());
//            BigDecimal gqty = new BigDecimal(decList.getGqty() == null ? "0" : decList.getGqty());
//            BigDecimal firstQty = new BigDecimal(decList.getFirstQty() == null ? "0" : decList.getFirstQty());
//            BigDecimal secondQty = new BigDecimal(decList.getSecondQty() == null ? "0" : decList.getSecondQty());
//
//            zjTotalNum = zjTotalNum.add(declTotal);
//            cjslTotalNum = cjslTotalNum.add(gqty);
//            fdslTotalNum = fdslTotalNum.add(firstQty);
//            deslTotalNum = deslTotalNum.add(secondQty);
//        }
//        BigDecimal zjTotal = zjTotalNum.setScale(5, BigDecimal.ROUND_HALF_UP);
//        BigDecimal cjslTotalInt = cjslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
//        BigDecimal fdslTotalInt = fdslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
//        BigDecimal deslTotalInt = deslTotalNum.setScale(5,BigDecimal.ROUND_HALF_UP);
//
//        String zjTotalString = String.valueOf(zjTotal);
//        String cjslTotalString = cjslTotalInt.toString();
//        String fdslTotalString = fdslTotalInt.toString();
//        String deslTotalString = deslTotalInt.toString();
//
//        String tips = "总价" + zjTotalString + ",成交数量合计" + cjslTotalString + ",法定数量合计" + fdslTotalString + ",第二数量合计" + deslTotalString;
//        result.put("tips", tips);
//
//
//        //endregion
//        Map httpParam=new HashMap();
//        httpParam.put("data",result);
//        httpParam.put("param",paramMap);
//        String str =Struts2Utils.toJSONString(httpParam);
//        System.out.println(str);
//        getDaoFacade().getEntityManager().detach(decHead);//断开连接
//        return HttpClientUtil.HttpPost(str,PropertyHolder.getProperty("printServer"));
//    }




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
//            //核对单
//            int goodSize = decHead.getDecLists() == null ? 0 : decHead.getDecLists().size();
//            int pageSize = 1;
//            if (goodSize > 6) {//需要添加附加页
//                pageSize = pageSize + (int) Math.ceil((double) (goodSize - 6) / 14);
//            }
//            result.put("page", "1/" + pageSize);
//            result.put("goods_table", decHead.getDecLists());
//            BigDecimal zjTotalNum = BigDecimal.ZERO;
//            //成交数量合计
//            BigDecimal cjslTotalNum = BigDecimal.ZERO;
//            //法定数量合计
//            BigDecimal fdslTotalNum = BigDecimal.ZERO;
//            //第二数量合计
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
//            String tips = "总价" + zjTotalString + ",成交数量合计" + cjslTotalString + ",法定数量合计" + fdslTotalString + ",第二数量合计" + deslTotalString;
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
//           /* if(printType.indexOf("bgdHdd")==0){//一开始以核对单开始的
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
//        //商品附加联
//        if (printType.contains("goodsFjy")) {
//            //核对单
//            int goodSize = decHead.getDecLists() == null ? 0 : decHead.getDecLists().size();
//            int pageSize = 1;
//            if (goodSize > 3) {//需要添加附加页
//                pageSize = pageSize + (int) Math.ceil((double) (goodSize - 3) / 5);
//            }
//            //tips
//            //总价
//            BigDecimal zjTotalNum = BigDecimal.ZERO;
//            //成交数量合计
//            BigDecimal cjslTotalNum = BigDecimal.ZERO;
//            //法定数量合计
//            BigDecimal fdslTotalNum = BigDecimal.ZERO;
//            //第二数量合计
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
//            String tips = "总价" + zjTotalString + ",成交数量合计" + cjslTotalString + ",法定数量合计" + fdslTotalString + ",第二数量合计" + deslTotalString;
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
//            /*if (printType.indexOf("goodsFjy") == 0) {//一开始以核对单开始的
//                template = template1;
//
//            }*/
//
//            NiceXWPFDocument doc = template.getXWPFDocument();
//           /* if (printType.indexOf("goodsFjy") > 0) {//一开始不是核对单开始的
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
//        //集装箱
//        if (printType.contains("jzx")) {
//            result.put("detail_table", decHead.getDecContainers());
//
//            //打印日期
//            Calendar calendar = Calendar.getInstance();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");// 设置你想要的格式,也可以yyyy-MM-dd
//            String dateStr = df.format(calendar.getTime());
//
////            Date now=new Date();
////            String dateStr = df.format(now);
//            result.put("printDate", dateStr);
//
//            Configure config = Configure.newBuilder().customPolicy("detail_table", new DecContainerPolicy()).build();
//            URL url = this.getClass().getClassLoader().getResource("");
//            XWPFTemplate template = XWPFTemplate.compile(url.getPath()+"printTemp/containers.docx", config).render(result);
//          /*  if (printType.indexOf("jzx") == 0) {//一开始以商品附加联开始的
//                template = template1;
//            }*/
//           /* if (printType.indexOf("jzx") > 0) {//一开始bushi 商品附加联开始的
//                NiceXWPFDocument doc = template.getXWPFDocument();
//                //段后添加分页符
//                XWPFParagraph p = doc.createParagraph();
//                p.setPageBreak(true);
//                //段前添加分页符
////                XWPFRun run = doc.getLastParagraph().createRun();
////                run.addBreak(BreakType.PAGE);
//                NiceXWPFDocument  doc2= template1.getXWPFDocument();
//                doc = doc.merge(doc2);
//                template.reload(doc);
//            }*/
//            xwpfTemplates.add(template);
//        }
//
//        //检验检疫
//        if(printType.contains("jyjy")){
//            int goodSize = decHead.getDecLists() == null ? 0 : decHead.getDecLists().size();
//            int pageSize = 1;
//            if (goodSize > 3) {//需要添加附加页
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
//            //查验通知单
//            int goodSize = decHead.getDecLists() == null ? 0 : decHead.getDecLists().size();
//            int pageSize = 1;
//            if (goodSize > 20) {//需要添加附加页
//                pageSize = pageSize + (int) Math.ceil((double) (goodSize - 20) / 30);
//            }
//            result.put("page", "第1页/共" + pageSize + "页");
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");// 设置你想要的格式,也可以yyyy-MM-dd
//            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// 设置你想要的格式,也可以yyyy-MM-dd
//            //查验日期
//            Date cyDate = decHead.getDecLogs().get(0).getOperDate();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(cyDate);
//            String operDate =  sdf.format(calendar.getTime());
//            result.put("operDate", operDate);
//
//            //打印日期
//            Calendar calendar2 = Calendar.getInstance();
//            String dateStr = sdf.format(calendar2.getTime());
//            result.put("printDate1", dateStr);
//            String dateStr2 = sdf2.format(calendar2.getTime());
//            result.put("printDate2", dateStr2);
//
//            //随附单证
//            List<DecLicenseDocu> decLicenseDocus = decHead.getDecLicenseDocus();
//            if(null!=decLicenseDocus&& decLicenseDocus.size()>0){
//                String dz_bh = "";
//                for (int i = 0; i < decLicenseDocus.size(); i++) {
//                    dz_bh+= decLicenseDocus.get(i).getDocuCode_value()+" ";
//                }
//
//                result.put("dz", dz_bh);
//            }
//            //生产厂家
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
//           /* if(printType.indexOf("bgdHdd")==0){//一开始以核对单开始的
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
//        //region 报关单
//        if(printType.contains("bgdm")){
//            //核对单
//            int goodSize = decHead.getDecLists() == null ? 0 : decHead.getDecLists().size();
//            int pageSize = 1;
//            if (goodSize > 6) {//需要添加附加页
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
////region放行通知书
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
//            //--报关单商品表
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
//            //        --报关单集装箱表
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
////        --随附单据
//            QueryWrapper<QpDecLicenseDocu> qpDecLicenseDocuQueryWrapper = new QueryWrapper<>();
//            qpDecLicenseDocuQueryWrapper.eq("headid", id);
//            List<QpDecLicenseDocu>  qpDecLicenseDocuList =  qpDecLicenseDocuService.list(qpDecLicenseDocuQueryWrapper);
//            if(!CollectionUtils.isEmpty(qpDecLicenseDocuList)) {
//                qpDecHeadDto.setQpDecLicenseDocuList(qpDecLicenseDocuList);
//            }
//
////        --自由文本信息  管联报关单
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
//            //组装数据
//            DecHead decHead = new DecHead();
//            Struts2Utils.copyNonNullProperties(qpDecHead,decHead);
//
//            //商品
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
//            //集装箱
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
//            //随附单据
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

