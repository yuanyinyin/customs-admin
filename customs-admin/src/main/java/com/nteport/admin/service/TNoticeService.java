package com.nteport.admin.service;

import com.nteport.admin.entity.TNoticeEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_notice】的数据库操作Service
* @createDate 2022-09-16 15:38:03
*/
public interface TNoticeService extends IService<TNoticeEntity> {

    ApiResponse create(String jsonString, UserEntity user);

    ApiResponse delete(Long id);

    ApiResponse update(Long id, UserEntity user, String jsonString);

    ApiResponse listPage(Map<String, String> params, UserEntity user);
}
