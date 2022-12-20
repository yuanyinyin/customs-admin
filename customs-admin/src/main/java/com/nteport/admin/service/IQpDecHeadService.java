package com.nteport.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.QpDecHead;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface IQpDecHeadService extends IService<QpDecHead> {

    /**
     * 获取用户列表，带分页
     */
    ApiResponse listPage(Map<String, String> params, UserEntity user);




    ApiResponse showOne(String headId, UserEntity user);


    void exportExcel(Map<String, String> params, HttpServletRequest request , HttpServletResponse response ,UserEntity user) ;


    ApiResponse printDec(Map<String, String> params);


}
