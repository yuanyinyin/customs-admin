package com.nteport.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nteport.admin.entity.TDailyRecordEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TDailyRecordDetailService;
import com.nteport.admin.service.TDailyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dailyRecord")
public class DailyRecordController {
    @Autowired
    private TDailyRecordService tDailyRecordService;
    @Autowired
    private TDailyRecordDetailService tDailyRecordDetailService;
    /**
     * 新增每日巡查
     * @param user
     * @return
     */
    @PostMapping("/create")
    public ApiResponse create(UserEntity user){
        return tDailyRecordService.create(user);
    }

    /**
     * 新增定期查
     * @param user
     * @return
     */
    @PostMapping("/createPeriod")
    public ApiResponse createPeriod(UserEntity user){
        return tDailyRecordService.createPeriod(user);
    }

    /**
     * 新增巡查指令的巡查
     * @param user
     * @return
     */
    @PostMapping("/createTask/{id}")
    public ApiResponse createTask(@PathVariable Long id,UserEntity user){
        return tDailyRecordService.createTask(id,user);
    }


    /**
     * 查询
     */
    @PostMapping("/listPage")
    public ApiResponse listPage(@RequestBody String jsonString, UserEntity user){
        return tDailyRecordService.listPage(jsonString,user);
    }

    /**
     * 根据id查询子表
     */
    @GetMapping("/detail/{id}")
    public ApiResponse getDetail(@PathVariable Long id){
        return tDailyRecordDetailService.getDetail(id);
    }

    /**
     * 打卡记录增加
     * @param id
     * @param jsonString
     * @param user
     * @return
     */
    @PostMapping("/addClockIn/{id}")
    public ApiResponse addClockIn(@PathVariable Long id,@RequestBody String jsonString,UserEntity user){
        return tDailyRecordDetailService.addClockIn(id,jsonString,user);
    }

    /**
     * 手机端结束巡查后，更新信息
     */
    @PostMapping("/update/{id}")
    public ApiResponse update(@PathVariable Long id,@RequestBody String result,UserEntity user){
        return tDailyRecordService.updateInfo(id,result,user);
    }

    /**
     * 指令执行结束
     * @param id
     * @param result
     * @param user
     * @return
     */
    @PostMapping("/updateTask/{id}")
    public ApiResponse updateTask(@PathVariable Long id,@RequestBody String result,UserEntity user){
        return tDailyRecordService.updateTask(id,result,user);
    }

    /**
     * 统计巡查记录
     * @return
     */
    @GetMapping("/statRecords")
    public ApiResponse statRecords(){
        return tDailyRecordService.statRecords();
    }

    /**
     * 取消巡查时删除记录
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id){
        return tDailyRecordService.delete(id);
    }

    /**
     * 根据指令执行查询对应的巡查
     * @param id
     * @return
     */
    @GetMapping("/queryByTaskExecute/{id}")
    public ApiResponse queryByTaskExecute(@PathVariable Long id){
        QueryWrapper<TDailyRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_execute_id",id);
        List<TDailyRecordEntity> list = tDailyRecordService.list(queryWrapper);
        if(null!=list && list.size()>0){
            return ApiResponse.success(list.get(0));
        }else{
            return ApiResponse.success(null);
        }
    }


    /**
     * 查询指令任务的巡查结果
     */
    @PostMapping("/listPageByTask")
    public ApiResponse listPageByTask(@RequestBody String jsonString, UserEntity user){
        return tDailyRecordService.listPageByTask(jsonString,user);
    }
}
