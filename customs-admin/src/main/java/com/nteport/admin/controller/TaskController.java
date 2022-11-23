package com.nteport.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nteport.admin.entity.TTaskEntity;
import com.nteport.admin.entity.TTaskPersonEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TTaskPersonService;
import com.nteport.admin.service.TTaskService;
import com.nteport.admin.service.system.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TTaskService tTaskService;

    @Autowired
    private IUserService userService;

    @Autowired
    private TTaskPersonService taskPersonService;

    /**
     * 新建点位
     * @param jsonString
     * @param user
     * @return
     */
    @PostMapping("/create")
    public ApiResponse create(@RequestBody String jsonString, UserEntity user){
        return tTaskService.create(jsonString,user);
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
        return tTaskService.updateInfo(id,jsonString,user);
    }

    @PostMapping("/update")
    public ApiResponse update(@RequestBody String jsonString){
        TTaskEntity tTideEntity = JSON.parseObject(jsonString, TTaskEntity.class);
        tTaskService.updateById(tTideEntity);
        return ApiResponse.success();
    }

    /**
     * 修改信息
     */
    @PostMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable(value = "id") Long id,UserEntity user){
        tTaskService.removeById(id);
        return ApiResponse.success();
    }

    /**
     * 列表查询
     */
    @GetMapping("/listPage")
    public ApiResponse listPage(@RequestParam Map<String, String> params, UserEntity user){
        return tTaskService.listPage(params,user);
    }

    @GetMapping("/queryAllPerson")
    public ApiResponse queryAllPerson(){
        return userService.selectAllUserAndDept();
    }

    @GetMapping("/queryPersonByHeadId/{id}")
    public ApiResponse queryPersonByHeadId(@PathVariable(value = "id") Long id){
        QueryWrapper<TTaskPersonEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("head_id",id);
        List<TTaskPersonEntity> list = taskPersonService.list(queryWrapper);
        List<Long> personList = new ArrayList<>();
        for(TTaskPersonEntity entity:list){
            personList.add(entity.getPersonId());
        }
        return  ApiResponse.success(personList);
    }

    @GetMapping("/queryInfoById/{id}")
    public ApiResponse queryInfoById(@PathVariable(value = "id") Long id){
        return ApiResponse.success(tTaskService.getById(id));
    }
}
