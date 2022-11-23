package com.nteport.admin.mapper;

import com.nteport.admin.entity.TLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
* @author qianjun
* @description 针对表【t_login_log】的数据库操作Mapper
* @createDate 2022-07-28 14:07:47
* @Entity com.nteport.admin.entity.TLoginLogEntity
*/
@Repository
public interface TLoginLogMapper extends BaseMapper<TLoginLogEntity> {

    List<HashMap> listPage();
}




