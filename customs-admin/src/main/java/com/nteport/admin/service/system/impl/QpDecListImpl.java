package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.QpDecLicenseDocu;
import com.nteport.admin.entity.QpDecList;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.QpDecLicenseDocuMapper;
import com.nteport.admin.mapper.QpDecListMapper;
import com.nteport.admin.service.IQpDecLicenseDocuService;
import com.nteport.admin.service.IQpDecListService;
import com.nteport.admin.service.system.IPageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户管理服务
 */
@Service
public class QpDecListImpl extends ServiceImpl<QpDecListMapper, QpDecList> implements IQpDecListService {

    @Autowired
    private QpDecListMapper qpDecListMapper;

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
        IPage<QpDecList> userPage = new Page<>(pageNum, pageSize);
//        Map params = json.getInnerMap();

        // 组装查询条件
        IPageHelper<QpDecList> pageHelper = new PageHelperImpl<>();
        QueryWrapper<QpDecList> queryWrapper = pageHelper.query(params);


        // 查询结果
        userPage = qpDecListMapper.selectPage(userPage, queryWrapper);
        List<QpDecList> list = userPage.getRecords();
        Long total = qpDecListMapper.selectCount(queryWrapper);

        JSONObject data = new JSONObject();
        // 组装json数据
        data.put("items", list);
        data.put("total", total);
        return ApiResponse.success(data);
    }








}

