package com.nteport.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.TTaskExecuteEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

/**
* @author User
* @description 针对表【t_task_execute(指令执行表)】的数据库操作Service
* @createDate 2022-09-16 19:23:28
*/
public interface TTaskExecuteService extends IService<TTaskExecuteEntity> {

    ApiResponse create(String jsonString, UserEntity user);

    ApiResponse updateInfo(Long id,String jsonString, UserEntity user);

    ApiResponse listPage(Map<String, String> params, UserEntity user);

    void updateLate();

}
