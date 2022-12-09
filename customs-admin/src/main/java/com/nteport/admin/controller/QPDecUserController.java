package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.IQpDecLogService;
import com.nteport.admin.service.system.IQpDecUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/qpDecUser")
public class QPDecUserController {

    @Autowired
    private IQpDecUserService qpDceUserService;

    /**
     * 分页查询用户
     *
     * @param params Map[pageNum 当前页数, pageSize 每页大小]
     * @return Json[list 用户列表，total 结果总数] 或 状态码
     */
    @GetMapping("/list")
    public ApiResponse list(@RequestParam Map<String, String> params, UserEntity user) {
        if(CollectionUtils.isEmpty(params)) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        return qpDceUserService.list(params, user);
    }







}
