package com.nteport.admin.controller;

import com.nteport.admin.entity.QpDecHead;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.OperationType;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.QpDecHeadMapper;
import com.nteport.admin.service.IQpDecHeadService;
import com.nteport.admin.service.system.IUserService;
import com.nteport.admin.service.system.OpeLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/qpDecHead")
public class QPDecHeadController {

    @Autowired
    private IQpDecHeadService qpDecHeadService;


    @Autowired
    private QpDecHeadMapper qpDecHeadMapper;





    /**
     * 分页查询用户
     *
     * @param params Map[pageNum 当前页数, pageSize 每页大小]
     * @return Json[list 用户列表，total 结果总数] 或 状态码
     */
    @GetMapping("/listPage")
    public ApiResponse listPage(@RequestParam Map<String, String> params, UserEntity user) {
        if(CollectionUtils.isEmpty(params)) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        return qpDecHeadService.listPage(params, user);
    }

    @PostMapping("add")
    public String add(@RequestBody QpDecHead qpDecHead){
        qpDecHeadMapper.insert(qpDecHead);
        return "success";
    }
    @GetMapping("/showOne")
    public ApiResponse showOne(@RequestParam String headId, UserEntity user) {
        if(StringUtils.isBlank(headId)) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        return qpDecHeadService.showOne(headId, user);
    }



    /**
     * 导出excel
     */
    @GetMapping("/exportExcel")
    public void exportExcel(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response){
        qpDecHeadService.exportExcel(params,request,response);
    }









}
