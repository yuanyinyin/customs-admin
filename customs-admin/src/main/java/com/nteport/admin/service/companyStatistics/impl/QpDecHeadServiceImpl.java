package com.nteport.admin.service.companyStatistics.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.NoticeInfo;
import com.nteport.admin.entity.TDeptEntity;
import com.nteport.admin.entity.TNoticeEntity;
import com.nteport.admin.entity.companyStatistics.QpDecHead;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.NoticeInfoMapper;
import com.nteport.admin.mapper.TNoticeMapper;
import com.nteport.admin.mapper.statistics.QpDecHeadStatisticsMapper;
import com.nteport.admin.service.companyStatistics.IQpDecHeadService;


import com.nteport.admin.util.CalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


@Service
public class QpDecHeadServiceImpl extends ServiceImpl<QpDecHeadStatisticsMapper, QpDecHead>
        implements IQpDecHeadService {
    @Autowired
    private QpDecHeadStatisticsMapper qpDecHeadMapper;

    @Autowired
    private TNoticeMapper tNoticeMapper;

    @Override
    public ApiResponse queryImOrExCustomsData(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            String qyFlag = params.get("qyFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");


            HashMap map0=new HashMap();
            map0.put("ieFlag", ieFlag);
            map0.put("qyFlag", qyFlag);
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid", user.getDeptId());

            List<Map>list = qpDecHeadMapper.queryImOrExCustomsData(map0);
            BigDecimal sum = new BigDecimal(0);

            if (list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    Map map = list.get(i);
                    sum =sum.add(new  BigDecimal((String)map.get("NUMB")));
                }
                BigDecimal average = sum.divide(new BigDecimal(list.size()) ,2,BigDecimal.ROUND_UP) ;
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).put("average",average);
                }
            }
            //组装json数据
            data.put("items", list);
            data.put("rs", list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryTransportMode(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");


            HashMap map0=new HashMap();
            map0.put("ieFlag", ieFlag);
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid", user.getDeptId());

            List<Map>list = qpDecHeadMapper.queryTransportMode(map0);
            BigDecimal sum = new BigDecimal(0);
            BigDecimal[] numb = new BigDecimal[list.size()];
            BigDecimal rate = new BigDecimal(0);

            if (list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    Map map =   list.get(i);
                    numb[i] = (BigDecimal) map.get("NUMB");
                    sum =sum.add(numb[i]);
                }

                for (int i = 0; i <list.size(); i++) {
                    rate = numb[i].divide(sum,4,BigDecimal.ROUND_HALF_UP) ;
                    list.get(i).put("rate",rate);
                    list.get(i).put("index",i+1);
                }
            }

            data.put("rs",list);
            data.put("sum",sum);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse portStatistic(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            String trafMode = params.get("trafMode");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid", user.getDeptId());
            map0.put("ieFlag", ieFlag);
            map0.put("trafMode", trafMode);

            List<Map>list = qpDecHeadMapper.portStatistic(map0);

            BigDecimal sum1 = BigDecimal.ZERO;
            BigDecimal sum2 = BigDecimal.ZERO;
            BigDecimal[] numb1 = new BigDecimal[list.size()];
            BigDecimal[] numb2 = new BigDecimal[list.size()];
            BigDecimal rate1 ;
            BigDecimal rate2 ;

            //防止起止日期相同报错
            if (list.size()>0){
                int j = 0;
                Map temporaryMap = new HashMap();
                temporaryMap.put("IEPORT",1);
                list.add(list.size(),temporaryMap);
                for (int i = 0; i < list.size()-1; i++) {
                    Map map = list.get(i);
                    if (map.get("IEPORT").equals(list.get(i + 1).get("IEPORT"))) {
                        sum1 = sum1.add((BigDecimal) map.get("NUMB"));
                    } else {
                        if (!map.get("IEPORT").equals(list.get(i + 1).get("IEPORT"))) {
                            sum1 = sum1.add((BigDecimal) map.get("NUMB"));

                            for (int k = j; k <= i; k++) {
                                numb1[k] = sum1;
                                list.get(i).put("PORTSUM", numb1[k]);
                            }
                            j = i+1;
                            sum1 = BigDecimal.ZERO;
                        }
                    }
                }
                list.remove(list.size()-1);

                //计算申报地海关占比
                for (int i = 0; i < list.size(); i++) {
                    Map map =   list.get(i);
                    numb2[i] = (BigDecimal) map.get("NUMB");
                    sum2 =sum2.add(numb2[i]);
                }
                for (int i = 0; i <list.size(); i++) {
                    rate2 = numb2[i].divide(sum2,4, RoundingMode.HALF_UP) ;
                    list.get(i).put("rate2",rate2);
                }
                //计算各口岸占比
                for (int i = 0; i < list.size(); i++) {
                    rate1 = numb1[i].divide(sum2,4, RoundingMode.HALF_UP);
                    list.get(i).put("rate1",rate1);
                }
            }
            //组装json数据
            data.put("rs",list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryTradeMode(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid", user.getDeptId());
            map0.put("ieFlag", ieFlag);

            List<Map>list = qpDecHeadMapper.queryTradeMode(map0);

            BigDecimal sum = new BigDecimal(0);
            BigDecimal[] numb = new BigDecimal[list.size()];
            BigDecimal rate = new BigDecimal(0);

            if (list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    Map map =   list.get(i);
                    numb[i] = (BigDecimal) map.get("NUMB");
                    sum =sum.add(numb[i]);
                }

                for (int i = 0; i <list.size(); i++) {
                    rate = numb[i].divide(sum,4,BigDecimal.ROUND_HALF_UP) ;
                    list.get(i).put("rate",rate);
                }
            }

            data.put("rs",list);
            data.put("sum",sum);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryGoodsTotal(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            String qyFlag = params.get("qyFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid",user.getDeptId());
            map0.put("ieFlag", ieFlag);
            map0.put("qyFlag", qyFlag);

            List<Map>list1 = qpDecHeadMapper.queryGoodsTotal1(map0);

            BigDecimal total = new BigDecimal(0);
            for (int i = 0; i < list1.size(); i++) {
                Map map = list1.get(i);
                total = total.add((BigDecimal) map.get("RMB"));
            }

            data.put("total",total);
            data.put("rs",list1);

            List<Map>list2 = qpDecHeadMapper.queryGoodsTotal2(map0);

            data.put("monRs",list2);

            //将结果汇总成新list，载入datagrid
            //当选择起始月份相同时，会出现越界报错，加入判断
            if(list2.size() !=0 ) {
                int j = 0;
                List<Map> newList = new ArrayList<>();
                Map map2;
                Map map1 = list1.get(0);
                newList.add(map1);

                for (int i = 1; i < list1.size() - 1; i++) {
                    map1 = list1.get(i);
                    newList.add(map1);
                    if (!list1.get(i).get("MON").equals(list1.get(i + 1).get("MON"))) {
                        map2 = list2.get(j);
                        map2.put("MON", "合计");
                        map2.put("CURRENCY", "---");
                        map2.put("NUMB", "---");
                        map2.put("RATE", "---");
                        newList.add(map2);
                        j++;
                    }
                }
                map1 = list1.get(list1.size() - 1);
                newList.add(map1);

                map2 = list2.get(j);
                map2.put("MON", "合计");
                map2.put("CURRENCY", "---");
                map2.put("NUMB", "---");
                map2.put("RATE", "---");
                newList.add(map2);

                data.put("newData", newList);
            }
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryTopGoods(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            String qyFlag = params.get("qyFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid",user.getDeptId());
            map0.put("qyFlag", qyFlag);
            map0.put("ieFlag", ieFlag);

            List<Map>list = qpDecHeadMapper.queryTopGoods(map0);

            data.put("rs",list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryTopFrequency(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            String qyFlag = params.get("qyFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid",user.getDeptId());
            map0.put("ieFlag", ieFlag);
            map0.put("qyFlag", qyFlag);

            List<Map>list = qpDecHeadMapper.queryTopFrequency(map0);

            data.put("rs",list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override

    public ApiResponse queryAreaData(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            String qyFlag = params.get("qyFlag");
            String keyFlag = params.get("keyFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("ieFlag", ieFlag);
            map0.put("qyFlag", qyFlag);
            map0.put("keyFlag", keyFlag);

            List<Map>list = qpDecHeadMapper.queryAreaData(map0);

            //组装json数据
            data.put("rs",list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryAreaDataByCode(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String areaCode = params.get("areaCode");
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("areaCode", areaCode);
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("ieFlag", ieFlag);

            List<Map>list = qpDecHeadMapper.queryAreaDataByCode(map0);


            //组装json数据
            data.put("rs",list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryInspectRate(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            String qyFlag = params.get("qyFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");


            HashMap map0=new HashMap();
            map0.put("ieFlag", ieFlag);
            map0.put("qyFlag", qyFlag);
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid",user.getDeptId());

            List<Map>list1 = qpDecHeadMapper.queryInspectRate(map0);


            BigDecimal rate = new BigDecimal(0);
            //当选择起始月份相同时，会出现越界报错，加入判断
            if(list1.size() !=0 ) {


                for (int i = 0; i < list1.size(); i++) {

                    map0.put("mon",list1.get(i).get("MON"));
                    List<Map>list2= qpDecHeadMapper.queryInspectRateCheck(map0);
                    if(list2.size()!=0){
                        list1.get(i).put("NUMBCHECKED",list2.get(0).get("NUMBCHECKED"));
                        int numbchecked = Integer.parseInt((String) list2.get(0).get("NUMBCHECKED"));
                        int allSum = Integer.parseInt((String)list1.get(i).get("NUMB"));
                        list1.get(i).put("NUMBUNCHECKED",allSum-numbchecked);
                        rate = BigDecimal.valueOf(numbchecked).divide(BigDecimal.valueOf(allSum),4,BigDecimal.ROUND_HALF_UP) ;
                        list1.get(i).put("rate",rate);
                    }else{
                        list1.get(i).put("NUMBCHECKED",0);
                        list1.get(i).put("NUMBUNCHECKED",list1.get(i).get("NUMB"));
                        list1.get(i).put("rate",0);
                    }

                }

            }
            data.put("rs", list1);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override

    public ApiResponse queryGoodsData(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            String qyFlag = params.get("qyFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("ieFlag", ieFlag);
            map0.put("qyFlag", qyFlag);
            map0.put("orgId", user.getDeptId());
            List<Map>list = qpDecHeadMapper.queryGoodsData(map0);

            //组装json数据
            data.put("rs",list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryGoodsDataByGNAME(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String gname = params.get("gname");
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("gname", gname);
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("ieFlag", ieFlag);

            List list = qpDecHeadMapper.queryGoodsDataByGNAME(map0);


            //组装json数据
            data.put("rs",list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryGoodsDataDetail(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String gname = params.get("gname");
            String codeTS = params.get("codeTS");
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("gname", gname);
            map0.put("codeTS", codeTS);
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("ieFlag", ieFlag);

            List list = qpDecHeadMapper.queryGoodsDataDetail(map0);


            //组装json数据
            data.put("rs",list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse queryKeyCompanyData(Map<String, String> params, UserEntity user) {
        //分页
        Long pageSize = Long.parseLong(params.get("limit"));
        Long pageNum = Long.parseLong(params.get("page"));
        try {
            //查询结果
            String orgName = String.valueOf(params.get("orgName"));
            String keyCompanyFlag = String.valueOf(params.get("keyCompanyFlag"));
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");


            HashMap map = new HashMap();

            map.put("startDate", startDate);
            map.put("endDate", endDate);
            long current = (pageNum - 1) * pageSize;
            map.put("current", current);
            map.put("pageSize", pageNum*pageSize);
            List<Map> list = qpDecHeadMapper.queryKeyCompanyDataList(map);
            Integer bidPageListByUserCount = qpDecHeadMapper.queryKeyCompanyDataListCount(map);
            //组装json数据

            JSONObject data = new JSONObject();
            data.put("items", list);
            data.put("total", bidPageListByUserCount);

            return ApiResponse.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    @Override
    public ApiResponse queryKeyCompanyDataSum(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("ieFlag", ieFlag);

            List<Map>list = qpDecHeadMapper.queryKeyCompanyDataSum(map0);

            //组装json数据
            data.put("rs",list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse queryKeyCompanyDataByCode(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String orgId = params.get("orgId");
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("orgId", orgId);
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("ieFlag", ieFlag);

            List list = qpDecHeadMapper.queryKeyCompanyDataByCode(map0);


            //组装json数据
            data.put("rs",list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse queryInspectRateDash(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            String qyFlag = params.get("qyFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");


            HashMap map0=new HashMap();
            map0.put("ieFlag", ieFlag);
            map0.put("qyFlag", qyFlag);
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid",      user.getDeptId());

            List<Map>list1 = qpDecHeadMapper.queryInspectRateDash(map0);


            data.put("rs", list1);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse queryCustomSumDash(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String ieFlag = params.get("ieFlag");
            String qyFlag = params.get("qyFlag");
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");


            HashMap map0=new HashMap();
            map0.put("ieFlag", ieFlag);
            map0.put("qyFlag", qyFlag);
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid",      user.getDeptId());

            List<Map>list1 = qpDecHeadMapper.queryCustomSumDash(map0);


            data.put("rs", list1);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryTotalCustomDataDash(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String qyFlag = params.get("qyFlag");
            String keyFlag = params.get("keyFlag");

            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");


            HashMap map0=new HashMap();
            map0.put("qyFlag", qyFlag);
            map0.put("keyFlag", keyFlag);
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid",      user.getDeptId());

            List<Map>list1 = qpDecHeadMapper.queryTotalCustomDataDash(map0);


            data.put("rs", list1);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryCalculateCustomDataDash(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String qyFlag = params.get("qyFlag");
            String keyFlag = params.get("keyFlag");

            HashMap map0=new HashMap();
            map0.put("qyFlag", qyFlag);
            map0.put("keyFlag", keyFlag);
            map0.put("orgid",      user.getDeptId());

            List<Map>list1 = qpDecHeadMapper.querySysTimeCustomData(map0);
            List<Map>list2 = qpDecHeadMapper.queryMonthCustomData(map0);
            List<Map>list3 = qpDecHeadMapper.queryYearCustomData(map0);
            List<Map>list4 = qpDecHeadMapper.queryTotalCustomData(map0);

            Map map = new HashMap();
            if(list1!=null&& list1.size()>0){
                BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(list1.get(0).get("NUMBNOWDAY")));
                BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(list1.get(0).get("NUMBBEFOREDAY")));
                String bigDecimal = CalculateUtil.percentBigDecimal(bigDecimal1,bigDecimal2);
                list1.get(0).put("rate1",bigDecimal);
                map.putAll((Map)list1.get(0));
            }
            if (list2 != null && list2.size() > 0) {
                BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(list2.get(0).get("NUMBNOWMONTH")));
                BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(list2.get(0).get("NUMBBEFOREMONTH")));
                String bigDecimal = CalculateUtil.percentBigDecimal(bigDecimal1, bigDecimal2);
                list2.get(0).put("rate2",bigDecimal);
                map.putAll((Map) list2.get(0));
            }
            if (list3 != null && list3.size() > 0) {
                BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(list3.get(0).get("NUMBNOWYEAR")));
                BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(list3.get(0).get("NUMBBEFOREYEAR")));
                String bigDecimal = CalculateUtil.percentBigDecimal(bigDecimal1, bigDecimal2);
                list3.get(0).put("rate3",bigDecimal);
                map.putAll((Map) list3.get(0));
            }
            if (list4 != null && list4.size() > 0) {
                map.putAll((Map) list4.get(0));
            }
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                String key = entry.getKey();
                String value = entry.getValue();
                if(value == null || "".equals(value)) {
                     map.put(key,"0");
                }
            }
            List list=new ArrayList();
            list.add(map);
            data.put("rs", list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse queryAreaDataDash(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String qyFlag = params.get("qyFlag");
            String keyFlag = params.get("keyFlag");

            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");


            HashMap map0=new HashMap();
            map0.put("keyFlag", keyFlag);
            map0.put("qyFlag", qyFlag);
            map0.put("orgid",      user.getDeptId());
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            List<Map>list1 = qpDecHeadMapper.queryAreaDataDash(map0);


            data.put("rs", list1);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse queryCustomDataDash(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String startDate = params.get("startDate");
            String endDate = params.get("endDate");
            String qyFlag = params.get("qyFlag");
            String keyFlag = params.get("keyFlag");

            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");


            HashMap map0=new HashMap();
            map0.put("keyFlag", keyFlag);
            map0.put("qyFlag", qyFlag);
            map0.put("orgid", user.getDeptId());
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            List<Map>list1 = qpDecHeadMapper.queryCustomDataDash(map0);


            data.put("rs", list1);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryQyUseDataDash(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {

            HashMap map0=new HashMap();
            List<Map>list1 = qpDecHeadMapper.queryQyUseDataDash(map0);
            List<Map>list2 = qpDecHeadMapper.queryKeyQyDataDash(map0);
            List<Map>list3 = qpDecHeadMapper.querAllQyDataDash1(map0);
            Map map = new HashMap();
            if(list1!=null&& list1.size()>0){
                BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(list1.get(0).get("NOWDAY")));
                BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(list1.get(0).get("BEFOREDAY")));
                String bigDecimal11 = CalculateUtil.percentBigDecimal(bigDecimal1,bigDecimal2);
                list1.get(0).put("rate1",bigDecimal11);
                BigDecimal bigDecimal3 = new BigDecimal(String.valueOf(list1.get(0).get("NOWMONTH")));
                BigDecimal bigDecimal4 = new BigDecimal(String.valueOf(list1.get(0).get("BEFOREMONTH")));
                String bigDecimal33 = CalculateUtil.percentBigDecimal(bigDecimal3,bigDecimal4);
                list1.get(0).put("rate3",bigDecimal33);
                BigDecimal bigDecimal5 = new BigDecimal(String.valueOf(list1.get(0).get("NOWYEAR")));
                BigDecimal bigDecimal6 = new BigDecimal(String.valueOf(list1.get(0).get("BEFOREYEAR")));
                String bigDecimal55 = CalculateUtil.percentBigDecimal(bigDecimal3,bigDecimal4);
                list1.get(0).put("rate4",bigDecimal55);


                //重点企业/总企业今日占比
                String ratioStr1 = String.valueOf(CalculateUtil.getRatioDouble(new BigDecimal(list2.get(0).get("NOWDAY1").toString()), new BigDecimal(list3.get(0).get("NOWDAY11").toString())));
                //去年重点企业/去年总企业今日占比
                String ratioStr2 = String.valueOf(CalculateUtil.getRatioDouble(new BigDecimal(list2.get(0).get("BEFOREDAY1").toString()), new BigDecimal(list3.get(0).get("BEFOREDAY11").toString())));
                //前年重点企业/前年总企业今日占比
                String ratioStr3 = String.valueOf(CalculateUtil.getRatioDouble(new BigDecimal(list2.get(0).get("BEFOREDAY2").toString()), new BigDecimal(list3.get(0).get("BEFOREDAY21").toString())));

                String bigDecimal11new1 = CalculateUtil.percentBigDecimalnot(new BigDecimal(ratioStr1),new BigDecimal(ratioStr2));
                String bigDecimal11new2 = CalculateUtil.percentBigDecimalnot(new BigDecimal(ratioStr2),new BigDecimal(ratioStr3));
                //重点企业的今日占比同比
                String bigDecimal11new22 = CalculateUtil.percentBigDecimal(new BigDecimal(String.valueOf(bigDecimal11new1)),new BigDecimal(String.valueOf(bigDecimal11new2)));

                list1.get(0).put("rate11",bigDecimal11new22);
                //重点企业/总企业本月占比
                String ratioStr4 = String.valueOf(CalculateUtil.getRatioDouble(new BigDecimal(list2.get(0).get("NOWMONTH1").toString()), new BigDecimal(list3.get(0).get("NOWMONTH11").toString())));
                //去年重点企业/去年总企业本月占比
                String ratioStr5 = String.valueOf(CalculateUtil.getRatioDouble(new BigDecimal(list2.get(0).get("BEFOREMONTH1").toString()), new BigDecimal(list3.get(0).get("BEFOREMONTH11").toString())));
                //前年重点企业/前年总企业本月占比
                String ratioStr6 = String.valueOf(CalculateUtil.getRatioDouble(new BigDecimal(list2.get(0).get("BEFOREMONTH2").toString()), new BigDecimal(list3.get(0).get("BEFOREMONTH21").toString())));
                String bigDecimal11new11 = CalculateUtil.percentBigDecimalnot(new BigDecimal(ratioStr4),new BigDecimal(ratioStr5));
                String bigDecimal11new21 = CalculateUtil.percentBigDecimalnot(new BigDecimal(ratioStr5),new BigDecimal(ratioStr6));
                //重点企业的本月同比
                String bigDecimal11new221 = CalculateUtil.percentBigDecimal(new BigDecimal(String.valueOf(bigDecimal11new11)),new BigDecimal(String.valueOf(bigDecimal11new21)));
                list1.get(0).put("rate33",bigDecimal11new221);


                //重点企业/总企业本年占比
                String ratioStr7 = String.valueOf(CalculateUtil.getRatioDouble(new BigDecimal(list2.get(0).get("NOWYEAR1").toString()), new BigDecimal(list3.get(0).get("NOWYEAR11").toString())));
                //去年重点企业/去年总企业本年占比
                String ratioStr8 = String.valueOf(CalculateUtil.getRatioDouble(new BigDecimal(list2.get(0).get("BEFOREYEAR1").toString()), new BigDecimal(list3.get(0).get("BEFOREYEAR11").toString())));
                //前年重点企业/前年总企业本年占比
                String ratioStr9 = String.valueOf(CalculateUtil.getRatioDouble(new BigDecimal(list2.get(0).get("BEFOREYEAR2").toString()), new BigDecimal(list3.get(0).get("BEFOREYEAR11").toString())));
                String bigDecimal11new111 = CalculateUtil.percentBigDecimalnot(new BigDecimal(ratioStr7),new BigDecimal(ratioStr8));
                String bigDecimal11new211 = CalculateUtil.percentBigDecimalnot(new BigDecimal(ratioStr8),new BigDecimal(ratioStr9));
                //重点企业的本年同比
                String bigDecimal11new2211 = CalculateUtil.percentBigDecimal(new BigDecimal(String.valueOf(bigDecimal11new111)),new BigDecimal(String.valueOf(bigDecimal11new211)));
                list1.get(0).put("rate44",bigDecimal11new2211);


                // 重点企业/all企业今日

                list1.get(0).put("NOWDAY1",list2.get(0).get("NOWDAY1").toString());
                list1.get(0).put("NOWDAY11",list3.get(0).get("NOWDAY11").toString());

                // 重点企业/all企业本月
                list1.get(0).put("NOWMONTH1",list2.get(0).get("NOWMONTH1").toString());
                list1.get(0).put("NOWMONTH11",list3.get(0).get("NOWMONTH11").toString());

                // 重点企业/all企业本周
                list1.get(0).put("NOWWEEK1",list2.get(0).get("NOWWEEK1").toString());
                list1.get(0).put("NOWWEEK11",list3.get(0).get("NOWWEEK11").toString());

                list1.get(0).put("NOWYEAR1",list2.get(0).get("NOWYEAR1").toString());
                list1.get(0).put("NOWYEAR11",list3.get(0).get("NOWYEAR11").toString());

                map.putAll((Map)list1.get(0));
            }


            List list=new ArrayList();
            list.add(map);
            data.put("rs", list);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse queryNoticeInfoDash(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            QueryWrapper<TNoticeEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("update_time");
            List<TNoticeEntity> list = tNoticeMapper.selectList(queryWrapper);


            data.put("rs", list);
            response.fillMessage(list);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse queryCompanyRateData(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();

        try {
            String searchFlag = params.get("searchFlag");

            HashMap map0=new HashMap();
            map0.put("searchFlag", searchFlag);
            List<Map>list1 = qpDecHeadMapper.queryCompanyRateData(map0);

            data.put("rs", list1);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
    @Override
    public ApiResponse queryCompanyUseDataList(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();
        //分页
        Long pageSize = Long.parseLong(params.get("limit"));
        Long pageNum = Long.parseLong(params.get("page"));
        try {
            String companyName = params.get("companyName");
            String searchFlag = params.get("searchFlag");
            String queryFlag = params.get("queryFlag");
            HashMap map0=new HashMap();
            map0.put("searchFlag", searchFlag);
            map0.put("companyName", companyName);
            long current = (pageNum - 1) * pageSize;
            map0.put("current", current);
            map0.put("pageSize", pageNum*pageSize);
            Integer countcompany = 0;
            List<Map>list1=new ArrayList<>();
            Map map = new HashMap();
            HashMap map1=new HashMap();
            if("1".equals(queryFlag)){
                list1 = qpDecHeadMapper.queryCompanyUseDataList(map0);
                countcompany = qpDecHeadMapper.queryCompanyUseDataListCount(map0);
                for(int i=0;i<list1.size();i++){
                    map1=new HashMap();
                    map1.put("deptId",list1.get(i).get("DEPT_ID"));
                    Integer integer = qpDecHeadMapper.queryCompanyUseDataByCodeCount(map1);
                    List list=new ArrayList();
                    if(integer>1){
                        list = qpDecHeadMapper.queryCompanyUseDataByCode1(map1);
                    }else{
                        list = qpDecHeadMapper.queryCompanyUseDataByCode(map1);
                    }

                    list1.get(i).put("companyInfo",list);
                }
            }else if("2".equals(queryFlag)){
                list1 = qpDecHeadMapper.queryCompanyUseDataListnot(map0);
                countcompany = qpDecHeadMapper.queryCompanyUseDataListnotCount(map0);
                for(int i=0;i<list1.size();i++){
                    map1=new HashMap();
                    map1.put("deptId",list1.get(i).get("DEPT_ID"));
                    Integer integer = qpDecHeadMapper.queryCompanyUseDataByCodeCount(map1);
                    List list=new ArrayList();
                    if(integer>1){
                        list = qpDecHeadMapper.queryCompanyUseDataByCode1(map1);
                    }else{
                        list = qpDecHeadMapper.queryCompanyUseDataByCode(map1);
                    }
                    list1.get(i).put("companyInfo",list);
                }
            }

            data.put("items", list1);
            data.put("total", countcompany);
            return ApiResponse.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
}

