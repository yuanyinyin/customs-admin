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
import com.nteport.admin.entity.TTaskPersonEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.*;
import com.nteport.admin.service.FeignMessageService;
import com.nteport.admin.service.TTaskService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IUserRoleService;
import com.nteport.admin.service.system.impl.PageHelperImpl;
import com.nteport.admin.util.ConstantUtil;
import com.nteport.admin.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_task(巡查指令表)】的数据库操作Service实现
* @createDate 2022-07-27 15:46:13
*/
@Service
public class TTaskServiceImpl extends ServiceImpl<TTaskMapper, TTaskEntity>
    implements TTaskService{

    @Autowired
    private TTaskPersonMapper taskPersonMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TTaskExecuteMapper taskExecuteMapper;

    @Autowired
    private FeignMessageService feignMessageService;

    @Value("${sms_task_code}")
    private String smsTaskCode;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private TDeptMapper deptMapper;

    @Override
    @Transactional
    public ApiResponse create(String jsonString, UserEntity user) {
        if ("".equals(jsonString) || user == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        try{
            TTaskEntity tTideEntity = JSON.parseObject(jsonString, TTaskEntity.class);
            tTideEntity.setDeclareDate(DateUtil.getNowTime());
            tTideEntity.createTimeStamp(user);
            tTideEntity.setCreateDept(user.getDeptId());
            TDeptEntity deptEntity = deptMapper.selectById(user.getDeptId());
            tTideEntity.setAreaId(deptEntity.getAreaId());
            tTideEntity.setArea(deptEntity.getArea());
            tTideEntity.setStreetId(deptEntity.getStreetId());
            tTideEntity.setStreet(deptEntity.getStreet());
            this.baseMapper.insert(tTideEntity);
            //判断发布指令时，是否是今天开始
//            createTodayExecute(tTideEntity);
//            createItem(tTideEntitgetUserListy);
            send2Tel(tTideEntity);
            return ApiResponse.success();
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 判断发布指令时，是否是今天开始
     * @param tTideEntity
     */
    private void createTodayExecute(TTaskEntity tTideEntity){
        if(DateUtil.getNowDate().equals(tTideEntity.getStartDate())){
            TTaskExecuteEntity taskExecute = new TTaskExecuteEntity();
            BeanUtils.copyProperties(tTideEntity,taskExecute);
            taskExecute.setId(null);
            taskExecute.setTaskId(tTideEntity.getId());
            taskExecute.setExecuted(ConstantUtil.TASK_EXECUTED_TODO);
            taskExecute.setCreateTime(LocalDateTime.now());
            taskExecute.setCreateUser(null);
            taskExecute.setUpdateTime(null);
            taskExecute.setUpdateUser(null);
            taskExecute.setCreatorName(null);
            taskExecuteMapper.insert(taskExecute);
        }
    }

    public void createItem(TTaskEntity tTideEntity){
        for(Long personId : tTideEntity.getPersonList()){
            TTaskPersonEntity entity = new TTaskPersonEntity();
            entity.setHeadId(tTideEntity.getId());
            entity.setPersonId(personId);
            entity.setStatus("1");
            taskPersonMapper.insert(entity);
        }
    }

    /**
     * 指令 短信通知到责任部门的管理员 市级部门管理员/区县部门管理员/乡镇街道管理员
     * @param tTideEntity
     */
    public void send2Tel(TTaskEntity tTideEntity){
        if(null!=tTideEntity.getDept() && !"".equals(tTideEntity.getDept())){
            List<UserEntity> userEntities = userMapper.selectManagerByDept(Long.valueOf(tTideEntity.getDept()));
            for(UserEntity userEntity:userEntities){
                sendMsg(userEntity.getTelephone());
            }
        }
    }

    public void sendMsg(String telephone){
        //组装发送数据
        Map colomnMap = new HashMap();
        colomnMap.put("contactTel", telephone);
        //组装短信接口字段
        Map mapMes = new HashMap();
        mapMes.put("messageContent", colomnMap);
        mapMes.put("sendMobile", telephone);
        mapMes.put("source", smsTaskCode);
        feignMessageService.sendMessage(JSON.toJSONString(mapMes));
    }

    @Override
    public ApiResponse updateInfo(Long id, String jsonString, UserEntity user) {
        if(jsonString == null || user == null || null==id){
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        TTaskEntity tTideEntity = JSON.parseObject(jsonString, TTaskEntity.class);
        try {
//            TTaskEntity entity = this.baseMapper.selectById(id);
//            if (entity == null) {
//                return ApiResponse.fail(EnumCode.ERROR_HANDLE);
//            }
            this.baseMapper.updateById(tTideEntity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 菜单就市打私办和各地打私办可以看到
     * 指令发布数据权限：
     * 1.市打私办角色可以看到所有
     * 2.区县打私办角色只能看到自己部门发布的
     * @param params
     * @param user
     * @return
     */
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
        page = this.baseMapper.selectPage(page, queryWrapper);
        List<TTaskEntity> records = page.getRecords();
        Long total = this.baseMapper.selectCount(queryWrapper);
        //组装json数据
        JSONObject data = new JSONObject();
        data.put("items", records);
        data.put("total", total);
        return ApiResponse.success(data);
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
        if(roleString.contains(ConstantUtil.ROLE_CITYADMIN) || roleString.contains(ConstantUtil.ROLE_ADMIN)){
        }else if(roleString.contains(ConstantUtil.ROLE_QXDSBADMIN)){
            TDeptEntity deptEntity = deptMapper.selectById(user.getDeptId());
            queryWrapper.eq("area_id",deptEntity.getAreaId());
        }
    }
}




