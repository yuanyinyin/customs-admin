package com.nteport.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.QpDecHead;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

public interface IQpDecHeadService extends IService<QpDecHead> {

    /**
     * 获取用户列表，带分页
     */
    ApiResponse listPage(Map<String, String> params, UserEntity user);




    ApiResponse showOne(String headId, UserEntity user);






}
