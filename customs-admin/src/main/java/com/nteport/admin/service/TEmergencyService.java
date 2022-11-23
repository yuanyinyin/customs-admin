package com.nteport.admin.service;

import com.nteport.admin.entity.TEmergencyEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_emergency(突发事件表)】的数据库操作Service
* @createDate 2022-07-27 15:46:13
*/
public interface TEmergencyService extends IService<TEmergencyEntity> {
    /**
     * 2022-08-05 yinq
     * @param jsonString
     * @param user
     * @return
     */
    ApiResponse create(String jsonString, UserEntity user);

    ApiResponse updateInfo(Long id,String jsonString, UserEntity user);

    ApiResponse updateInfo(String jsonString, UserEntity user);

    ApiResponse listPage(Map<String, String> params,UserEntity user);

    ApiResponse statEmergency();

    ApiResponse updateResult(String jsonString, UserEntity user);

    ApiResponse getHandleList(UserEntity user);
}
