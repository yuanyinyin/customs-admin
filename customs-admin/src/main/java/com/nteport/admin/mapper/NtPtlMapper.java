package com.nteport.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nteport.admin.entity.system.MenuEntity;
import com.nteport.admin.entity.system.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单接口
 */
@Repository
public interface NtPtlMapper extends BaseMapper<MenuEntity> {

    Map selectUser(String username, String password);
    Map selectUserByToken(String token);

    Map selectDept(String depId);

    int updateTokenById(String id, String token);

    ArrayList<String> queryRoleCodes(String userId);

    List getMenuByUser(String userId);

    int updateUser(UserEntity user);
}
