package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.QpDecLog;
import com.nteport.admin.entity.QpDecOtherPack;
import com.nteport.admin.entity.QpDecResult;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.QpDecOtherPackMapper;
import com.nteport.admin.mapper.QpDecResultMapper;
import com.nteport.admin.service.IQpDecOtherPackService;
import com.nteport.admin.service.IQpDecResultService;
import com.nteport.admin.service.system.IPageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户管理服务
 */
@Service
public class QpDecResultImpl extends ServiceImpl<QpDecResultMapper, QpDecResult> implements IQpDecResultService {

    @Autowired
    private QpDecResultMapper qpDecResultMapper;



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
        IPage<QpDecResult> userPage = new Page<>(pageNum, pageSize);
//        Map params = json.getInnerMap();

        // 组装查询条件
        IPageHelper<QpDecResult> pageHelper = new PageHelperImpl<>();
        QueryWrapper<QpDecResult> queryWrapper = pageHelper.queryEq(params);

        queryWrapper.eq("orgid", user.getDeptId());


        queryWrapper.orderByDesc("NOTICE_DATE");

        // 查询结果
        userPage = qpDecResultMapper.selectPage(userPage, queryWrapper);
        List<QpDecResult> list = userPage.getRecords();
        Long total = qpDecResultMapper.selectCount(queryWrapper);

        JSONObject data = new JSONObject();
        // 组装json数据
        data.put("items", list);
        data.put("total", total);
        return ApiResponse.success(data);
    }






}

