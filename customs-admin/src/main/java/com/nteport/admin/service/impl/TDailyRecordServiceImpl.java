package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.dto.TDailyRecordDto;
import com.nteport.admin.entity.*;
import com.nteport.admin.entity.system.*;
import com.nteport.admin.mapper.*;
import com.nteport.admin.service.TDailyRecordService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IUserRoleService;
import com.nteport.admin.service.system.impl.PageHelperImpl;
import com.nteport.admin.util.ConstantUtil;
import com.nteport.admin.util.DateUtil;
import com.nteport.admin.util.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* @author qianjun
* @description 针对表【t_daily_record(每日巡)】的数据库操作Service实现
* @createDate 2022-07-27 15:46:13
*/
@Service
public class TDailyRecordServiceImpl extends ServiceImpl<TDailyRecordMapper, TDailyRecordEntity>
    implements TDailyRecordService{

    @Autowired
    private TDailyRecordMapper tDailyRecordMapper;
    @Autowired
    private TDailyRecordDetailMapper tDailyRecordDetailMapper;
    @Autowired
    private TPointInfoMapper tPointInfoMapper;
    @Autowired
    private TLineInfoMapper lineInfoMapper;
    @Autowired
    private TTaskExecuteMapper tTaskExecuteMapper;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private TDeptMapper deptMapper;

    @Override
    public ApiResponse create(UserEntity user) {
        ApiResponse response = new ApiResponse();
        try{
            if(user == null) {
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            }else {
                TDailyRecordEntity entity = new TDailyRecordEntity();
                LocalDateTime time = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String patrolTime = formatter.format(time);
                entity.createTimeStamp(user);
                entity.setPatrolTime(patrolTime);
                entity.setStartTime(time);
                entity.setType("1");
                entity.setCreateDept(user.getDeptId());
                TDeptEntity dept = deptMapper.selectById(user.getDeptId());
                entity.setCreateDeptName(dept.getDeptName());
                TDeptEntity deptEntity = deptMapper.selectById(user.getDeptId());
                entity.setAreaId(deptEntity.getAreaId());
                entity.setArea(deptEntity.getArea());
                entity.setStreetId(deptEntity.getStreetId());
                entity.setStreet(deptEntity.getStreet());
                entity.setStatus("0");//未完成
                tDailyRecordMapper.insert(entity);
                return ApiResponse.success(entity.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse createPeriod(UserEntity user) {
        ApiResponse response = new ApiResponse();
        try{
            if(user == null) {
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            }else {
                TDailyRecordEntity entity = new TDailyRecordEntity();
                LocalDateTime time = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String patrolTime = formatter.format(time);
                entity.createTimeStamp(user);
                entity.setPatrolTime(patrolTime);
                entity.setStartTime(time);
                entity.setType("2");
                entity.setCreateDept(user.getDeptId());
                TDeptEntity dept = deptMapper.selectById(user.getDeptId());
                entity.setCreateDeptName(dept.getDeptName());
                TDeptEntity deptEntity = deptMapper.selectById(user.getDeptId());
                entity.setAreaId(deptEntity.getAreaId());
                entity.setArea(deptEntity.getArea());
                entity.setStreetId(deptEntity.getStreetId());
                entity.setStreet(deptEntity.getStreet());
                entity.setStatus("0");//未完成
                tDailyRecordMapper.insert(entity);
                return ApiResponse.success(entity.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse createTask(Long id,UserEntity user) {
        ApiResponse response = new ApiResponse();
        try{
//            if(null!=redisUtils.get("taskExecute_"+id)){
//                return ApiResponse.fail("该指令有人正在执行，请稍后再试");
//            }else{
//                redisUtils.setWithTime("taskExecute_"+id, id, 6L, TimeUnit.HOURS);
//            }
            if(user == null) {
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            }else {
                TDailyRecordEntity entity = new TDailyRecordEntity();
                LocalDateTime time = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String patrolTime = formatter.format(time);
                entity.createTimeStamp(user);
                entity.setPatrolTime(patrolTime);
                entity.setStartTime(time);
                entity.setType("3");
                entity.setTaskExecuteId(id);
                entity.setCreateDept(user.getDeptId());
                TDeptEntity dept = deptMapper.selectById(user.getDeptId());
                entity.setCreateDeptName(dept.getDeptName());
                TDeptEntity deptEntity = deptMapper.selectById(user.getDeptId());
                entity.setAreaId(deptEntity.getAreaId());
                entity.setArea(deptEntity.getArea());
                entity.setStreetId(deptEntity.getStreetId());
                entity.setStreet(deptEntity.getStreet());
                entity.setStatus("0");//未完成
                tDailyRecordMapper.insert(entity);
                return ApiResponse.success(entity.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse listPage(String jsonString,UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(user == null || jsonString == null){
            response.fillMessage(EnumCode.BAD_REQUEST);
            return response;
        }
        //分页
        JSONObject json = JSON.parseObject(jsonString);
        if (!json.containsKey("page") || !json.containsKey("limit")) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        // 组装分页配置
        long pageNum = Long.parseLong(json.getString("page"));
        long pageSize = Long.parseLong(json.getString("limit"));
        IPage<TDailyRecordEntity> dailyPage = new Page<>(pageNum, pageSize);
        Map params = (Map) JSON.parse(jsonString);
        // 展示权限控制
        List<RoleEntity> roleList = userRoleService.getRoles(user.getId());
        List<String> roleCodes = new ArrayList<>();
        for (RoleEntity role :
                roleList) {
            roleCodes.add(role.getRoleCode());
        }
        if(roleCodes.size() <= 0){
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
        //查管理员所在的部门
        QueryWrapper<TDeptEntity> deptWrapper = new QueryWrapper<>();
        deptWrapper.eq("id",user.getDeptId());
        TDeptEntity dept = deptMapper.selectOne(deptWrapper);
        if(dept == null){
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
        // 组装查询条件
        IPageHelper<TDailyRecordEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<TDailyRecordEntity> queryWrapper = pageHelper.query(params);
        if(roleCodes.contains(ConstantUtil.ROLE_ADMIN) || roleCodes.contains(ConstantUtil.ROLE_CITYADMIN) || roleCodes.contains(ConstantUtil.ROLE_SJBMADMIN)){
        }else if(roleCodes.contains(ConstantUtil.ROLE_QXDSBADMIN) || roleCodes.contains(ConstantUtil.ROLE_QXBMADMIN)){
            queryWrapper.eq("area_id",dept.getAreaId());
        }else if(roleCodes.contains(ConstantUtil.ROLE_XZJDADMIN)){
            queryWrapper.eq("street_id",dept.getStreetId());
        }else{
            queryWrapper.eq("create_user",user.getId());
        }
        queryWrapper.eq("type","1");
        queryWrapper.orderByDesc("create_time");
        dailyPage = tDailyRecordMapper.selectPage(dailyPage,queryWrapper);
        List<TDailyRecordEntity> list = dailyPage.getRecords();
        Long total = tDailyRecordMapper.selectCount(queryWrapper);
        // 组装json数据
        HashMap data = new HashMap();
        data.put("items", list);
        data.put("total", total);
        return response.fillMessage(data);
    }

    @Override
    public ApiResponse updateInfo(Long id, String result, UserEntity user) {
        ApiResponse response = new ApiResponse();
        try {
            if(id == null || result == null || user == null){
                return response.fillMessage(EnumCode.BAD_REQUEST);
            }
            TDailyRecordEntity entity = tDailyRecordMapper.selectById(id);
            entity.setResult(result);
            entity.updateTimeStamp(user);
            LocalDateTime endTime = LocalDateTime.now();
            entity.setEndTime(endTime);
            entity.setStatus("1");//完成
            tDailyRecordMapper.updateById(entity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_HANDLE);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse updateTask(Long id, String result, UserEntity user) {
        ApiResponse response = new ApiResponse();
        try {
            if(id == null || result == null || user == null){
                return response.fillMessage(EnumCode.BAD_REQUEST);
            }
            TDailyRecordEntity entity = tDailyRecordMapper.selectById(id);
            entity.setResult(result);
            entity.updateTimeStamp(user);
            LocalDateTime endTime = LocalDateTime.now();
            entity.setEndTime(endTime);
            entity.setStatus("1");//完成
            tDailyRecordMapper.updateById(entity);
            //更新指令执行信息 2022-09 删除
//            TTaskExecuteEntity tTaskExecuteEntity = tTaskExecuteMapper.selectById(entity.getTaskExecuteId());
//            tTaskExecuteEntity.setExecuteTime(DateUtil.getNowTime());
//            tTaskExecuteEntity.setExecuted("1");
//            tTaskExecuteMapper.updateById(tTaskExecuteEntity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_HANDLE);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse statRecords() {
        ApiResponse response = new ApiResponse();
        try{
            List list = tDailyRecordMapper.statRecords();
            return response.fillMessage(list);
        }catch (Exception e){
            e.printStackTrace();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse delete(Long id){
        ApiResponse response = new ApiResponse();
        if(id == null){
            return response.fillMessage(EnumCode.BAD_REQUEST);
        }
        try {
            tDailyRecordMapper.deleteById(id);
            QueryWrapper<TDailyRecordDetailEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("head_id",id);
            tDailyRecordDetailMapper.delete(queryWrapper);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse listPageByTask(String jsonString,UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(user == null || jsonString == null){
            response.fillMessage(EnumCode.BAD_REQUEST);
            return response;
        }
        //分页
        JSONObject json = JSON.parseObject(jsonString);
        if (!json.containsKey("page") || !json.containsKey("limit")) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        // 组装分页配置
        long pageNum = Long.parseLong(json.getString("page"));
        long pageSize = Long.parseLong(json.getString("limit"));
        //获取任务id(原来是任务执行id)
        long taskExecuteId = Long.parseLong(json.getString("taskExecuteId"));
        IPage<TDailyRecordEntity> dailyPage = new Page<>(pageNum, pageSize);
        Map params = (Map) JSON.parse(jsonString);

        // 组装查询条件
        IPageHelper<TDailyRecordEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<TDailyRecordEntity> queryWrapper = pageHelper.query(params);
        queryWrapper.eq("task_execute_id",taskExecuteId);
        queryWrapper.eq("type","3");
        queryWrapper.orderByDesc("create_time");
        dailyPage = tDailyRecordMapper.selectPage(dailyPage,queryWrapper);
        List<TDailyRecordEntity> list = dailyPage.getRecords();
        Long total = tDailyRecordMapper.selectCount(queryWrapper);
        // 组装json数据
        HashMap data = new HashMap();
        data.put("items", list);
        data.put("total", total);
        return response.fillMessage(data);
    }

    @Override
    public ApiResponse queryXuncha(String yearMonth) {
        return ApiResponse.success(tDailyRecordMapper.queryXuncha(yearMonth));
    }

    @Override
    public ApiResponse queryXunchaContent() {
        return ApiResponse.success(tDailyRecordMapper.queryXunchaContent());
    }

}
