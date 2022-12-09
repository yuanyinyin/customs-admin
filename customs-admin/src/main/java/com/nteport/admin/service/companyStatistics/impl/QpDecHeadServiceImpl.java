package com.nteport.admin.service.companyStatistics.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.companyStatistics.QpDecHead;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.statistics.QpDecHeadStatisticsMapper;
import com.nteport.admin.service.companyStatistics.IQpDecHeadService;


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
            map0.put("orgid", "201306050000007559");

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
            map0.put("orgid", "201306050000007559");

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
            map0.put("orgid", "201306050000007559");
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
            map0.put("orgid", "201306050000007559");
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
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid", "201306050000007559");
            map0.put("ieFlag", ieFlag);

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
            map0.put("orgid", "201306050000007559");
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
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("orgid", "201306050000007559");
            map0.put("ieFlag", ieFlag);

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
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("ieFlag", ieFlag);

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
            map0.put("orgid", "201306050000007559");

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
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");

            HashMap map0=new HashMap();
            map0.put("startDate", startDate);
            map0.put("endDate", endDate);
            map0.put("ieFlag", ieFlag);

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
            map0.put("orgid", "201306050000007559");

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
}
