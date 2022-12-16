package com.nteport.admin.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.DecNative;
import com.nteport.admin.entity.DecReceiptLog;
import com.nteport.admin.mapper.DecNativeMapper;
import com.nteport.admin.mapper.DecReceiptLogMapper;
import com.nteport.admin.service.IDecNativeService;
import com.nteport.admin.service.IDecReceiptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理服务
 */
@Service
public class DecReceiptLogImpl extends ServiceImpl<DecReceiptLogMapper, DecReceiptLog> implements IDecReceiptLogService {

    @Autowired
    private DecReceiptLogMapper decReceiptLogMapper;









}

