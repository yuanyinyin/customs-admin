package com.nteport.admin.mapper;

import com.nteport.admin.entity.TDeptEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author qianjun
* @description 针对表【t_dept(部门表)】的数据库操作Mapper
* @createDate 2022-07-27 16:14:21
* @Entity com.nteport.admin.entity.TDept
*/
@Repository
public interface TDeptMapper extends BaseMapper<TDeptEntity> {
    List<Long> queryAreaDept(Long id);
}




