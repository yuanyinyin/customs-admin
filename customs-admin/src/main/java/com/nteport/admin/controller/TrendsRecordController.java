package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TTrendsRecordDetailService;
import com.nteport.admin.service.TTrendsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trendsRecord")
public class TrendsRecordController {
    @Autowired
    private TTrendsRecordService tTrendsRecordService;

    @Autowired
    private TTrendsRecordDetailService tTrendsRecordDetailService;
    /**
     * 新增定期巡查
     * @param jsonString
     * @param user
     * @return
     */
    @PostMapping("/create")
    public ApiResponse create(@RequestBody String jsonString, UserEntity user){
        return tTrendsRecordService.create(jsonString,user);
    }

    /**
     * 查询
     */
    @PostMapping("/listPage")
    public ApiResponse listPage(@RequestBody String jsonString, UserEntity user){
        return tTrendsRecordService.listPage(jsonString,user);
    }

    /**
     * 根据id查询子表
     */
    @GetMapping("/detail/{id}")
    public ApiResponse getDetail(@PathVariable Long id){
        return tTrendsRecordDetailService.getDetail(id);
    }
}
