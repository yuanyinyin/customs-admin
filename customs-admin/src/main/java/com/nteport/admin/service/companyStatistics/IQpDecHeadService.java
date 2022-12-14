package com.nteport.admin.service.companyStatistics;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.companyStatistics.QpDecHead;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public interface IQpDecHeadService extends IService<QpDecHead> {
    ApiResponse queryImOrExCustomsData(Map<String, String> params, UserEntity user);
    ApiResponse queryTransportMode(Map<String, String> params, UserEntity user);
    ApiResponse portStatistic(Map<String, String> params, UserEntity user);
    ApiResponse queryTradeMode(Map<String, String> params, UserEntity user);
    ApiResponse queryGoodsTotal(Map<String, String> params, UserEntity user);
    ApiResponse queryTopGoods(Map<String, String> params, UserEntity user);
    ApiResponse queryTopFrequency(Map<String, String> params, UserEntity user);
    ApiResponse queryAreaData(Map<String, String> params, UserEntity user);
    ApiResponse queryAreaDataByCode(Map<String, String> params, UserEntity user);
    ApiResponse queryInspectRate(Map<String, String> params, UserEntity user);
    ApiResponse queryGoodsData(Map<String, String> params, UserEntity user);
    ApiResponse queryGoodsDataByGNAME(Map<String, String> params, UserEntity user);
    ApiResponse queryGoodsDataDetail(Map<String, String> params, UserEntity user);
    ApiResponse queryKeyCompanyData(Map<String, String> params, UserEntity user);
    ApiResponse queryKeyCompanyDataSum(Map<String, String> params, UserEntity user);
    ApiResponse queryKeyCompanyDataByCode(Map<String, String> params, UserEntity user);
    ApiResponse queryInspectRateDash(Map<String, String> params, UserEntity user);
    ApiResponse queryCustomSumDash(Map<String, String> params, UserEntity user);
    ApiResponse queryTotalCustomDataDash(Map<String, String> params, UserEntity user);
    ApiResponse queryCalculateCustomDataDash(Map<String, String> params, UserEntity user);
    //管理端首页
    ApiResponse queryAreaDataDash(Map<String, String> params, UserEntity user);
    ApiResponse queryCustomDataDash(Map<String, String> params, UserEntity user);

    ApiResponse queryQyUseDataDash(Map<String, String> params, UserEntity user);

    ApiResponse queryNoticeInfoDash(Map<String, String> params, UserEntity user);
}

