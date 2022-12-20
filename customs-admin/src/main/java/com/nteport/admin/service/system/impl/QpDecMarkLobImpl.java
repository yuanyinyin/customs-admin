package com.nteport.admin.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.QpDecMarkLob;
import com.nteport.admin.entity.QpDecOtherPack;
import com.nteport.admin.mapper.QpDecMarkLobMapper;
import com.nteport.admin.mapper.QpDecOtherPackMapper;
import com.nteport.admin.service.IQpDecMarkLobService;
import com.nteport.admin.service.IQpDecOtherPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理服务
 */
@Service
public class QpDecMarkLobImpl extends ServiceImpl<QpDecMarkLobMapper, QpDecMarkLob> implements IQpDecMarkLobService {

    @Autowired
    private QpDecMarkLobMapper qpDecMarkLobMapper;









}

