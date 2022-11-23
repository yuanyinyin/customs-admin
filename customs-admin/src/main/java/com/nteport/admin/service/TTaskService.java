package com.nteport.admin.service;

import com.nteport.admin.entity.TTaskEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_task(巡查指令表)】的数据库操作Service
* @createDate 2022-07-27 15:46:13
*/
public interface TTaskService extends IService<TTaskEntity> {
    /**
     * 2022-08-05 yinq
     * @param jsonString
     * @param user
     * @return
     */
    ApiResponse create(String jsonString, UserEntity user);

    ApiResponse updateInfo(Long id,String jsonString, UserEntity user);

    ApiResponse listPage(Map<String, String> params, UserEntity user);
}
