package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TPointInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    private TPointInfoService tPointInfoService;

    /**
     * 新建点位（暂存）
     * @param jsonString
     * @param user
     * @return
     */
    @PostMapping("/create")
    public ApiResponse create(@RequestBody String jsonString, UserEntity user){
        return tPointInfoService.create(jsonString,user);
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
        return tPointInfoService.audit(id,status,user);
    }

    /**
     * 修改点位信息
     */
    @PostMapping("/update")
    public ApiResponse updateInfo(@RequestBody String jsonString,UserEntity user){
        return tPointInfoService.updateInfo(jsonString,user);
    }

    /**
     * 列表查询
     */
    @GetMapping("/listPage")
    public ApiResponse listPage(@RequestParam Map<String, String> params, UserEntity user){
        return tPointInfoService.listPage(params, user);
    }

    /**
     * 根据名字模糊查询点位
     * @param params
     * @return
     */
    @GetMapping("/queryPointByName")
    public ApiResponse queryPointByName(@RequestParam Map<String, String> params){
        return tPointInfoService.queryPointByName(params);
    }

    /**
     * 根据Id查询点位
     * @param params
     * @return
     */
    @GetMapping("/queryPointById")
    public ApiResponse queryPointById(@RequestParam Map<String, String> params){
        Long id = Long.valueOf(params.get("Id"));
        return ApiResponse.success(tPointInfoService.getById(id));
    }

    /**
     * 根据id查询完整信息 点位信息和人员信息
     * @param id
     * @return
     */
    @GetMapping("/queryInfoById/{id}")
    public ApiResponse queryInfoById(@PathVariable Long id){
        return tPointInfoService.queryInfoById(id);
    }

    /**
     * 查询所有巡查点位
     * @return
     */
    @GetMapping("/queryPoints")
    public ApiResponse queryPoints(){
        return tPointInfoService.queryPoints();
    }

    /**
     * 查询沿江和沿海点位信息
     * @return
     */
    @GetMapping("/getAllPoints")
    public ApiResponse getAllPoints(){
        return tPointInfoService.getAllPoints();
    }

    /**
     * 点位统计mobile 首页
     *
     * @return
     */
    @GetMapping("/pointCount")
    public ApiResponse pointCount(){
        return tPointInfoService.pointCount();
    }

    /**
     * 查最近5条更新记录 信息播报 首页
     */
    @GetMapping("/getUpdateList")
    public ApiResponse getUpdateList(UserEntity user){
        return tPointInfoService.getUpdateList(user);
    }

    @PostMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id){
        return ApiResponse.success(tPointInfoService.removeById(id));
    }
}
