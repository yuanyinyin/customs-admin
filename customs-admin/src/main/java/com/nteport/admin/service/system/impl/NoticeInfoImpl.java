package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.NoticeInfo;
import com.nteport.admin.entity.QpDecLog;
import com.nteport.admin.entity.QpDecUser;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.NoticeInfoMapper;
import com.nteport.admin.mapper.QpDecUserMapper;
import com.nteport.admin.service.system.INoticeInfoService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IQpDecUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户管理服务
 */
@Service
public class NoticeInfoImpl extends ServiceImpl<NoticeInfoMapper, NoticeInfo> implements INoticeInfoService {

    @Autowired
    private NoticeInfoMapper noticeInfoMapper;

    /**
     * 获取用户列表，带分页
     *
     * @param params Map[pageNum 当前页数, pageSize 每页大小]
     * @return Json [list 用户列表，total 结果总数]
     * 或 状态码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse listPage(Map<String, String> params, UserEntity user) {
        if (!params.containsKey("page") || !params.containsKey("limit")) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        // 组装分页设置
        long pageNum = Long.parseLong(params.get("page"));
        long pageSize = Long.parseLong(params.get("limit"));
        IPage<NoticeInfo> userPage = new Page<>(pageNum, pageSize);

        // 组装查询条件
        IPageHelper<NoticeInfo> pageHelper = new PageHelperImpl<>();
        QueryWrapper<NoticeInfo> queryWrapper = pageHelper.query(params);
        System.out.println("***********" + user.getId());
        queryWrapper.eq("NOTICE_USER_ID", user.getId());
        // 查询结果
        userPage = noticeInfoMapper.selectPage(userPage, queryWrapper);
        List<NoticeInfo> list = userPage.getRecords();
        Long total = noticeInfoMapper.selectCount(queryWrapper);

        JSONObject data = new JSONObject();
        // 组装json数据
        data.put("items", list);
        data.put("total", total);
        //更新为已推送
        if(!CollectionUtils.isEmpty(list)){
            List<String>  idList = list.stream().map(NoticeInfo::getId).collect(Collectors.toList());
            System.out.println(idList.toString());
            noticeInfoMapper.updateByIds(idList);
        }

        return ApiResponse.success(data);
    }








}

