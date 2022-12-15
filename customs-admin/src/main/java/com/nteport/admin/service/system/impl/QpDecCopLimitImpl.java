package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.QpDecCopLimit;
import com.nteport.admin.entity.QpDecRequestCert;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.QpDecCopLimitMapper;
import com.nteport.admin.mapper.QpDecRequestCertMapper;
import com.nteport.admin.service.IQpDecCopLimitService;
import com.nteport.admin.service.system.IQpDecRequestCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户管理服务
 */
@Service
public class QpDecCopLimitImpl extends ServiceImpl<QpDecCopLimitMapper, QpDecCopLimit> implements IQpDecCopLimitService {

    @Autowired
    private QpDecCopLimitMapper qpDecCopLimitMapper;









}

