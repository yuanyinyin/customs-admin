package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TDailyRecordDetailEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TDailyRecordDetailService;
import com.nteport.admin.mapper.TDailyRecordDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
* @author qianjun
* @description 针对表【t_daily_record_detail(每日巡详情)】的数据库操作Service实现
* @createDate 2022-07-27 15:46:13
*/
@Service
public class TDailyRecordDetailServiceImpl extends ServiceImpl<TDailyRecordDetailMapper, TDailyRecordDetailEntity>
    implements TDailyRecordDetailService{

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

    @Override
    public ApiResponse addClockIn(Long id, String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        try {
            if(user == null || jsonString == null || id == null){
                return response.fillMessage(EnumCode.BAD_REQUEST);
            }
            TDailyRecordDetailEntity entity = JSON.parseObject(jsonString,TDailyRecordDetailEntity.class);
            entity.setHeadId(id);
            entity.createTimeStamp(user);
            tDailyRecordDetailMapper.insert(entity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }
}




