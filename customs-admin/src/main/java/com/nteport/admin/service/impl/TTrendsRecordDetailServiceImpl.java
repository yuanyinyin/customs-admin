package com.nteport.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TTrendsRecordDetailEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.service.TTrendsRecordDetailService;
import com.nteport.admin.mapper.TTrendsRecordDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author qianjun
* @description 针对表【t_trends_record_detail(定期检查记录子表)】的数据库操作Service实现
* @createDate 2022-07-28 11:25:28
*/
@Service
public class TTrendsRecordDetailServiceImpl extends ServiceImpl<TTrendsRecordDetailMapper, TTrendsRecordDetailEntity>
    implements TTrendsRecordDetailService{

    @Autowired
    private TTrendsRecordDetailMapper tTrendsRecordDetailMapper;

    @Override
    public ApiResponse getDetail(Long id) {
        List list = tTrendsRecordDetailMapper.getDetail(id);
        return ApiResponse.success(list);
    }
}




