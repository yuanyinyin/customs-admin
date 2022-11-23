package com.nteport.admin.service;

import com.nteport.admin.entity.TLineInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_line_info】的数据库操作Service
* @createDate 2022-09-07 16:37:24
*/
public interface TLineInfoService extends IService<TLineInfoEntity> {

    ApiResponse create(String jsonString, UserEntity user);

    ApiResponse audit(Long id, String status, UserEntity user);

    ApiResponse updateInfo(String jsonString, UserEntity user);

    ApiResponse listPage(Map<String, String> params, UserEntity user);

    ApiResponse queryLineByName(Map<String, String> params);

    ApiResponse queryInfoById(Long id);

    ApiResponse queryLines();

    ApiResponse lineCount();

    /**
     * 大屏
     * 岸线统计 区县
     * 2022-10
     * @return
     */
    ApiResponse queryLineGroupByArea();
}
