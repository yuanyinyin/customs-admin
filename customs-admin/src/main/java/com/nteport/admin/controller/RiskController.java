package com.nteport.admin.controller;

import com.nteport.admin.entity.TPointInfoEntity;
import com.nteport.admin.entity.TRiskEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TPointInfoService;
import com.nteport.admin.service.TRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/risk")
public class RiskController {
    @Autowired
    private TRiskService tRiskService;

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
        return tRiskService.create(jsonString,user);
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
        return tRiskService.updateInfo(id,jsonString,user);
    }

    /**
     * 修改信息
     */
    @PostMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable(value = "id") Long id,UserEntity user){
        tRiskService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 列表查询
     */
    @GetMapping("/listPage")
    public ApiResponse listPage(@RequestParam Map<String, String> params,UserEntity user){
        return tRiskService.listPage(params,user);
    }

    @GetMapping("/queryInfoById/{id}")
    public ApiResponse queryInfoById(@PathVariable(value = "id") Long id){
        TRiskEntity entity = tRiskService.getById(id);
        if(0!=entity.getPointId()){
            TPointInfoEntity pointEntity = tPointInfoService.getById(entity.getPointId());
            entity.setPointName(pointEntity.getName());
        }
        return ApiResponse.success(entity);
    }


    /**
     * 更新处理结果
     */
    @PostMapping("/updateResult")
    public ApiResponse updateResult(@RequestBody String jsonString,UserEntity user){
        return tRiskService.updateResult(jsonString,user);
    }

    @GetMapping("/statRisks")
    public ApiResponse statRisks(){
        return tRiskService.statRisks();
    }

    @GetMapping("/getHandleList")
    public ApiResponse getHandleList(UserEntity user){
        return tRiskService.getHandleList(user);
    }
}
