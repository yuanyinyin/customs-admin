package com.nteport.admin.mapper;

import com.nteport.admin.entity.TDailyRecordDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author qianjun
* @description 针对表【t_daily_record_detail(每日巡详情)】的数据库操作Mapper
* @createDate 2022-07-27 15:46:13
* @Entity com.nteport.admin.entity.TDailyRecordDetail
*/
@Repository
public interface TDailyRecordDetailMapper extends BaseMapper<TDailyRecordDetailEntity> {

    List getDetail(Long id);
}




