package com.nteport.admin.service;

import com.nteport.admin.entity.TDailyRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

/**
* @author qianjun
* @description 针对表【t_daily_record(每日巡)】的数据库操作Service
* @createDate 2022-07-27 15:46:13
*/
public interface TDailyRecordService extends IService<TDailyRecordEntity> {

    ApiResponse create(UserEntity user);

    ApiResponse createPeriod(UserEntity user);

    ApiResponse createTask(Long id,UserEntity user);

    ApiResponse listPage(String jsonString,UserEntity user);

    ApiResponse updateInfo(Long id, String result, UserEntity user);

    ApiResponse updateTask(Long id, String result, UserEntity user);

    ApiResponse statRecords();

    ApiResponse delete(Long id);

    ApiResponse listPageByTask(String jsonString,UserEntity user);

    ApiResponse queryXuncha(String yearMonth);

    ApiResponse queryXunchaContent();

}
