package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.dto.TTrendsRecordDto;
import com.nteport.admin.entity.*;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.*;
import com.nteport.admin.service.TTrendsRecordService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.impl.PageHelperImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_trends_record(动态督)】的数据库操作Service实现
* @createDate 2022-07-27 15:46:13
*/
@Service
public class TTrendsRecordServiceImpl extends ServiceImpl<TTrendsRecordMapper, TTrendsRecordEntity>
    implements TTrendsRecordService{

    @Autowired
    private TTrendsRecordMapper tTrendsRecordMapper;
    @Autowired
    private TTrendsRecordDetailMapper tTrendsRecordDetailMapper;
    @Autowired
    private TPointInfoMapper tPointInfoMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private TRoleAreaMapper tRoleAreaMapper;

    @Override
    @Transactional
    public ApiResponse create(String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        try{
            if(jsonString == null || user == null) {
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            }else {
                TTrendsRecordDto dto = JSON.parseObject(jsonString, TTrendsRecordDto.class);
                TTrendsRecordEntity entity = new TTrendsRecordEntity();
                BeanUtils.copyProperties(dto,entity);
                entity.createTimeStamp(user);
                tTrendsRecordMapper.insert(entity);
                Long headId = entity.getId();
                List<TTrendsRecordDetailEntity> list = dto.getDetailList();
                for (TTrendsRecordDetailEntity detail :
                        list) {
                    detail.createTimeStamp(user);
                    detail.setHeadId(headId);
                    detail.setPatrolDate(entity.getPatrolTime());
                    tTrendsRecordDetailMapper.insert(detail);
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
        IPage<TTrendsRecordEntity> periodPage = new Page<>(pageNum, pageSize);
        Map params = (Map) JSON.parse(jsonString);
        List<Long> headIds = new ArrayList<>();
        // 展示权限控制
        Long userId = user.getId();
        List<Long> roleIds = userRoleMapper.getRoleIdsByUserId(userId);
        List<String> areaList = new ArrayList<>();
        int isTopAdmin = 0;
        for (Long id :
                roleIds) {
            if(id == 1L || id == 2L){
                isTopAdmin ++;
                break;
            }
            QueryWrapper<TRoleAreaEntity> areaWrapper = new QueryWrapper<>();
            areaWrapper.eq("role_id",id);
            try {
                TRoleAreaEntity entity = tRoleAreaMapper.selectOne(areaWrapper);
                if(entity != null){
                    areaList.add(entity.getArea());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 不是顶级管理员，则限制点位
        if (isTopAdmin <= 0) {
            QueryWrapper<TPointInfoEntity> pointWrapper = new QueryWrapper<>();
            pointWrapper.in("area", areaList);
            if(areaList.size() <= 0){
                return ApiResponse.fail("您不是地区管理员");
            }
            List<TPointInfoEntity> pointList = tPointInfoMapper.selectList(pointWrapper);
            if(pointList.size() <= 0){
                return ApiResponse.fail("您管理的地区不存在点位");
            }
            List<Long> pointIds = new ArrayList<>();
            for (TPointInfoEntity point :
                    pointList) {
                pointIds.add(point.getId());
            }
            QueryWrapper<TTrendsRecordDetailEntity> detailWrapper = new QueryWrapper<>();
            detailWrapper.select("head_id");
            detailWrapper.in("point_id",pointIds);
            detailWrapper.groupBy("head_id");
            // 查询包含查询点位的所有headId
            List<TTrendsRecordDetailEntity> detailList = tTrendsRecordDetailMapper.selectList(detailWrapper);
            for (TTrendsRecordDetailEntity detail :
                    detailList) {
                headIds.add(detail.getHeadId());
            }
        }
        // 处理前端el-date-picker当选择值清空时，会返回一个null的参数，且会传到后端影响查询参数
        if(params.get("patrolTime") == null){
            params.remove("patrolTime");
        }
        if(params.get("pointName") == ""){
            params.remove("pointName");
        }
        // 由于主表不存点位信息，需要设置查询参数
        String pointName = null;
        if(params.get("pointName") != null) {
            // 获取点位名称查点位id
            pointName = params.get("pointName").toString();
            QueryWrapper<TPointInfoEntity> pointWrapper = new QueryWrapper<>();
            pointWrapper.like("name", pointName);
            List<TPointInfoEntity> pointList = tPointInfoMapper.selectList(pointWrapper);
            List<Long> pointIds = new ArrayList<>();
            if(pointList.size() <= 0){
                return ApiResponse.fail("您的管辖区域不存在该点位");
            }
            if(isTopAdmin <= 0) {
                for (TPointInfoEntity point :
                        pointList) {
                    if (areaList.contains(point.getArea())) {
                        pointIds.add(point.getId());
                    }
                }
                if (pointIds.size() <= 0) {
                    return ApiResponse.fail("您的管辖区域不存在该点位");
                }
            }else{
                for (TPointInfoEntity point :
                        pointList) {
                    pointIds.add(point.getId());
                }
            }
            // 重新设置按点位名称查询的参数
            params.remove("pointName");
            QueryWrapper<TTrendsRecordDetailEntity> detailWrapper = new QueryWrapper<>();
            detailWrapper.select("head_id");
            detailWrapper.in("point_id", pointIds);
            detailWrapper.groupBy("head_id");
            // 查询包含查询点位的所有headId
            List<TTrendsRecordDetailEntity> detailList = tTrendsRecordDetailMapper.selectList(detailWrapper);
            headIds = new ArrayList<>();
            for (TTrendsRecordDetailEntity detail :
                    detailList) {
                headIds.add(detail.getHeadId());
            }
        }
        // 组装查询条件
        IPageHelper<TTrendsRecordEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<TTrendsRecordEntity> queryWrapper = pageHelper.query(params);
        if(pointName != null && headIds.size() <= 0){
            return ApiResponse.fail("没有该点位的巡查记录");
        }
        if(headIds.size() > 0){
            queryWrapper.in("id",headIds);
        }
        queryWrapper.orderByDesc("create_time");
        periodPage = tTrendsRecordMapper.selectPage(periodPage,queryWrapper);
        List<TTrendsRecordEntity> list = periodPage.getRecords();
        Long total = tTrendsRecordMapper.selectCount(queryWrapper);
        // 组装json数据
        JSONObject data = new JSONObject();
        data.put("items", list);
        data.put("total", total);
        return ApiResponse.success(data);
    }
}




