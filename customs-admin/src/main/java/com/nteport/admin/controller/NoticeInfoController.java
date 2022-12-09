package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.system.INoticeInfoService;
import com.nteport.admin.service.system.IQpDecUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/noticeInfo")
public class NoticeInfoController {

    @Autowired
    private INoticeInfoService noticeInfoService;

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
        return noticeInfoService.listPage(params, user);
    }







}
