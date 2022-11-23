package com.nteport.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserService extends IService<UserEntity> {

    /**
     * 获取用户列表，带分页
     */
    ApiResponse listPage(String jsonString, UserEntity user);

    /**
     * 获取用户列表
     */
    ApiResponse getUsers(String jsonString);

    /**
     * 创建用户
     */
    ApiResponse createUser(String jsonString, UserEntity user);

    /**
     * 修改用户
     */
    ApiResponse updateUser(Long id, String jsonString, UserEntity user);

    /**
     * 审核用户
     */
    ApiResponse audit(Long id,UserEntity user);

    /**
     * 校验用户密码
     */
    ApiResponse verifyUserPassword(String jsonString, String token);

    /**
     * 修改用户自己的密码
     */
    ApiResponse changeUserPassword(String jsonString, String token);

    /**
     * 删除用户
     */
    ApiResponse deleteUser(Long id);

    /**
     * 校验新用户名可注册性
     */
    ApiResponse verifyUserName(String jsonString);

    /**
     * 获取操作者后转发请求给UserRoleService
     */
    ApiResponse authorize(Long id, String jsonString, UserEntity user);

    /**
     * 从操作者令牌获取操作者
     */
    UserEntity getOperator(String token);

    /**
     * 树状显示用户角色关系
     */
    ApiResponse listUserRoleTreeSq(Long id);

    /**
     * 根据token或者openid获取用户信息
     */
    UserEntity getUserByColumnValue(String column, String value);

    /**
     * app注册用户
     */
    ApiResponse register(String jsonString);

    /**
     * 用户审核通过
     */
    ApiResponse passUser(Long id);

    /**
     * 用户审核不通过
     */
    ApiResponse notPassUser(Long id);

    /**
     * app忘记密码
     */
    ApiResponse forget(String jsonString);

    ApiResponse selectAllUserAndDept();
}
