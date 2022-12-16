package com.nteport.admin.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.DecFile;
import com.nteport.admin.entity.QpDecCopLimit;
import com.nteport.admin.mapper.DecFileMapper;
import com.nteport.admin.mapper.QpDecCopLimitMapper;
import com.nteport.admin.service.IDecFileService;
import com.nteport.admin.service.IQpDecCopLimitService;
import com.nteport.admin.service.IQpDecFreeTxtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理服务
 */
@Service
public class DecFileImpl extends ServiceImpl<DecFileMapper, DecFile> implements IDecFileService {

    @Autowired
    private DecFileMapper decFileMapper;









}

