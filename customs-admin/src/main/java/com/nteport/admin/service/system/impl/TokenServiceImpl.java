package com.nteport.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.system.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 用户管理服务
 */
@Service()
@Primary
public class TokenServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements ITokenService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity getUserByColumnValue(String column, String value) {
        QueryWrapper<UserEntity> query = new QueryWrapper<>();
        query.eq(column, value);
        UserEntity userEntity = userMapper.selectOne(query);
        userEntity.setRoleCodes((ArrayList<String>) userMapper.queryRoleCodes(userEntity.getId()));
        return userEntity;
    }

}

