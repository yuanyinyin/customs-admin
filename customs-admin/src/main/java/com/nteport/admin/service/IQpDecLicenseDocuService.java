package com.nteport.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.QpDecFreeTxt;
import com.nteport.admin.entity.QpDecLicenseDocu;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

public interface IQpDecLicenseDocuService extends IService<QpDecLicenseDocu> {

    /**
     * 获取用户列表，带分页
     */
    ApiResponse listPage(Map<String, String> params, UserEntity user);


}
