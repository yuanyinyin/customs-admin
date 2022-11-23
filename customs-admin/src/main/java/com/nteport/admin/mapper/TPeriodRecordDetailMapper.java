package com.nteport.admin.mapper;

import com.nteport.admin.entity.TPeriodRecordDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author qianjun
* @description 针对表【t_period_record_detail(动态督查记录子表)】的数据库操作Mapper
* @createDate 2022-07-28 11:25:28
* @Entity com.nteport.admin.entity.TPeriodRecordDetail
*/
@Repository
public interface TPeriodRecordDetailMapper extends BaseMapper<TPeriodRecordDetailEntity> {
    List getDetail(Long id);
}




