package com.nteport.admin.mapper;

import com.nteport.admin.entity.TTrendsRecordDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author qianjun
* @description 针对表【t_trends_record_detail(定期检查记录子表)】的数据库操作Mapper
* @createDate 2022-07-28 11:25:28
* @Entity com.nteport.admin.entity.TTrendsRecordDetail
*/
@Repository
public interface TTrendsRecordDetailMapper extends BaseMapper<TTrendsRecordDetailEntity> {
    List getDetail(Long id);
}




