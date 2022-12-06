package com.nteport.admin.mapper;

import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.RoleMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色接口
 */
@Repository
public interface RoleMapper extends BaseMapper<RoleEntity> {

    List<Long> listTreeSq(String roleId);

    int deleteByRoleId(String roleId);

    int insertSq(RoleMenuEntity roleMenuEntity);

}
