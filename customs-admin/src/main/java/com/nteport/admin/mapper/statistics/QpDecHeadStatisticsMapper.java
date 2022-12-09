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
    List queryAreaList(HashMap hashMap);
    List queryCustomsData(HashMap hashMap);
    List queryCustomsTotalData(HashMap hashMap);
    List queryNetWtData(HashMap hashMap);
    List queryGrossWtData(HashMap hashMap);
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
}
