package com.nteport.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.NtPtlMapper;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.system.ITokenService;
import com.nteport.admin.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * 用户管理服务
 */
@Service()
@Primary
public class TokenServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements ITokenService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NtPtlMapper ntPtlMapper;

    @Override
    public UserEntity getUserByColumnValue(String column, String value) {
        QueryWrapper<UserEntity> query = new QueryWrapper<>();
        query.eq(column, value);
        UserEntity userEntity = userMapper.selectOne(query);
        if (userEntity!=null) {
            userEntity.setRoleCodes((ArrayList<String>) userMapper.queryRoleCodes(userEntity.getId()));
        }
        /*add by panh for 登录验证从核心库读取  包含菜单权限*/
        if(LoginUtil.useNtPtlLogin){
            Map ntPtl_userinfo=ntPtlMapper.selectUserByToken(value);
            userEntity =LoginUtil.ptlUser2UserEntity(ntPtl_userinfo);
            userEntity.setRoleCodes(ntPtlMapper.queryRoleCodes(userEntity.getId()));
        }
        /*end*/

        return userEntity;
    }

}

