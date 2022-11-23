package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.dto.TPointInfoDto;
import com.nteport.admin.entity.*;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.TDeptMapper;
import com.nteport.admin.service.TNoticeService;
import com.nteport.admin.mapper.TNoticeMapper;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IUserRoleService;
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
* @description 针对表【t_notice】的数据库操作Service实现
* @createDate 2022-09-16 15:38:03
*/
@Service
public class TNoticeServiceImpl extends ServiceImpl<TNoticeMapper, TNoticeEntity>
    implements TNoticeService {

    @Autowired
    private TNoticeMapper tNoticeMapper;

    @Override
    @Transactional
    public ApiResponse create(String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(jsonString == null || user == null){
            return response.fillMessage(EnumCode.BAD_REQUEST);
        }
        try {
            TNoticeEntity entity = JSON.parseObject(jsonString,TNoticeEntity.class);
            entity.createTimeStamp(user);
            tNoticeMapper.insert(entity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Transactional
    @Override
    public ApiResponse delete(Long id) {
        ApiResponse response = new ApiResponse();
        if(id == null){
            return response.fillMessage(EnumCode.BAD_REQUEST);
        }
        try {
            tNoticeMapper.deleteById(id);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse update(Long id, UserEntity user, String jsonString) {
        ApiResponse response = new ApiResponse();
        if(jsonString == null || user == null){
            return response.fillMessage(EnumCode.BAD_REQUEST);
        }
        try {
            TNoticeEntity entity = JSON.parseObject(jsonString,TNoticeEntity.class);
            entity.updateTimeStamp(user);
            tNoticeMapper.updateById(entity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse listPage(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();
        try {
            //分页
            long pageSize = Long.parseLong(params.get("limit"));
            long pageNum = Long.parseLong(params.get("page"));
            IPage<TNoticeEntity> userPage = new Page<>(pageNum, pageSize);
            //组装查询条件
            IPageHelper<TNoticeEntity> pageHelper = new PageHelperImpl<>();
            QueryWrapper<TNoticeEntity> queryWrapper = pageHelper.query(params);
            queryWrapper.orderByDesc("update_time");
            //查询结果
            userPage = tNoticeMapper.selectPage(userPage, queryWrapper);
            List<TNoticeEntity> list = userPage.getRecords();
            Long total = tNoticeMapper.selectCount(queryWrapper);
            //组装json数据
            data.put("total", total);
            data.put("data",list);
            return response.fillMessage(data);
        } catch (Exception e) {
            e.printStackTrace();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }
}




