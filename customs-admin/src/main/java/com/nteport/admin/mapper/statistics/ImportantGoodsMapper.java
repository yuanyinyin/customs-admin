package com.nteport.admin.mapper.statistics;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;import com.nteport.admin.entity.companyStatistics.ImportantGoodsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface ImportantGoodsMapper extends BaseMapper<ImportantGoodsEntity> {
    List <Map>queryGoodsList(HashMap hashMap);

    Integer queryGoodsListCount(HashMap map);
}
