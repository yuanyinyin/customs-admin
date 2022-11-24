package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.OperationType;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TDeptService;
import com.nteport.admin.service.system.OpeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private TDeptService deptService;

    /**
     * 查询所有部分
     * 然后构建树形结构
     * @return
     */
    @GetMapping("/getDept")
    public ApiResponse getDept() {
        return deptService.getDeptTree();
    }

    @PostMapping("/delete/{id}")
    @OpeLog(opeModule = "部门管理-部门删除", opeType = OperationType.DELETE, opeDesc = "部门删除")
    public ApiResponse delete(@PathVariable(value = "id") String id) {
        return deptService.removeDept(id);
    }

    /**
     * 新增部门
     */
    @PostMapping("/add")
    @OpeLog(opeModule = "部门管理-部门新增",opeType = OperationType.ADD,opeDesc = "部门新增")
    public ApiResponse add(@RequestBody String jsonString, UserEntity user){
        return deptService.add(jsonString,user);
    }

    /**
     * 修改部门
     */
    @PostMapping("/update")
    @OpeLog(opeModule = "部门管理-部门修改",opeType = OperationType.MODIFY,opeDesc = "部门修改")
    public ApiResponse update(@RequestBody String jsonString,UserEntity user){
        return deptService.updateDept(jsonString,user);
    }

    /**
     * app
     * 查询除市打私办之外的部门树
     * @return
     */
    @GetMapping("/getDeptChildren")
    public ApiResponse getDeptChildren() {
        return deptService.getDeptTreeChildren();
    }

    /**
     * 根据部门获取人员
     * @return
     */
    @GetMapping("/getUserByDept/{id}")
    public ApiResponse getUserByDept(@PathVariable String id) {
        return deptService.getUserByDept(id);
    }

    /**
     * 根据人员获取部门信息
     */
    @GetMapping("/getDeptById/{id}")
    public ApiResponse getDeptById(@PathVariable String id){
        return deptService.getDeptById(id);
    }
}
