package com.nteport.admin.mapper;

import com.nteport.admin.entity.TRiskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
* @author qianjun
* @description 针对表【t_risk(隐患表)】的数据库操作Mapper
* @createDate 2022-07-27 15:46:13
* @Entity com.nteport.admin.entity.TRisk
*/
@Repository
public interface TRiskMapper extends BaseMapper<TRiskEntity> {

    List count();

    List<HashMap> queryRiskCount(String year);

    List<HashMap> queryRiskRatio(String year);

}




