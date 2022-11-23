package com.nteport.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.UserRoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户-角色接口
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

    List<Long> listTreeSq(Long roleId);

    List<RoleEntity> getRolesByUserId(Long userId);

    int deleteByUserId(Long userId);

    int insertSq(UserRoleEntity userRoleEntity);

    List<Long> getRoleIdsByUserId(Long userId);
}
