package com.nteport.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.QpDecFreeTxt;
import com.nteport.admin.entity.QpDecUser;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

public interface IQpDecUserService extends IService<QpDecUser> {

    /**
     * 获取用户列表，带分页
     */
    ApiResponse list(Map<String, String> params, UserEntity user);


}
