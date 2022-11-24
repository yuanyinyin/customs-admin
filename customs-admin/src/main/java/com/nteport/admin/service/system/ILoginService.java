package com.nteport.admin.service.system;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.ArrayList;

public interface ILoginService {

    /**
     * 登陆验证
     */
    ApiResponse loginValidate(String jsonString);

    /**
     * app登陆验证
     */
    ApiResponse mobileLogin(String jsonString);

    ApiResponse autoLogin(String jsonString);

    /**
     * 登出
     */
    ApiResponse logout(UserEntity user);

    ApiResponse getVerifyCode(String jsonString);

    ApiResponse statLogin();

    ApiResponse statLoginMobile();


    ArrayList<String> queryRoleCodes(UserEntity user);
}
