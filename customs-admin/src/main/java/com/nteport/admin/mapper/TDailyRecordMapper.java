package com.nteport.admin.mapper;

import com.nteport.admin.entity.TDailyRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nteport.admin.entity.system.ApiResponse;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_daily_record(每日巡)】的数据库操作Mapper
* @createDate 2022-07-27 15:46:13
* @Entity com.nteport.admin.entity.TDailyRecord
*/
@Repository
public interface TDailyRecordMapper extends BaseMapper<TDailyRecordEntity> {
    List statRecords();

    List<HashMap> queryXuncha(String yearMonth);

    List<HashMap> queryXunchaContent();

}




