package com.nteport.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

public interface IRoleService extends IService<RoleEntity> {

    ApiResponse getRolePageList(Map<String, String> params);

    ApiResponse getRoleList(Map params,UserEntity user);

    ApiResponse createRole(String jsonString, String token);

    ApiResponse updateRole(String id, String jsonString, String token);

    ApiResponse deleteRole(String id);

    ApiResponse listTreeSq(String roleId);

    ApiResponse authorize(String roleId, String jsonString, String token);

    ApiResponse verifyCode(String roleCode, String roleId);

    ApiResponse canUseWeek(UserEntity user);

}
