package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TDeptEntity;
import com.nteport.admin.entity.TRiskEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.TDeptMapper;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.FeignMessageService;
import com.nteport.admin.service.TRiskService;
import com.nteport.admin.mapper.TRiskMapper;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IUserRoleService;
import com.nteport.admin.service.system.impl.PageHelperImpl;
import com.nteport.admin.util.ConstantUtil;
import com.nteport.admin.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
* @author qianjun
* @description 针对表【t_risk(隐患表)】的数据库操作Service实现
* @createDate 2022-07-27 15:46:13
*/
@Service
public class TRiskServiceImpl extends ServiceImpl<TRiskMapper, TRiskEntity>
    implements TRiskService{

    @Autowired
    private TRiskMapper riskMapper;
    @Autowired
    private TDeptMapper deptMapper;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private FeignMessageService feignMessageService;
    @Value("${sms_emergency_code}")
    private String smsEmergencyCode;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResponse create(String jsonString, UserEntity user) {
        if ("".equals(jsonString) || user == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        try{
            TRiskEntity tRiskEntity = JSON.parseObject(jsonString, TRiskEntity.class);
            tRiskEntity.setReportTime(DateUtil.getNowTime());
            tRiskEntity.createTimeStamp(user);
            tRiskEntity.setCreateDept(user.getDeptId());
            TDeptEntity deptEntity = deptMapper.selectById(user.getDeptId());
            tRiskEntity.setAreaId(deptEntity.getAreaId());
            tRiskEntity.setArea(deptEntity.getArea());
            tRiskEntity.setStreetId(deptEntity.getStreetId());
            tRiskEntity.setStreet(deptEntity.getStreet());
            this.baseMapper.insert(tRiskEntity);
            send2Tel(tRiskEntity);
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
        TRiskEntity tRiskEntity = JSON.parseObject(jsonString, TRiskEntity.class);
        try {
//            TRiskEntity entity = this.baseMapper.selectById(id);
//            if (entity == null) {
//                return ApiResponse.fail(EnumCode.ERROR_HANDLE);
//            }
            this.baseMapper.updateById(tRiskEntity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     *  app按钮：市打私办 区县打私办
     *  数据权限：
     *  1.市打私办角色可以看到所有
     *  2.区县打私办角色只能看到自己部门和子部门发布的
     *  3.上报人所在部门、协助部门可以看到
     * @param params
     * @return
     */
    @Override
    public ApiResponse listPage(Map<String, String> params,UserEntity user) {
        //分页
        long pageSize = Long.parseLong(params.get("limit"));
        long pageNum = Long.parseLong(params.get("page"));
        IPage<TRiskEntity> page = new Page<>(pageNum, pageSize);
        //组装查询条件
        IPageHelper<TRiskEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<TRiskEntity> queryWrapper = pageHelper.query(params);
        boolean show = filterUserData(queryWrapper,user);
        queryWrapper.orderByDesc("create_time");
        page = this.baseMapper.selectPage(page, queryWrapper);
        List<TRiskEntity> records = page.getRecords();
        setButtonShow(records,show);
        Long total = this.baseMapper.selectCount(queryWrapper);
        //组装json数据
        JSONObject data = new JSONObject();
        data.put("items", records);
        data.put("total", total);
        return ApiResponse.success(data);
    }

    @Override
    public ApiResponse updateResult(String jsonString, UserEntity user) {
        if(jsonString == null || user == null ){
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        TRiskEntity tRiskEntity = JSON.parseObject(jsonString, TRiskEntity.class);
        try {
            tRiskEntity.setStatus("1");
            this.baseMapper.updateById(tRiskEntity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    @Override
    public ApiResponse statRisks() {
        ApiResponse response = new ApiResponse();
        try {
            List list = riskMapper.count();
            return response.fillMessage(list);
        } catch (Exception e) {
            e.printStackTrace();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse getHandleList(UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(user == null){
            return response.fillMessage(EnumCode.BAD_REQUEST);
        }
        //控制展示权限
        List<RoleEntity> roleList = userRoleService.getRoles(user.getId());
        List<String> roleCodes = new ArrayList<>();
        for (RoleEntity role :
                roleList) {
            roleCodes.add(role.getRoleCode());
        }
        if(roleCodes.size() <= 0){
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
        QueryWrapper<TRiskEntity> queryWrapper = new QueryWrapper<>();
        QueryWrapper<TDeptEntity> deptWrapper = new QueryWrapper<>();
        deptWrapper.eq("id",user.getDeptId());
        TDeptEntity dept = deptMapper.selectOne(deptWrapper);
        if(dept == null){
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
        if(roleCodes.contains("admin") || roleCodes.contains("cityAdmin") || roleCodes.contains("sjbmAdmin") || roleCodes.contains("cityXunfang")){

        }else if(roleCodes.contains("qxdsbAdmin") || roleCodes.contains("qxbmAdmin") || roleCodes.contains("qxbmXunfang")){
            queryWrapper.eq("area_id",dept.getAreaId());
        }else{
            queryWrapper.eq("street_id",dept.getStreetId());
        }
        queryWrapper.orderByDesc("update_time");
        queryWrapper.last("limit 5");
        List<TRiskEntity> list = riskMapper.selectList(queryWrapper);
        return response.fillMessage(list);
    }

    @Override
    public ApiResponse queryRiskCount(String year) {
        return ApiResponse.success(riskMapper.queryRiskCount(year));
    }

    @Override
    public ApiResponse queryRiskRatio(String year) {
        return ApiResponse.success(riskMapper.queryRiskRatio(year));
    }

    /**
     * 增加 根据角色 所在部门 筛选相关数据
     * @param queryWrapper
     * @param user
     */
    public boolean filterUserData(QueryWrapper queryWrapper, UserEntity user) {
        List<RoleEntity> roleList = userRoleService.getRoles(user.getId());
        String roleString = "";
        for (RoleEntity roleEntity : roleList) {
            roleString += roleEntity.getRoleCode() + ",";
        }
        if(roleString.contains(ConstantUtil.ROLE_CITYADMIN) || roleString.contains(ConstantUtil.ROLE_ADMIN)){
            return true;
        }else if(roleString.contains(ConstantUtil.ROLE_QXDSBADMIN)){
            TDeptEntity dept = deptMapper.selectById(user.getDeptId());
            queryWrapper.eq("area_id",dept.getAreaId());
            return true;
        }else{
//            TDeptEntity deptEntity = deptMapper.selectById(user.getDeptId());
            //上报人部门都能看到，协助部门也能看到
            Consumer<QueryWrapper> consumer = qw -> {
                qw.eq("create_dept", user.getDeptId());
            };
            queryWrapper.and(consumer);
            return false;
        }
    }

    private void setButtonShow(List<TRiskEntity> records,boolean show){
        for(TRiskEntity entity:records){
            entity.setButtonShow(show);
        }
    }

    /**
     * 短信通知到 区县打私办管理员角色
     * @param tRiskEntity
     */
    public void send2Tel(TRiskEntity tRiskEntity){
        if(null!=tRiskEntity.getAreaId()){
            List<UserEntity> userEntities = userMapper.selectAdminByArea(tRiskEntity.getAreaId());
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
        mapMes.put("source", smsEmergencyCode);
        feignMessageService.sendMessage(JSON.toJSONString(mapMes));
    }
}




