package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.OperationType;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.system.ILoginService;
import com.nteport.admin.service.system.IUserService;
import com.nteport.admin.service.system.OpeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 新增用户
     *
     * @param jsonString 新用户的数据集
     * @param user      创建者
     * @return 状态码
     */
    @PostMapping("/create")
    @OpeLog(opeModule = "用户管理-用户新增", opeType = OperationType.ADD, opeDesc = "用户新增")
    public ApiResponse create(@RequestBody String jsonString, UserEntity user) {
        return userService.createUser(jsonString, user);
    }

    /**
     * 分页查询用户
     *
     * @param jsonString Map[pageNum 当前页数, pageSize 每页大小]
     * @return Json[list 用户列表，total 结果总数] 或 状态码
     */
    @PostMapping("/listPage")
    public ApiResponse listPage(@RequestBody String jsonString, UserEntity user) {
        if(jsonString == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        return userService.listPage(jsonString, user);
    }

    /**
     * 获取用户列表
     * @param jsonString 用户筛选参数 Json[roleCode   角色编码（可选）]
     * @return 用户列表
     */
    @PostMapping("/getUsers")
    public ApiResponse getUsersByCondition(@RequestBody String jsonString){
        return userService.getUsers(jsonString);
    }

    /**
     * 修改用户
     *
     * @param id         被更新用户的id
     * @param jsonString 被更新用户的新数据集
     * @param user      更新者
     * @return 状态码
     */
    @PostMapping("/update/{id}")
    @OpeLog(opeModule = "用户管理-用户编辑", opeType = OperationType.MODIFY, opeDesc = "用户编辑")
    public ApiResponse update(@PathVariable(value = "id") Long id, @RequestBody String jsonString, UserEntity user) {
        return userService.updateUser(id, jsonString, user);
    }

    /**
     * 审核用户
     */
    @GetMapping("/audit/{id}")
    public ApiResponse audit(@PathVariable(value = "id") Long id,UserEntity user){
        return userService.audit(id,user);
    }

    /**
     * 校验用户密码
     *
     * @param jsonString 用户密码
     * @return ‘incorrect’ 或 状态码
     */
    @PostMapping("/verifyUserPassword")
    public ApiResponse verifyUserPassword(@RequestBody String jsonString, @RequestHeader("authToken") String token) {
        return userService.verifyUserPassword(jsonString, token);
    }

    /**
     * 修改用户自己的密码
     *
     * @param jsonString 用户的旧、新密码
     * @param token  用户的令牌
     * @return 状态码
     */
    @PostMapping("/changeUserPassword")
    public ApiResponse changeUserPassword(@RequestBody String jsonString, @RequestHeader("authToken") String token) {
        return userService.changeUserPassword(jsonString, token);
    }

    /**
     * 删除用户
     *
     * @param id 被删用户的id
     * @return 状态码
     */
    @PostMapping("/delete/{id}")
    @OpeLog(opeModule = "用户管理-用户删除", opeType = OperationType.DELETE, opeDesc = "用户删除")
    public ApiResponse delete(@PathVariable(value = "id") Long id) {
        return userService.deleteUser(id);
    }

    /**
     * 校验新用户名可注册性
     *
     * @param jsonString 用户数据集
     * @return ‘exists’ 或 状态码
     */
    @PostMapping("/verifyUserName")
    public ApiResponse verifyUserName(@RequestBody String jsonString) {
        return userService.verifyUserName(jsonString);
    }

    /**
     * 用户-角色授权
     *
     * @param id         被授予角色用户的id
     * @param jsonString 用户新角色数据集
     * @param user      授权者的令牌
     * @return 状态码
     */
    @PostMapping("/authorize/{id}")
    public ApiResponse authorize(@PathVariable(value = "id") Long id, @RequestBody String jsonString, UserEntity user) {
        return userService.authorize(id, jsonString, user);
    }

    /**
     * 树状显示用户角色关系
     *
     * @param id 用户id
     * @return 用户持有的角色集
     */
    @PostMapping("/listRoleTreeSq/{id}")
    public ApiResponse listUserRoleTreeSq(@PathVariable(value = "id") Long id) {
        return userService.listUserRoleTreeSq(id);
    }

    @GetMapping("/{id}")
    public ApiResponse getUser(@PathVariable(value = "id") Long id){
        return ApiResponse.success(userService.getById(id));
    }

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("/allUser")
    public ApiResponse getAllUser() {
        List<UserEntity> list = userService.list();
        return ApiResponse.success(list);
    }

    @PostMapping("/register")
    @OpeLog(opeModule = "用户管理-App用户注册", opeType = OperationType.ADD, opeDesc = "App用户注册")
    public ApiResponse register(@RequestBody String jsonString) {
        return userService.register(jsonString);
    }

    @PostMapping("/passUser/{id}")
    @OpeLog(opeModule = "用户管理-用户审核通过", opeType = OperationType.DELETE, opeDesc = "用户审核通过")
    public ApiResponse passUser(@PathVariable(value = "id") Long id) {
        return userService.passUser(id);
    }

    @PostMapping("/notPassUser/{id}")
    @OpeLog(opeModule = "用户管理-用户审核不通过", opeType = OperationType.DELETE, opeDesc = "用户审核不通过")
    public ApiResponse notPassUser(@PathVariable(value = "id") Long id) {
        return userService.notPassUser(id);
    }

    @PostMapping("/forget")
    @OpeLog(opeModule = "用户管理-App忘记密码", opeType = OperationType.ADD, opeDesc = "App忘记密码")
    public ApiResponse forget(@RequestBody String jsonString) {
        return userService.forget(jsonString);
    }
}
