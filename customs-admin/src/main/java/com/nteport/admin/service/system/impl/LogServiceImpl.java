package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.LogEntity;
import com.nteport.admin.mapper.LogMapper;
import com.nteport.admin.service.system.ILogService;
import com.nteport.admin.service.system.IPageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 日志管理服务
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogEntity> implements ILogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 获取日志列表，带分页
     *
     * @param jsonString String
     *                   [page        当前页数,
     *                   limit       每页大小
     *                   opeModule   操作模块(可选)
     *                   opeDesc     操作类型，增删改(可选)
     *                   createUser  操作员名字(可选)]
     * @return Json [list 日志列表，total 结果总数]
     * 或 状态码
     */
    @Override
    public ApiResponse listPage(String jsonString) {
        if ("".equals(jsonString)) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        //分页
        JSONObject json = JSON.parseObject(jsonString);
        if (!json.containsKey("page") || !json.containsKey("limit")) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        // 组装分页配置
        long pageNum = Long.parseLong(json.getString("page"));
        long pageSize = Long.parseLong(json.getString("limit"));
        IPage<LogEntity> logPage = new Page<>(pageNum, pageSize);
        Map params = (Map) JSON.parse(jsonString);
        // 组装查询条件
        IPageHelper<LogEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<LogEntity> queryWrapper = pageHelper.query(params);
        queryWrapper.orderByDesc("create_time");
        // 查询结果
        logPage = logMapper.selectPage(logPage, queryWrapper);
        List<LogEntity> list = logPage.getRecords();
        Long total = logMapper.selectCount(queryWrapper);
        // 组装json数据
        JSONObject data = new JSONObject();
        data.put("items", list);
        data.put("total", total);
        return ApiResponse.success(data);
    }

}
