package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.QpDecHead;
import com.nteport.admin.entity.QpDecLog;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.QpDecHeadMapper;
import com.nteport.admin.mapper.QpDecLogMapper;
import com.nteport.admin.service.IQpDecHeadService;
import com.nteport.admin.service.IQpDecLogService;
import com.nteport.admin.service.system.IPageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户管理服务
 */
@Service
public class QpDecLogImpl extends ServiceImpl<QpDecLogMapper, QpDecLog> implements IQpDecLogService {

    @Autowired
    private QpDecLogMapper qpDecLogMapper;

    /**
     * 获取用户列表，带分页
     *
     * @param params Map[pageNum 当前页数, pageSize 每页大小]
     * @return Json [list 用户列表，total 结果总数]
     * 或 状态码
     */
    @Override
    public ApiResponse listPage(Map<String, String> params, UserEntity user) {
        if (!params.containsKey("page") || !params.containsKey("limit")) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        // 组装分页设置
        long pageNum = Long.parseLong(params.get("page"));
        long pageSize = Long.parseLong(params.get("limit"));
        IPage<QpDecLog> userPage = new Page<>(pageNum, pageSize);
//        Map params = json.getInnerMap();

        // 组装查询条件
        IPageHelper<QpDecLog> pageHelper = new PageHelperImpl<>();
        QueryWrapper<QpDecLog> queryWrapper = pageHelper.query(params);


        // 查询结果
        userPage = qpDecLogMapper.selectPage(userPage, queryWrapper);
        List<QpDecLog> list = userPage.getRecords();
        Long total = qpDecLogMapper.selectCount(queryWrapper);

        JSONObject data = new JSONObject();
        // 组装json数据
        data.put("items", list);
        data.put("total", total);
        return ApiResponse.success(data);
    }








}

