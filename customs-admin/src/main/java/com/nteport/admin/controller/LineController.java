package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TLineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/line")
public class LineController {

    @Autowired
    private TLineInfoService lineInfoService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody String jsonString, UserEntity user){
        return lineInfoService.create(jsonString,user);
    }

    /**
     * 审核相关
     * @param id
     * @param status 前端请求：1-提交审核，2-审核通过，3-审核未通过
     * @param user
     * @return
     */
    @GetMapping("/audit/{id}/{status}")
    public ApiResponse audit(@PathVariable Long id,@PathVariable String status,UserEntity user){
        return lineInfoService.audit(id,status,user);
    }

    /**
     * 修改点位信息
     */
    @PostMapping("/update")
    public ApiResponse updateInfo(@RequestBody String jsonString,UserEntity user){
        return lineInfoService.updateInfo(jsonString,user);
    }

    /**
     * 列表查询
     */
    @GetMapping("/listPage")
    public ApiResponse listPage(@RequestParam Map<String, String> params, UserEntity user){
        return lineInfoService.listPage(params,user);
    }

    /**
     * 根据名字模糊查询点位
     * @param params
     * @return
     */
    @GetMapping("/queryLineByName")
    public ApiResponse queryLineByName(@RequestParam Map<String, String> params){
        return lineInfoService.queryLineByName(params);
    }

    /**
     * 根据Id查询点位
     * @param params
     * @return
     */
    @GetMapping("/queryLineById")
    public ApiResponse queryLineById(@RequestParam Map<String, String> params){
        Long id = Long.valueOf(params.get("Id"));
        return ApiResponse.success(lineInfoService.getById(id));
    }

    /**
     * 根据id查询完整信息 点位信息和人员信息
     * @param id
     * @return
     */
    @GetMapping("/queryInfoById/{id}")
    public ApiResponse queryInfoById(@PathVariable Long id){
        return lineInfoService.queryInfoById(id);
    }

    /**
     * 查询所有巡查点位
     * @return
     */
    @GetMapping("/queryLines")
    public ApiResponse queryLines(){
        return lineInfoService.queryLines();
    }

    /**
     * 点位统计mobile 首页
     *
     * @return
     */
    @GetMapping("/lineCount")
    public ApiResponse lineCount(){
        return lineInfoService.lineCount();
    }

    @PostMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id){
        return ApiResponse.success(lineInfoService.removeById(id));
    }
}
