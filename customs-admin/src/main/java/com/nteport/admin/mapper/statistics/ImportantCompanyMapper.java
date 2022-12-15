package com.nteport.admin.mapper.statistics;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nteport.admin.entity.companyStatistics.ImportantCompanyEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public interface ImportantCompanyMapper extends BaseMapper<ImportantCompanyEntity> {
    List <Map>queryCompanyList(HashMap hashMap);

    Integer queryCompanyListCount(HashMap map);
}
