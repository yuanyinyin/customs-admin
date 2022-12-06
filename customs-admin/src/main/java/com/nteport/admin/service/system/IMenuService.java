package com.nteport.admin.service.system;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.MenuEntity;

import java.util.Map;

public interface IMenuService extends IService<MenuEntity> {

    ApiResponse getMenuInfo(String token);

    ApiResponse createMenu(String jsonString, String token);

    ApiResponse updateMenu(String id, String jsonString, String token);

    ApiResponse deleteMenu(String id);

    JSONObject getMenuPageList(Map<String, String> params);

    String getMenuList(Map params);

    String getMenu(String pid);

}
