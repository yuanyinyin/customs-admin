package com.nteport.admin.mapper;

import com.nteport.admin.entity.TPatrolPersonEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author qianjun
* @description 针对表【t_patrol_person(巡查负责人表)】的数据库操作Mapper
* @createDate 2022-07-27 15:46:13
* @Entity com.nteport.admin.entity.TPatrolPerson
*/
@Repository
public interface TPatrolPersonMapper extends BaseMapper<TPatrolPersonEntity> {

    List selectPointList();
}




