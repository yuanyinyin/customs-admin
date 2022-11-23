package com.nteport.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.LogEntity;

public interface ILogService extends IService<LogEntity> {

    /**
     * 获取日志列表，带分页
     */
    ApiResponse listPage(String jsonString);

}
