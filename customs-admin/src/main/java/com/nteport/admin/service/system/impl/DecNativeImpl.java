package com.nteport.admin.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.DecFile;
import com.nteport.admin.entity.DecNative;
import com.nteport.admin.mapper.DecFileMapper;
import com.nteport.admin.mapper.DecNativeMapper;
import com.nteport.admin.service.IDecFileService;
import com.nteport.admin.service.IDecNativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理服务
 */
@Service
public class DecNativeImpl extends ServiceImpl<DecNativeMapper, DecNative> implements IDecNativeService {

    @Autowired
    private DecNativeMapper decNativeMapper;









}

