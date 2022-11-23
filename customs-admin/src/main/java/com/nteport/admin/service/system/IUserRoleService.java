package com.nteport.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.entity.system.UserRoleEntity;

import java.util.List;

public interface IUserRoleService extends IService<UserRoleEntity> {

    /**
     * 用户-角色授权
     */
    ApiResponse authorize(Long userId, String jsonString, UserEntity user);

    /**
     * 树状显示用户角色关系
     */
    ApiResponse listUserRoleTreeSq(Long id);

    /**
     * 按userId查询用户持有的角色身份
     */
    List<RoleEntity> getRoles(Long userId);

    /**
     * 按照用户id删除用户-角色关系
     */
    void deleteUserRoleByUserId(Long userId);

}
