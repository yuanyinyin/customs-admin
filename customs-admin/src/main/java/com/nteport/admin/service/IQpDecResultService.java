package com.nteport.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.QpDecOtherPack;
import com.nteport.admin.entity.QpDecResult;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

public interface IQpDecResultService extends IService<QpDecResult> {


    ApiResponse listPage(Map<String, String> params, UserEntity user);


}
