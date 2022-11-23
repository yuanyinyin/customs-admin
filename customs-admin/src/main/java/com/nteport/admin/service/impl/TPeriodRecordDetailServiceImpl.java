package com.nteport.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TDailyRecordDetailEntity;
import com.nteport.admin.entity.TPeriodRecordDetailEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.mapper.TDailyRecordDetailMapper;
import com.nteport.admin.service.TPeriodRecordDetailService;
import com.nteport.admin.mapper.TPeriodRecordDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author qianjun
* @description 针对表【t_period_record_detail(动态督查记录子表)】的数据库操作Service实现
* @createDate 2022-07-28 11:25:28
*/
@Service
public class TPeriodRecordDetailServiceImpl extends ServiceImpl<TPeriodRecordDetailMapper, TPeriodRecordDetailEntity>
    implements TPeriodRecordDetailService{

    @Autowired
    private TDailyRecordDetailMapper tDailyRecordDetailMapper;

    @Override
    public ApiResponse getDetail(Long id) {
        QueryWrapper<TDailyRecordDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("head_id",id);
        queryWrapper.orderByAsc("create_time");
        List<TDailyRecordDetailEntity> list = tDailyRecordDetailMapper.selectList(queryWrapper);
        return ApiResponse.success(list);
    }
}




