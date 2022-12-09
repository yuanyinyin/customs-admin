package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.QpDecFreeTxt;
import com.nteport.admin.entity.QpDecUser;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.QpDecFreeTxtMapper;
import com.nteport.admin.mapper.QpDecUserMapper;
import com.nteport.admin.service.IQpDecFreeTxtService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IQpDecUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户管理服务
 */
@Service
public class QpDecUserImpl extends ServiceImpl<QpDecUserMapper, QpDecUser> implements IQpDecUserService  {

    @Autowired
    private QpDecUserMapper qpDecUserMapper;

    /**
     * 获取用户列表，带分页
     *
     * @param params Map[pageNum 当前页数, pageSize 每页大小]
     * @return Json [list 用户列表，total 结果总数]
     * 或 状态码
     */
    @Override
    public ApiResponse list(Map<String, String> params, UserEntity user) {
        if (!params.isEmpty()) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        QueryWrapper<QpDecUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("headid", params.get("params"));
        List<QpDecUser> list =  qpDecUserMapper.selectList(queryWrapper);

        JSONObject data = new JSONObject();
        // 组装json数据
        data.put("items", list);
        return ApiResponse.success(data);
    }








}

