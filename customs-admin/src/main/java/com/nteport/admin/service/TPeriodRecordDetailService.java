package com.nteport.admin.service;

import com.nteport.admin.entity.TPeriodRecordDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;

/**
* @author qianjun
* @description 针对表【t_period_record_detail(动态督查记录子表)】的数据库操作Service
* @createDate 2022-07-28 11:25:28
*/
public interface TPeriodRecordDetailService extends IService<TPeriodRecordDetailEntity> {

    ApiResponse getDetail(Long id);
}
