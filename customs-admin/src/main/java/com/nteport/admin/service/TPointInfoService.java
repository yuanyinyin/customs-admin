package com.nteport.admin.service;

import com.nteport.admin.entity.TPointInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_point_info】的数据库操作Service
* @createDate 2022-07-27 15:46:13
*/
public interface TPointInfoService extends IService<TPointInfoEntity> {

    ApiResponse create(String jsonString, UserEntity user);

    ApiResponse audit(Long id,String status,UserEntity user);

    ApiResponse updateInfo(String jsonString, UserEntity user);

    ApiResponse listPage(Map<String, String> params, UserEntity user);

    /**
     * 根据名字模糊查询点位
     * @param params
     * @return
     */
    ApiResponse queryPointByName(Map<String, String> params);

    /**
     * 查询完整信息
     * @param id
     * @return
     */
    ApiResponse queryInfoById(Long id);

    ApiResponse queryPoints();

    ApiResponse getAllPoints();

    ApiResponse pointCount();

    ApiResponse getUpdateList(UserEntity user);

    /**
     * 大屏
     * 点位统计 类型
     * 2022-10
     * @return
     */
    ApiResponse queryPointGroupByType();

    /**
     * 大屏
     * 点位统计 区县
     * 2022-10
     * @return
     */
    ApiResponse queryPointGroupByArea();

    /**
     * 大屏
     * 案发情况
     * @param year
     * @return
     */
    ApiResponse queryAnfaCount(String year);

    /**
     * 大屏
     * 风险等级个数
     * @return
     */
    ApiResponse queryPointRiskLevel();

    ApiResponse queryApprovedPoints();
}
