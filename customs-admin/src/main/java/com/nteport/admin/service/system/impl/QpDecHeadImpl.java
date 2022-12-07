package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.dto.QpDecHeadDto;
import com.nteport.admin.entity.*;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.QpDecHeadMapper;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.*;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IUserRoleService;
import com.nteport.admin.service.system.IUserService;
import com.nteport.admin.util.ConstantUtil;
import com.nteport.admin.util.MD5Util;
import com.nteport.admin.util.RedisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户管理服务
 */
@Service
public class QpDecHeadImpl extends ServiceImpl<QpDecHeadMapper, QpDecHead> implements IQpDecHeadService {

    @Autowired
    private QpDecHeadMapper qpDecHeadMapper;
    @Autowired
    private IQpDecListService  qpDecListService;

    @Autowired
    private IQpDecLicenseDocuService qpDecLicenseDocuService;

    @Autowired
    private IQpDecFreeTxtService qpDecFreeTxtService;
    @Autowired
    private IQpDecContainerService qpDecContainerService;


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
        IPage<QpDecHead> userPage = new Page<>(pageNum, pageSize);
//        Map params = json.getInnerMap();

        // 组装查询条件
        IPageHelper<QpDecHead> pageHelper = new PageHelperImpl<>();

        String startTime =  params.get("startTime");
        params.remove("startTime");
        String endTime =  params.get("endTime");
        params.remove("endTime");

        QueryWrapper<QpDecHead> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            queryWrapper.ge("declarationdata",  simpleDateFormat.format(new Date(Timestamp.valueOf(startTime).getTime())));
            queryWrapper.le("declarationdata",  simpleDateFormat.format(new Date(Timestamp.valueOf(endTime).getTime())));
        }
        String ieFlag =  params.get("ieFlag");
        params.remove("ieFlag");
        if(StringUtils.isNotBlank(ieFlag)){
            queryWrapper.eq("ieflag", ieFlag);
        }

        String entryId =  params.get("entryId");
        params.remove("entryId");
        if(StringUtils.isNotBlank(entryId)){
            queryWrapper.like("entryid", entryId);
        }


//        String startTime = (String) params.get("startTime");
//        String endTime = (String) params.get("endTime");
//        params.remove("startTime");
//        params.remove("endTime");


//        queryWrapper.apply("CREATETIME = TO_TIMESTAMP({0}, 'YYYY-MM-DD HH24:MI:SS:FF6')", createtime);
//        if(StringUtils.isNotBlank(createtime)){
//            queryWrapper.eq("createtime", Timestamp.valueOf(createtime));
//        }

//        queryWrapper.e
        //根据登录人员区县过滤
//        if(user.getRoleCodes().contains(ConstantUtil.ROLE_ADMIN) ||user.getRoleCodes().contains(ConstantUtil.ROLE_CITYADMIN)){
//        }else if(user.getRoleCodes().contains(ConstantUtil.ROLE_QXDSBADMIN)){
//            List<String> deptIds = deptService.queryAreaDept(user.getDeptId());
//            queryWrapper.in("dept_id",deptIds);
//        }

//        queryWrapper.orderByAsc("dept_id");

        // 查询结果

        userPage = qpDecHeadMapper.selectPage(userPage, queryWrapper);
        List<QpDecHead> list = userPage.getRecords();
        Long total = qpDecHeadMapper.selectCount(queryWrapper);

        JSONObject data = new JSONObject();
        // 组装json数据
        data.put("items", list);
        data.put("total", total);
        return ApiResponse.success(data);
    }

    @Override
    public ApiResponse showOne(String headId, UserEntity user) {
        if(StringUtils.isBlank(headId)) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        QpDecHead qpDecHead =   qpDecHeadMapper.selectById(headId);
        QpDecHeadDto qpDecHeadDto = new QpDecHeadDto();
        BeanUtils.copyProperties(qpDecHead, qpDecHeadDto);
        //--报关单商品表
        QueryWrapper<QpDecList> decListQueryWrapper = new QueryWrapper<>();
        decListQueryWrapper.eq("headid", headId);
        List<QpDecList>  qpDecLists =  qpDecListService.list(decListQueryWrapper);
        if(!CollectionUtils.isEmpty(qpDecLists)){
            qpDecHeadDto.setQpDecLists(qpDecLists);
        }

//        --报关单集装箱表
        QueryWrapper<QpDecContainer> qpDecContainerQueryWrapper = new QueryWrapper<>();
        qpDecContainerQueryWrapper.eq("headid", headId);
        List<QpDecContainer>  qpDecContainerList =  qpDecContainerService.list(qpDecContainerQueryWrapper);
        if(!CollectionUtils.isEmpty(qpDecContainerList)) {
            qpDecHeadDto.setQpDecContainerList(qpDecContainerList);
        }

//        --随附单据
        QueryWrapper<QpDecLicenseDocu> qpDecLicenseDocuQueryWrapper = new QueryWrapper<>();
        qpDecLicenseDocuQueryWrapper.eq("headid", headId);
        List<QpDecLicenseDocu>  qpDecLicenseDocuList =  qpDecLicenseDocuService.list(qpDecLicenseDocuQueryWrapper);
        if(!CollectionUtils.isEmpty(qpDecLicenseDocuList)) {
            qpDecHeadDto.setQpDecLicenseDocuList(qpDecLicenseDocuList);
        }

//        --自由文本信息  管联报关单
        QueryWrapper<QpDecFreeTxt> qpDecFreeTxtQueryWrapper = new QueryWrapper<>();
        qpDecFreeTxtQueryWrapper.eq("headid", headId);
        QpDecFreeTxt  qpDecFreeTxt =  qpDecFreeTxtService.getOne(qpDecFreeTxtQueryWrapper);
        if(!ObjectUtils.isEmpty(qpDecFreeTxt)) {
            qpDecHeadDto.setQpDecFreeTxt(qpDecFreeTxt);
        }

        return ApiResponse.success(qpDecHeadDto);
    }
}

