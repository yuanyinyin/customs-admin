package com.nteport.admin.mapper.statistics;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nteport.admin.entity.companyStatistics.QpDecHead;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface QpDecHeadStatisticsMapper extends BaseMapper<QpDecHead> {

    List  queryImOrExCustomsData(HashMap hashMap);
    List  queryTransportMode(HashMap hashMap);
    List  portStatistic(HashMap hashMap);
    List  queryTradeMode(HashMap hashMap);
    List  queryGoodsTotal1(HashMap hashMap);
    List  queryGoodsTotal2(HashMap hashMap);
    List  queryTopGoods(HashMap hashMap);
    List  queryTopFrequency(HashMap hashMap);
    List queryAreaData(HashMap hashMap);
    List queryAreaDataByCode(HashMap hashMap);
    List queryInspectRate(HashMap hashMap);
    List queryInspectRateCheck(HashMap hashMap);
    List queryGoodsData(HashMap hashMap);
    List queryGoodsDataByGNAME(HashMap hashMap);
    List queryGoodsDataDetail(HashMap hashMap);
    List queryKeyCompanyDataList(HashMap hashMap);
    Integer queryKeyCompanyDataListCount(HashMap hashMap);
    List queryKeyCompanyDataSum(HashMap hashMap);
    List queryKeyCompanyDataByCode(HashMap hashMap);


    //首页
    List queryInspectRateDash(HashMap hashMap);
    List queryCustomSumDash(HashMap hashMap);
    List queryTotalCustomDataDash(HashMap hashMap);
    List querySysTimeCustomData(HashMap hashMap);
    List queryMonthCustomData(HashMap hashMap);
    List queryYearCustomData(HashMap hashMap);
    List queryTotalCustomData(HashMap hashMap);
    List queryAreaDataDash(HashMap hashMap);
    List queryCustomDataDash(HashMap hashMap);

    List queryQyUseDataDash(HashMap hashMap);
    List queryKeyQyDataDash(HashMap hashMap);

    List querAllQyDataDash(HashMap hashMap);

    List querAllQyDataDash1(HashMap hashMap);


    List queryCompanyRateData(HashMap hashMap);
    List queryCompanyUseDataList(HashMap hashMap);
    Integer queryCompanyUseDataListCount(HashMap hashMap);
    List queryCompanyUseDataListnot(HashMap hashMap);
    Integer queryCompanyUseDataListnotCount(HashMap hashMap);
    Integer queryCompanyUseDataByCodeCount(HashMap hashMap);
    List queryCompanyUseDataByCode(HashMap hashMap);
    List queryCompanyUseDataByCode1(HashMap hashMap);
}
