package com.nteport.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;


public interface ITokenService extends IService<UserEntity> {

    /**
     * 根据token或者openid获取用户信息
     */
    UserEntity getUserByColumnValue(String column, String value);
}
