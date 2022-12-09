package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.entity.system.OperationType;
import com.nteport.admin.service.system.IRoleService;
import com.nteport.admin.service.system.OpeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * 分页查询设备列表
     *
     * @param params
     * @return
     */
    @GetMapping("/listPage")
    public ApiResponse listPage(@RequestParam Map<String, String> params) {
        return roleService.getRolePageList(params);
    }

    /**
     * 获取全部Role
     *
     * @param params 条件//未实现
     * @return 角色列表 或 状态码
     */
    @GetMapping("/list")
    public ApiResponse list(@RequestParam Map params,UserEntity user) {
        return roleService.getRoleList(params,user);
    }

    /**
     * 查看日志详情
     *
     * @return
     */
    @GetMapping("/canUseWeek")
    public ApiResponse canUseWeek(UserEntity user) {
        return roleService.canUseWeek(user);
    }

    /**
     * 新增角色信息
     *
     * @param jsonString
     * @return
     */
    @PostMapping("/create")
    @OpeLog(opeModule = "角色管理-角色新增", opeType = OperationType.ADD, opeDesc = "角色新增")
    public ApiResponse create(@RequestBody String jsonString, @RequestHeader("authToken") String token) {
        return roleService.createRole(jsonString, token);
    }

    /**
     * 修改角色信息
     *
     * @param jsonString
     * @return
     */
    @PostMapping("/update/{id}")
    @OpeLog(opeModule = "角色管理-角色编辑", opeType = OperationType.MODIFY, opeDesc = "角色编辑")
    public ApiResponse update(@PathVariable(value = "id") String id, @RequestBody String jsonString, @RequestHeader("authToken") String token) {
        return roleService.updateRole(id, jsonString, token);
    }

    /**
     * 删除角色信息
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @OpeLog(opeModule = "角色管理-角色删除", opeType = OperationType.DELETE, opeDesc = "角色删除")
    public ApiResponse delete(@PathVariable(value = "id") String id) {
        return roleService.deleteRole(id);
    }

    /**
     * 当前角色对应的菜单项
     *
     * @param id
     * @return
     */
    @GetMapping("/listTreeSq/{id}")
    public ApiResponse listTreeSq(@PathVariable(value = "id") String id) {
        return roleService.listTreeSq(id);
    }

    /**
     * 角色菜单授权
     *
     * @param id
     * @param jsonString
     * @param token
     * @return
     */
    @PostMapping("/authorize/{id}")
    @OpeLog(opeModule = "角色管理-分配权限", opeType = OperationType.ADD, opeDesc = "角色授权")
    public ApiResponse authorize(@PathVariable(value = "id") String id, @RequestBody String jsonString, @RequestHeader("authToken") String token)
    throws  Exception {
        return roleService.authorize(id, jsonString, token);
    }

    @PostMapping("/verifyCode")
    public ApiResponse verifyCode(@RequestParam Map<String, String> params) {
        String roleCode = params.get("roleCode");
        String roleId = null;
        if (null != params.get("roleId")) {
            roleId = params.get("roleId");
        }
        return roleService.verifyCode(roleCode, roleId);
    }



}
