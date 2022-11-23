package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TDeptEntity;
import com.nteport.admin.entity.TTaskEntity;
import com.nteport.admin.entity.TTaskExecuteEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.TDeptMapper;
import com.nteport.admin.mapper.TTaskExecuteMapper;
import com.nteport.admin.mapper.TTaskMapper;
import com.nteport.admin.service.TTaskExecuteService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IUserRoleService;
import com.nteport.admin.service.system.impl.PageHelperImpl;
import com.nteport.admin.util.ConstantUtil;
import com.nteport.admin.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;

/**
* @author User
* @description 针对表【t_task_execute(指令执行表)】的数据库操作Service实现
* @createDate 2022-09-16 19:23:28
*/
@Service
public class TTaskExecuteServiceImpl extends ServiceImpl<TTaskExecuteMapper, TTaskExecuteEntity>
implements TTaskExecuteService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private TDeptMapper deptMapper;

    @Autowired
    private TTaskMapper taskMapper;

    @Override
    @Transactional
    public ApiResponse create(String jsonString, UserEntity user) {
        if ("".equals(jsonString) || user == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        try{
            TTaskExecuteEntity taskExecuteEntity = JSON.parseObject(jsonString, TTaskExecuteEntity.class);
            taskExecuteEntity.createTimeStamp(user);
            this.baseMapper.insert(taskExecuteEntity);
            return ApiResponse.success();
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }
    
    @Override
    public ApiResponse updateInfo(Long id, String jsonString, UserEntity user) {
        if(jsonString == null || user == null || null==id){
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        TTaskExecuteEntity taskExecuteEntity = JSON.parseObject(jsonString, TTaskExecuteEntity.class);
        try {
            this.baseMapper.updateById(taskExecuteEntity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 发布权限和责任部门并集
     *  1.市打私办角色可以看到所有
     *  2.区县打私办角色只能看到自己部门发布的
     *  3.责任部门能看到
     * @param params
     * @param user
     * @return
     */
    public ApiResponse listPage2(Map<String, String> params, UserEntity user) {
        //分页
        long pageSize = Long.parseLong(params.get("limit"));
        long pageNum = Long.parseLong(params.get("page"));
        IPage<TTaskExecuteEntity> page = new Page<>(pageNum, pageSize);
        //组装查询条件
        IPageHelper<TTaskExecuteEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<TTaskExecuteEntity> queryWrapper = pageHelper.query(params);
        filterUserData(queryWrapper,user);
        queryWrapper.orderByDesc("create_time");
        page = this.baseMapper.selectPage(page, queryWrapper);
        List<TTaskExecuteEntity> records = page.getRecords();
        setExecuteShow2(records,user);
        Long total = this.baseMapper.selectCount(queryWrapper);
        //组装json数据
        JSONObject data = new JSONObject();
        data.put("items", records);
        data.put("total", total);
        return ApiResponse.success(data);
    }

    @Override
    public ApiResponse listPage(Map<String, String> params, UserEntity user) {
        //分页
        long pageSize = Long.parseLong(params.get("limit"));
        long pageNum = Long.parseLong(params.get("page"));
        IPage<TTaskEntity> page = new Page<>(pageNum, pageSize);
        //组装查询条件
        IPageHelper<TTaskEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<TTaskEntity> queryWrapper = pageHelper.query(params);
        filterUserData(queryWrapper,user);
        queryWrapper.orderByDesc("create_time");
        page = taskMapper.selectPage(page, queryWrapper);
        List<TTaskEntity> records = page.getRecords();
        setExecuteShow(records,user);
        Long total = taskMapper.selectCount(queryWrapper);
        //组装json数据
        JSONObject data = new JSONObject();
        data.put("items", records);
        data.put("total", total);
        return ApiResponse.success(data);
    }
    
    @Override
    public void updateLate() {
        this.baseMapper.updateLate();
    }

    /**
     * 增加 根据角色 所在部门 筛选相关数据
     * @param queryWrapper
     * @param user
     */
    public void filterUserData(QueryWrapper queryWrapper, UserEntity user) {
        List<RoleEntity> roleList = userRoleService.getRoles(user.getId());
        String roleString = "";
        for (RoleEntity roleEntity : roleList) {
            roleString += roleEntity.getRoleCode() + ",";
        }
        if(roleString.contains(ConstantUtil.ROLE_CITYADMIN) ||roleString.contains(ConstantUtil.ROLE_ADMIN)){
        }else if(roleString.contains(ConstantUtil.ROLE_QXDSBADMIN)){
            TDeptEntity deptEntity = deptMapper.selectById(user.getDeptId());
            queryWrapper.eq("area_id",deptEntity.getAreaId());
        }else{
            //责任部门
            queryWrapper.eq("dept",user.getDeptId());
        }
    }


    private void setExecuteShow2(List<TTaskExecuteEntity> records, UserEntity user){
        if(null!=user.getDeptId()){
            String dept = String.valueOf(user.getDeptId());
            for(TTaskExecuteEntity entity:records){
                entity.setExecuteShow(dept.equals(entity.getDept()));
            }
        }
    }

    private void setExecuteShow(List<TTaskEntity> records, UserEntity user){
        if(null!=user.getDeptId()){
            String dept = String.valueOf(user.getDeptId());
            String nowDate = DateUtil.getNowDate();
            for(TTaskEntity entity:records){
                entity.setExecuteShow(dept.equals(entity.getDept()) && nowDate.compareTo(entity.getEndDate())<=0);
            }
        }
    }
}
