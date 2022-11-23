package com.nteport.admin.service;

import com.nteport.admin.entity.TTrendsRecordDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;

/**
* @author qianjun
* @description 针对表【t_trends_record_detail(定期检查记录子表)】的数据库操作Service
* @createDate 2022-07-28 11:25:28
*/
public interface TTrendsRecordDetailService extends IService<TTrendsRecordDetailEntity> {

    ApiResponse getDetail(Long id);
}
