package com.nteport.admin.service;

import com.nteport.admin.entity.TDeptEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;

import java.util.List;

/**
* @author qianjun
* @description 针对表【t_dept(部门表)】的数据库操作Service
* @createDate 2022-07-27 16:14:21
*/
public interface TDeptService extends IService<TDeptEntity> {

    /**
     * pc-获取部门树
     * @return
     */
    ApiResponse getDeptTree();
    List<TDeptEntity> getDeptTree2();

    ApiResponse add(String jsonString, UserEntity user);

    ApiResponse updateDept(String jsonString, UserEntity user);

    ApiResponse removeDept(String id);

    /**
     * app-获取除市打私办之外的子部门树
     * @return
     */
    ApiResponse getDeptTreeChildren();

    /**
     * app-根据部门获取人员
     * @return
     */
    ApiResponse getUserByDept(String id);

    ApiResponse getDeptById(String id);
    /**
     * 根据部门ID 获取部门数据
     * @param id
     * @return
     */
    TDeptEntity getDeptEntityById(String id);

    /**
     * 根据部门id查询这个地区所有部门
     */
    List<String> queryAreaDept(String id);

}
