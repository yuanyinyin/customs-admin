package com.nteport.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.OperationType;
import com.nteport.admin.service.system.IMenuService;
import com.nteport.admin.service.system.OpeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    /**
     * 分页查询菜单列表
     *
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    public String listPage(@RequestParam Map<String, String> params) {
        JSONObject object = menuService.getMenuPageList(params);
        return JSONObject.toJSONString(object);
    }

    /**
     * 获取全部菜单
     *
     * @return
     */
    @RequestMapping("/getMenuInfo")
    public ApiResponse getMenuInfo() {
        return menuService.getMenuInfo("");
    }

    /**
     * 获取全部菜单
     *
     * @return
     */
    @RequestMapping("/list")
    public ApiResponse list(@RequestParam Map<String, String> params) {
        ApiResponse response = new ApiResponse();
        return response.fillMessage(menuService.getMenuList(params));
    }

    /**
     * 新增菜单信息
     *
     * @param jsonString
     * @return
     */
    @PostMapping("/create")
    @OpeLog(opeModule = "菜单管理-菜单新增", opeType = OperationType.ADD, opeDesc = "菜单新增")
    public ApiResponse create(@RequestBody String jsonString, @RequestHeader("authToken") String token) {
        return menuService.createMenu(jsonString, token);
    }

    /**
     * 修改菜单信息
     *
     * @param jsonString
     * @return
     */
    @PostMapping("/update/{id}")
    @OpeLog(opeModule = "菜单管理-菜单编辑", opeType = OperationType.MODIFY, opeDesc = "菜单编辑")
    public ApiResponse update(@PathVariable(value = "id") Long id, @RequestBody String jsonString, @RequestHeader("authToken") String token) {
        return menuService.updateMenu(id, jsonString, token);
    }

    /**
     * 删除菜单信息
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @OpeLog(opeModule = "菜单管理-菜单删除", opeType = OperationType.DELETE, opeDesc = "菜单删除")
    public ApiResponse delete(@PathVariable(value = "id") Long id) {
        return menuService.deleteMenu(id);
    }
}
