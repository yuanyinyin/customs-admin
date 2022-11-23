package com.nteport.admin.service;

import com.nteport.admin.entity.TDailyRecordDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

/**
* @author qianjun
* @description 针对表【t_daily_record_detail(每日巡详情)】的数据库操作Service
* @createDate 2022-07-27 15:46:13
*/
public interface TDailyRecordDetailService extends IService<TDailyRecordDetailEntity> {

    ApiResponse getDetail(Long id);

    ApiResponse addClockIn(Long id,String jsonString, UserEntity user);
}
