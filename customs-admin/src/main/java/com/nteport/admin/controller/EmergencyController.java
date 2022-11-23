package com.nteport.admin.controller;

import com.nteport.admin.entity.TEmergencyEntity;
import com.nteport.admin.entity.TPointInfoEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TEmergencyService;
import com.nteport.admin.service.TPointInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/emergency")
public class EmergencyController {
    @Autowired
    private TEmergencyService tEmergencyService;

    @Autowired
    private TPointInfoService tPointInfoService;

    /**
     * 新建点位
     * @param jsonString
     * @param user
     * @return
     */
    @PostMapping("/create")
    public ApiResponse create(@RequestBody String jsonString, UserEntity user){
        return tEmergencyService.create(jsonString,user);
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
        return tEmergencyService.updateInfo(id,jsonString,user);
    }

    /**
     * 修改信息
     */
    @PostMapping("/update")
    public ApiResponse updateInfo(@RequestBody String jsonString,UserEntity user){
        return tEmergencyService.updateInfo(jsonString,user);
    }

    /**
     * 修改信息
     */
    @PostMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable(value = "id") Long id,UserEntity user){
        tEmergencyService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 列表查询
     */
    @GetMapping("/listPage")
    public ApiResponse listPage(@RequestParam Map<String, String> params,UserEntity user){
        return tEmergencyService.listPage(params,user);
    }

    @GetMapping("/queryInfoById/{id}")
    public ApiResponse queryInfoById(@PathVariable(value = "id") Long id){
        TEmergencyEntity entity = tEmergencyService.getById(id);
        if(0!=entity.getPointId()){
            TPointInfoEntity pointEntity = tPointInfoService.getById(entity.getPointId());
            entity.setPointName(pointEntity.getName());
        }
        return ApiResponse.success(entity);
    }

    @GetMapping("/statEmergency")
    public ApiResponse statEmergency() {
        return tEmergencyService.statEmergency();
    }

    /**
     * 更新处理结果
     */
    @PostMapping("/updateResult")
    public ApiResponse updateResult(@RequestBody String jsonString,UserEntity user){
        return tEmergencyService.updateResult(jsonString,user);
    }

    /**
     * 获取最新5条处置信息
     */
    @GetMapping("/getHandleList")
    public ApiResponse getHandleList(UserEntity user){
        return tEmergencyService.getHandleList(user);
    }
}
