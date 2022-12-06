package com.nteport.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.nteport.admin.entity.TDeptEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.OperationType;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TDeptService;
import com.nteport.admin.service.system.ILoginService;
import com.nteport.admin.service.system.IMenuService;
import com.nteport.admin.service.system.OpeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZZQ
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private TDeptService deptService;

    /**
     * 登陆验证
     *
     * @param jsonString [userName 用户名, password 密码]
     * @return
     */
    @PostMapping("/validate")
    @OpeLog(opeModule = "登录管理-用户登录", opeType = OperationType.QUERY, opeDesc = "用户登录")
    public ApiResponse loginValidate(@RequestBody String jsonString) {
        if (jsonString == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        return loginService.loginValidate(jsonString);
    }

    @PostMapping("/mobileLogin")
    @OpeLog(opeModule = "登录管理-用户登录", opeType = OperationType.QUERY, opeDesc = "用户登录")
    public ApiResponse mobileLogin(@RequestBody String jsonString) {
        if (jsonString == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        return loginService.mobileLogin(jsonString);
    }

    /**
     * App自动登录
     * @param jsonString
     * @return
     */
    @PostMapping("/autoLogin")
    @OpeLog(opeModule = "登录管理-用户自动登录", opeType = OperationType.QUERY, opeDesc = "用户自动登录")
    public ApiResponse autoLogin(@RequestBody String jsonString) {
        if (jsonString == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        return loginService.autoLogin(jsonString);
    }

    /**
     * 获取身份信息
     *
     * @param user 用户
     * @return
     */
    @GetMapping("/getUserInfo")
    public ApiResponse getUserInfo(UserEntity user) {
        if (user == null) {
            return ApiResponse.fail(EnumCode.UNAUTHORIZED);
        }
        if(null!=user.getDeptId()){
            TDeptEntity dept = deptService.getById(user.getDeptId());
            user.setDeptName(dept.getDeptName());
        }
        //角色
        user.setRoleCodes(loginService.queryRoleCodes(user));
        return ApiResponse.success(JSONObject.toJSON(user));
    }

    /**
     * 获取当前用户拥有的全部的菜单信息
     *
     * @param user 用户
     * @return
     */
    @GetMapping("/getMenuInfo")
    public ApiResponse getMenuInfo(UserEntity user) {
        return menuService.getMenuInfo(user.getToken());
    }

    /**
     * 登出
     *
     * @param user 用户
     * @return 状态码
     */
    @GetMapping("/logout")
    @OpeLog(opeModule = "登录管理-用户登出", opeType = OperationType.QUERY, opeDesc = "用户登出")
    public ApiResponse logout(UserEntity user) {
        return loginService.logout(user);
    }

    /**
     * 获取手机验证码
     * @param jsonString
     * @return
     */
    @PostMapping("/getVerifyCode")
    public ApiResponse getVerifyCode(@RequestBody String jsonString){
        return loginService.getVerifyCode(jsonString);
    }

    /**
     * 查询登录统计
     * @return
     */
    @GetMapping("/statLogin")
    public ApiResponse statLogin(){
        return loginService.statLogin();
    }

    /**
     * 查询登录统计mobile
     * @return
     */
    @GetMapping("/statLoginMobile")
    public ApiResponse statLoginMobile(){
        return loginService.statLoginMobile();
    }



}
