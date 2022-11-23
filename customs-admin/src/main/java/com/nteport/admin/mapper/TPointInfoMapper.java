package com.nteport.admin.mapper;

import com.nteport.admin.entity.TPointInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
* @author qianjun
* @description 针对表【t_point_info】的数据库操作Mapper
* @createDate 2022-07-27 15:46:13
* @Entity com.nteport.admin.entity.TPointInfo
*/
@Repository
public interface TPointInfoMapper extends BaseMapper<TPointInfoEntity> {

    List<HashMap> pointCount();

    List selectUpdateTop5(HashMap map);

    List<HashMap> queryPointGroupByType();

    List<HashMap> queryPointGroupByArea();

    List<HashMap> queryAnfaCount(String year);

    List<HashMap> queryPointRiskLevel();

}




