package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TDeptEntity;
import com.nteport.admin.entity.system.*;
import com.nteport.admin.mapper.RoleMapper;
import com.nteport.admin.mapper.TDeptMapper;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IRoleService;
import com.nteport.admin.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements IRoleService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private TDeptMapper deptMapper;


    /**
     * 获取角色列表，带分页
     *
     * @param params
     * @return
     */
    @Transactional
    @Override
    public ApiResponse getRolePageList(Map<String, String> params) {
        ApiResponse response = new ApiResponse();
        JSONObject data = new JSONObject();
        try {
            //分页
            long pageSize = Long.parseLong(params.get("limit"));
            long pageNum = Long.parseLong(params.get("page"));
            IPage<RoleEntity> rolePage = new Page<>(pageNum, pageSize);
            //组装查询条件
            IPageHelper<RoleEntity> pageHelper = new PageHelperImpl<>();
            QueryWrapper<RoleEntity> queryWrapper = pageHelper.query(params);
            queryWrapper.orderByDesc("create_time");
            //查询结果
            rolePage = roleMapper.selectPage(rolePage, queryWrapper);
            List<RoleEntity> list = rolePage.getRecords();
            Long total = roleMapper.selectCount(queryWrapper);
            //组装json数据
            data.put("items", list);
            data.put("total", total);
            response.fillMessage(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    /**
     * 获取全部Role
     *
     * @param params 条件//未实现
     * @return 角色列表 或 状态码
     */
    @Override
    public ApiResponse getRoleList(Map params,UserEntity user) {
        ApiResponse response = new ApiResponse();
        StringBuffer sb = new StringBuffer();
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        sb.append("[");
        List<RoleEntity> roles = roleMapper.selectList(queryWrapper);
        //区县打私办只能分配部分角色
        if(user.getRoleCodes().contains(ConstantUtil.ROLE_QXDSBADMIN)){
            for (RoleEntity role : roles) {
                if(ConstantUtil.ROLE_ADMIN.equals(role.getRoleCode()) ||ConstantUtil.ROLE_CITYADMIN.equals(role.getRoleCode())
                        ||ConstantUtil.ROLE_SJBMADMIN.equals(role.getRoleCode()) ||ConstantUtil.ROLE_CITYXUNFANG.equals(role.getRoleCode())){
                    continue;
                }
                sb.append("{");
                sb.append("\"id\":\"" + role.getId() + "\",");
                sb.append("\"title\":\"" + role.getRoleName() + "\"},");
            }
        }else{
            for (RoleEntity role : roles) {
                sb.append("{");
                sb.append("\"id\":\"" + role.getId() + "\",");
                sb.append("\"title\":\"" + role.getRoleName() + "\"},");
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        response.fillMessage(sb);
        return response;
    }

    /**
     * 创建角色
     *
     * @param jsonString
     * @param token
     * @return
     */
    @Transactional
    @Override
    public ApiResponse createRole(String jsonString, String token) {
        ApiResponse response = new ApiResponse();
        try {
            if (jsonString.equals("") || token.equals("")) {
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            } else {
                RoleEntity entity = JSON.parseObject(jsonString, RoleEntity.class);
                QueryWrapper<UserEntity> queryUser = new QueryWrapper<>();
                queryUser.eq("token", token);
                UserEntity user = userMapper.selectOne(queryUser);
                if (entity.getId() != null) {
                    response.fillMessage(EnumCode.ERROR_HANDLE);
                    return response;
                } else {
                    entity.createTimeStamp(user);
                    roleMapper.insert(entity);
                    response.fillMessage(EnumCode.SUCCESS);
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    /**
     * 修改角色
     *
     * @param jsonString
     * @param token
     * @return
     */
    @Transactional
    @Override
    public ApiResponse updateRole(String id, String jsonString, String token) {
        ApiResponse response = new ApiResponse();
        try {
            if (jsonString.equals("") || token.equals("")) {
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            } else {
                RoleEntity entity = JSON.parseObject(jsonString, RoleEntity.class);
                QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper();
                queryWrapper.eq("ID", id);
                RoleEntity exist = roleMapper.selectOne(queryWrapper);
                if (exist != null) {
                    QueryWrapper<UserEntity> queryUser = new QueryWrapper<>();
                    queryUser.eq("token", token);
                    UserEntity user = userMapper.selectOne(queryUser);
                    entity.updateTimeStamp(user);
                    roleMapper.update(entity, queryWrapper);
                    response.fillMessage(EnumCode.SUCCESS);
                    return response;
                } else {
                    response.fillMessage(EnumCode.ERROR_HANDLE);
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public ApiResponse deleteRole(String id) {
        ApiResponse response = new ApiResponse();
        try {
            if (null == id) {
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            } else {
                roleMapper.deleteById(id);
                response.fillMessage(EnumCode.SUCCESS);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse listTreeSq(String roleId) {
        ApiResponse response = new ApiResponse();
        return response.fillMessage(roleMapper.listTreeSq(roleId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResponse authorize(String roleId, String jsonString, String token) {
        ApiResponse response = new ApiResponse();
        try {
            QueryWrapper<UserEntity> queryUser = new QueryWrapper<>();
            queryUser.eq("token", token);
            UserEntity user = userMapper.selectOne(queryUser);
            //先删除 再插入
            roleMapper.deleteByRoleId(roleId);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            String menuIds = jsonObject.getString("menuIds");
            String[] menuArr = menuIds.split(",");
            IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();

            for (int i = 0; i < menuArr.length; i++) {
                RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                roleMenuEntity.setId(identifierGenerator.nextId(new Object()).toString());
                roleMenuEntity.setRoleId(roleId);
                roleMenuEntity.setMenuId(menuArr[i]);
                roleMenuEntity.setCreateUser(user.getId());
                roleMenuEntity.setCreateTime(LocalDateTime.now());
                roleMapper.insertSq(roleMenuEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
        return response.fillMessage(EnumCode.SUCCESS);
    }

    @Override
    public ApiResponse verifyCode(String roleCode, String roleId) {
        ApiResponse response = new ApiResponse();
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_code", roleCode);
        RoleEntity roleEntity = roleMapper.selectOne(queryWrapper);
        if (roleId == null && roleEntity != null) {
            return response.fillMessage("exists");
        }
        if (roleId != null && roleEntity != null && !roleId.equals(roleEntity.getId())) {
            return response.fillMessage("exists");
        }
        return response.fillMessage(EnumCode.SUCCESS);
    }

    /**
     * 根据ID获取角色
     *
     * @param user
     * @return
     */
    @Override
    public ApiResponse canUseWeek(UserEntity user) {
        ApiResponse response = new ApiResponse();
        if ("4".equals(user.getDeptId())) {
            return response.fillMessage("false");
        }
        TDeptEntity dept = deptMapper.selectById(user.getDeptId());
        if (user.getId().equals(dept.getLeaderId())) {
            return response.fillMessage("true");
        } else {
            return response.fillMessage("false");
        }
    }

}
