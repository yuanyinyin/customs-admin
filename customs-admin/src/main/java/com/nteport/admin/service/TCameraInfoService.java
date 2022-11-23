package com.nteport.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.TCameraInfo;
import com.nteport.admin.entity.system.ApiResponse;

/**
* @author User
* @description 针对表【t_camera_info】的数据库操作Service
* @createDate 2022-11-08 09:19:10
*/
public interface TCameraInfoService extends IService<TCameraInfo> {

    ApiResponse queryCameras();
}
