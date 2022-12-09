package com.nteport.admin.service.companyStatistics;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.companyStatistics.ImportantCompanyEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;


public interface ImportantCompanyService extends IService<ImportantCompanyEntity> {

    /**
     * 数据统计其他相关模块详细设计
     * @param
     * @param user
     * @return
     */
    //重点企业
    ApiResponse queryKeyCompany(Map<String, String> params, UserEntity user);

    ApiResponse cancelKeyCompany(Long id);

    ApiResponse queryCompanyList(Map<String, String> params, UserEntity user);

    ApiResponse addCompany(String jsonString, UserEntity user);

    ApiResponse cancelCompany(Map<String, String> params, UserEntity user);
}

