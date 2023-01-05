package com.nteport.admin.service.companyStatistics;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.companyStatistics.ImportantGoodsEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;


public interface ImportantGoodsService extends IService<ImportantGoodsEntity> {

    /**
     * 数据统计其他相关模块详细设计
     * @param
     * @param user
     * @return
     */
    //重点企业
    ApiResponse queryKeyGoods(Map<String, String> params, UserEntity user);

    ApiResponse cancelKeyGoods(Long id);
    ApiResponse queryGoodsList(Map<String, String> params, UserEntity user);
//
    ApiResponse addGoods(String jsonString, UserEntity user);
//
    ApiResponse cancelGoods(Map<String, String> params, UserEntity user);

}

