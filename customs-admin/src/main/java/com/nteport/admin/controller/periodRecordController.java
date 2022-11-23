package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TPeriodRecordDetailService;
import com.nteport.admin.service.TPeriodRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/periodRecord")
public class periodRecordController {
    @Autowired
    private TPeriodRecordService tPeriodRecordService;
    @Autowired
    private TPeriodRecordDetailService tPeriodRecordDetailService;

    /**
     * 新增定期巡查
     * @param jsonString
     * @param user
     * @return
    @PostMapping("/create")
    */
    /*public ApiResponse create(@RequestBody String jsonString, UserEntity user){
        return tPeriodRecordService.create(jsonString,user);
    }*/

    /**
     * 查询
     */
    @PostMapping("/listPage")
    public ApiResponse listPage(@RequestBody String jsonString, UserEntity user){
        return tPeriodRecordService.listPage(jsonString,user);
    }

    /**
     * 根据id查询子表
     */
    @GetMapping("/detail/{id}")
    public ApiResponse getDetail(@PathVariable Long id){
        return tPeriodRecordDetailService.getDetail(id);
    }
}
