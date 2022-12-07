package com.nteport.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.QpDecContainer;
import com.nteport.admin.entity.QpDecFreeTxt;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

public interface IQpDecFreeTxtService extends IService<QpDecFreeTxt> {

    /**
     * 获取用户列表，带分页
     */
    ApiResponse listPage(Map<String, String> params, UserEntity user);


}
