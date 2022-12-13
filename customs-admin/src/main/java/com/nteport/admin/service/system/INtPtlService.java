package com.nteport.admin.service.system;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.MenuEntity;
import com.nteport.admin.entity.system.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface INtPtlService extends IService<MenuEntity> {

    Map selectUser(String username, String password);
    Map selectUserByToken(String token);

    Map selectDept(String depId);

    int updateTokenById(String id, String token);

    ArrayList<String> queryRoleCodes(String userId);

    int registerDepUser(UserEntity user,String jsonString);
    List<Map> registerCheck(String column,String value);

    String sendYzm(String mobile, String ip, HttpServletRequest request);
}
