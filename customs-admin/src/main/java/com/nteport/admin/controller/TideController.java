package com.nteport.admin.controller;

import com.alibaba.fastjson.JSON;
import com.nteport.admin.entity.TTideEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TTideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tide")
public class TideController {
    @Autowired
    private TTideService tTideService;

    /**
     * 新建点位
     * @param jsonString
     * @param user
     * @return
     */
    @PostMapping("/create")
    public ApiResponse create(@RequestBody String jsonString, UserEntity user){
        return tTideService.create(jsonString,user);
    }

    /**
     * 审核相关
     * @param id
     * @param status 前端请求：1-提交审核，2-审核通过，3-审核未通过
     * @param user
     * @return
     */
//    @GetMapping("/audit/{id}/{status}")
//    public ApiResponse audit(@PathVariable Long id,@PathVariable String status,UserEntity user){
//        return tPointInfoService.audit(id,status,user);
//    }

    /**
     * 修改信息
     */
    @PostMapping("/update/{id}")
    public ApiResponse updateInfo(@PathVariable(value = "id") Long id,@RequestBody String jsonString,UserEntity user){
        return tTideService.updateInfo(id,jsonString,user);
    }

    @PostMapping("/update")
    public ApiResponse updateInfo(@RequestBody String jsonString){
        TTideEntity tTideEntity = JSON.parseObject(jsonString, TTideEntity.class);
        tTideService.updateById(tTideEntity);
        return ApiResponse.success();
    }

    /**
     * 修改信息
     */
    @PostMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable(value = "id") Long id,UserEntity user){
        tTideService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 列表查询
     */
    @GetMapping("/listPage")
    public ApiResponse listPage(@RequestParam Map<String, String> params){
        return tTideService.listPage(params);
    }


    @GetMapping("/queryInfoById/{id}")
    public ApiResponse queryInfoById(@PathVariable(value = "id") Long id){
        return ApiResponse.success(tTideService.getById(id));
    }

    /**
     * 查询当日潮汐时间
     */
    @GetMapping("/queryToday")
    public ApiResponse queryToday(){
        return tTideService.queryToday();
    }

    /**
     * 判断此时是否在涨潮时间内
     */
    @GetMapping("/ifInTide")
    public Boolean ifInTide(){
        return tTideService.ifInTide();
    }
}
