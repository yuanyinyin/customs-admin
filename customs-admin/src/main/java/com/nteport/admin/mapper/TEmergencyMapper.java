package com.nteport.admin.mapper;

import com.nteport.admin.entity.TEmergencyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author qianjun
* @description 针对表【t_emergency(突发事件表)】的数据库操作Mapper
* @createDate 2022-07-27 15:46:13
* @Entity com.nteport.admin.entity.TEmergency
*/
@Repository
public interface TEmergencyMapper extends BaseMapper<TEmergencyEntity> {

    List count();
}




