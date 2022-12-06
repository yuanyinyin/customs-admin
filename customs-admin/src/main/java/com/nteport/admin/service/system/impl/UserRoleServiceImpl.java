package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.system.*;
import com.nteport.admin.mapper.UserRoleMapper;
import com.nteport.admin.service.system.IUserRoleService;
import com.nteport.admin.service.system.OpeLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * 用户-角色管理服务
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 用户-角色授权
     *
     * @param userId     被授予角色用户的id
     * @param jsonString 用户新角色数据集
     * @param user       操作者
     * @return 状态码
     */
    @Override
    @Transactional
    @OpeLog(opeModule = "用户角色管理-UserRoleService", opeType = OperationType.MODIFY, opeDesc = "分配角色")
    public ApiResponse authorize(String userId, String jsonString, UserEntity user) {
        if ("".equals(jsonString) || user == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        try {
            // 先删除所有 用户-角色 再重新插入
            deleteUserRoleByUserId(userId);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            String roleIds = jsonObject.getString("roleIds");
            if (StringUtils.isBlank(roleIds)) {
                return ApiResponse.fail("角色id为空");
            }
            String[] roleArr = roleIds.split(",");
            // 遍历用户新角色数据集插入
            for (String s : roleArr) {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setUserId(userId);
                userRoleEntity.setRoleId(s);
                userRoleEntity.createTimeStamp(user);
                userRoleMapper.insertSq(userRoleEntity);
            }
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail(EnumCode.ERROR_HANDLE);
        }
    }

    /**
     * 树状显示用户角色关系
     *
     * @param id 用户
     * @return 用户持有的角色集
     */
    @Override
    public ApiResponse listUserRoleTreeSq(String id) {
        return ApiResponse.success(userRoleMapper.listTreeSq(id));
    }

    /**
     * 按userId查询用户持有的角色身份
     *
     * @param userId 用户
     * @return List 对应用户的role列表
     */
    @Override
    public List<RoleEntity> getRoles(String userId) {
        return userRoleMapper.getRolesByUserId(userId);
    }

    /**
     * 按照用户id删除用户-角色关系
     *
     * @param userId 被删除用户id
     */
    @Override
    @Transactional
    public void deleteUserRoleByUserId(String userId) {
        userRoleMapper.deleteByUserId(userId);
    }

}
