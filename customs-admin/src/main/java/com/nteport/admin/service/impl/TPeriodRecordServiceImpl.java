package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.dto.TPeriodRecordDto;
import com.nteport.admin.entity.*;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.*;
import com.nteport.admin.service.TPeriodRecordService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IUserRoleService;
import com.nteport.admin.service.system.impl.PageHelperImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_period_record(定期查)】的数据库操作Service实现
* @createDate 2022-07-27 15:46:13
*/
@Service
public class TPeriodRecordServiceImpl extends ServiceImpl<TPeriodRecordMapper, TPeriodRecordEntity>
    implements TPeriodRecordService{

    @Autowired
    private TPeriodRecordMapper tPeriodRecordMapper;
    @Autowired
    private TPeriodRecordDetailMapper tPeriodRecordDetailMapper;
    @Autowired
    private TPointInfoMapper tPointInfoMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private TRoleAreaMapper tRoleAreaMapper;
    @Autowired
    private TDailyRecordDetailMapper tDailyRecordDetailMapper;
    @Autowired
    private TDailyRecordMapper tDailyRecordMapper;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private TDeptMapper deptMapper;

    @Override
    @Transactional
    public ApiResponse create(String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        try{
            if(jsonString == null || user == null) {
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            }else {
                TPeriodRecordDto dto = JSON.parseObject(jsonString, TPeriodRecordDto.class);
                TPeriodRecordEntity entity = new TPeriodRecordEntity();
                BeanUtils.copyProperties(dto,entity);
                entity.createTimeStamp(user);
                tPeriodRecordMapper.insert(entity);
                Long headId = entity.getId();
                List<TPeriodRecordDetailEntity> list = dto.getDetailList();
                for (TPeriodRecordDetailEntity detail :
                        list) {
                    detail.createTimeStamp(user);
                    detail.setHeadId(headId);
                    detail.setPatrolDate(entity.getPatrolTime());
                    tPeriodRecordDetailMapper.insert(detail);
                }
                return ApiResponse.success();
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
        if(roleCodes.contains("admin") || roleCodes.contains("cityAdmin") || roleCodes.contains("sjbmAdmin")){
        }else if(roleCodes.contains("qxdsbAdmin") || roleCodes.contains("qxbmAdmin")){
            queryWrapper.eq("area_id",dept.getAreaId());
        }else if(roleCodes.contains("xzjdAdmin")){
            queryWrapper.eq("street_id",dept.getStreetId());
        }else{
            queryWrapper.eq("create_user",user.getId());
        }
        queryWrapper.eq("type","2");
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
}




