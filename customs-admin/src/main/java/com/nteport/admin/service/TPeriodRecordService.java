package com.nteport.admin.service;

import com.nteport.admin.entity.TPeriodRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

/**
* @author qianjun
* @description 针对表【t_period_record(定期查)】的数据库操作Service
* @createDate 2022-07-27 15:46:13
*/
public interface TPeriodRecordService extends IService<TPeriodRecordEntity> {

    ApiResponse create(String jsonString, UserEntity user);

    ApiResponse listPage(String jsonString,UserEntity user);
}
