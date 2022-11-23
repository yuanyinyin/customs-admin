package com.nteport.admin.mapper;

import com.nteport.admin.entity.TLineInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nteport.admin.entity.system.ApiResponse;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
* @author qianjun
* @description 针对表【t_line_info】的数据库操作Mapper
* @createDate 2022-09-07 16:37:24
* @Entity com.nteport.admin.entity.TLineInfoEntity
*/
@Repository
public interface TLineInfoMapper extends BaseMapper<TLineInfoEntity> {

    List<HashMap> queryLineGroupByArea();

}




