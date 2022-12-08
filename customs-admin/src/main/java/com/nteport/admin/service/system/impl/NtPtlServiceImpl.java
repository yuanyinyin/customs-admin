package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.MenuEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.MenuMapper;
import com.nteport.admin.mapper.NtPtlMapper;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.system.IMenuService;
import com.nteport.admin.service.system.INtPtlService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NtPtlServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements INtPtlService {

    @Autowired
    private NtPtlMapper ntPtlMapper;

    /**
     * 查询用户
     * @return
     */
    @Transactional
    @Override
    public Map selectUser(String username, String password) {
        return ntPtlMapper.selectUser(username,password);
    }

    @Override
    public Map selectUserByToken(String token) {
        return ntPtlMapper.selectUserByToken(token);
    }

    @Override
    public Map selectDept(String depId) {
        return ntPtlMapper.selectDept(depId);
    }

    @Override
    public int updateTokenById(String id, String token) {
        return ntPtlMapper.updateTokenById(id,token);
    }

    @Override
    public ArrayList<String> queryRoleCodes(String userId){
        ArrayList<String> list = (ArrayList<String>) ntPtlMapper.queryRoleCodes(userId);
        return list;
    }

}
