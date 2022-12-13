package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TDeptEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.NtPtlMapper;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.FeignMessageService;
import com.nteport.admin.service.TDeptService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IUserRoleService;
import com.nteport.admin.service.system.IUserService;
import com.nteport.admin.util.ConstantUtil;
import com.nteport.admin.util.LoginUtil;
import com.nteport.admin.util.MD5Util;
import com.nteport.admin.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.*;

/**
 * 用户管理服务
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private FeignMessageService feignMessageService;

    @Value("${sms_approve_code}")
    private String smsApproveCode;

    @Autowired
    private TDeptService deptService;

    @Autowired
    private NtPtlMapper ntPtlMapper;

    /**
     * 获取用户列表，带分页
     *
     * @param jsonString Map[pageNum 当前页数, pageSize 每页大小]
     * @return Json [list 用户列表，total 结果总数]
     * 或 状态码
     */
    @Override
    public ApiResponse listPage(String jsonString, UserEntity user) {
        JSONObject json = JSON.parseObject(jsonString);
        if (!json.containsKey("page") || !json.containsKey("limit")) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        // 组装分页设置
        long pageNum = Long.parseLong(json.getString("page"));
        long pageSize = Long.parseLong(json.getString("limit"));
        IPage<UserEntity> userPage = new Page<>(pageNum, pageSize);
        Map params = json.getInnerMap();

        // 组装查询条件
        IPageHelper<UserEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<UserEntity> queryWrapper = pageHelper.query(params);

        //根据登录人员区县过滤
        if(user.getRoleCodes().contains(ConstantUtil.ROLE_ADMIN) ||user.getRoleCodes().contains(ConstantUtil.ROLE_CITYADMIN)){
        }else if(user.getRoleCodes().contains(ConstantUtil.ROLE_QXDSBADMIN)){
            List<String> deptIds = deptService.queryAreaDept(user.getDeptId());
            queryWrapper.in("dept_id",deptIds);
        }

        queryWrapper.orderByAsc("dept_id");

        // 查询结果
        userPage = userMapper.selectPage(userPage, queryWrapper);
        List<UserEntity> list = userPage.getRecords();
        Long total = userMapper.selectCount(queryWrapper);

        JSONArray userList = new JSONArray();
        for (UserEntity usr : list) {
            JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(usr));
            // 查询用户持有的角色身份
            obj.put("roles", userRoleService.getRoles(usr.getId()));
            obj.put("dept", deptService.getById(usr.getDeptId()));
            userList.add(obj);
        }

        JSONObject data = new JSONObject();
        // 组装json数据
        data.put("items", userList);
        data.put("total", total);
        return ApiResponse.success(data);
    }

    /**
     * 获取用户列表
     *
     * @param jsonString 用户筛选参数 Json[roleCode   角色编码（可选）]
     * @return 用户列表
     */
    @Override
    public ApiResponse getUsers(String jsonString) {
        JSONObject data = new JSONObject();
        HashMap<String, String> params = JSON.parseObject(jsonString, HashMap.class);
        data.put("users", userMapper.selectUserList(params));
        return ApiResponse.success(data);
    }

    /**
     * 创建用户
     *
     * @param jsonString 新用户的数据集
     * @param user       创建者
     * @return 状态码
     */
    @Override
    @Transactional
    public ApiResponse createUser(String jsonString, UserEntity user) {
        if ("".equals(jsonString) || user == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        UserEntity newUser = JSON.parseObject(jsonString, UserEntity.class);
        //查询是否已存在该登录名
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_Name", newUser.getUserName());
        try {
            if (userMapper.selectOne(queryWrapper) != null) {
                return ApiResponse.fail(EnumCode.ERROR_HANDLE);
            }
            //密码加密
            newUser.setUserPassword(MD5Util.md5(newUser.getUserPassword()));
            newUser.createTimeStamp(user);
            userMapper.insert(newUser);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 修改用户
     *
     * @param id         被更新用户的id
     * @param jsonString 被更新用户的新数据集
     * @param user       更新者
     * @return 状态码
     */
    @Override
    public ApiResponse updateUser(String id, String jsonString, UserEntity user) {
        if (id == null || "".equals(jsonString) || user == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        UserEntity entity = JSON.parseObject(jsonString, UserEntity.class);
        try {
            UserEntity userToUpdate = userMapper.selectById(id);
            if (userToUpdate == null) {
                return ApiResponse.fail(EnumCode.ERROR_HANDLE);
            }
            entity.setUserPassword(userToUpdate.getUserPassword());
            entity.updateTimeStamp(user);
            userMapper.updateById(entity);

            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 审核用户
     */
    @Override
    public ApiResponse audit(String id, UserEntity user) {
        if(id == null || user == null){
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        try{
            UserEntity userToAudit = userMapper.selectById(id);
            if(userToAudit == null){
                return ApiResponse.fail(EnumCode.ERROR_HANDLE);
            }
            userToAudit.setStatus("2");
            userToAudit.updateTimeStamp(user);
            userMapper.updateById(userToAudit);
            return ApiResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 校验用户密码
     *
     * @param jsonString 用户密码
     * @param token      用户的令牌
     * @return ‘incorrect’ 或 状态码
     */
    @Override
    public ApiResponse verifyUserPassword(String jsonString, String token) {
        if ("".equals(token) || "".equals(jsonString)) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        JSONObject json = JSON.parseObject(jsonString);
        if (json.getString("oldPass") == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        UserEntity user = getOperator(token);
        /*add by panh for 登录验证从核心库读取*/
        if(LoginUtil.useNtPtlLogin){
            Map ntPtl_userinfo=ntPtlMapper.selectUserByToken(token);
            user =LoginUtil.ptlUser2UserEntity(ntPtl_userinfo);
        }
        /*end*/
        if (user.getUserPassword().equals(MD5Util.md5(json.getString("oldPass")))) {
            return ApiResponse.success();
        } else {
            /*add by panh for 登录验证从核心库读取*/
            if(LoginUtil.useNtPtlLogin){
                if (user.getUserPassword().toLowerCase().equals(MD5Util.md5(json.getString("oldPass")))) {//注意原oa密码和核心库密码 md5加密时有大小写区分
                    return ApiResponse.success();
                }
            }
            /*end*/
            return ApiResponse.success("incorrect");
        }
    }

    /**
     * 修改用户自己的密码
     *
     * @param jsonString 用户的旧、新密码
     * @param token      用户的令牌
     * @return 状态码
     */
    @Override
    @Transactional
    public ApiResponse changeUserPassword(String jsonString, String token) {
        if ("".equals(token) || "".equals(jsonString)) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        JSONObject json = JSON.parseObject(jsonString);
        if (json.getString("oldPass") == null || json.getString("newPass") == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        UserEntity user = getOperator(token);
        /*add by panh for 登录验证从核心库读取*/
        if(LoginUtil.useNtPtlLogin){
            Map ntPtl_userinfo=ntPtlMapper.selectUserByToken(token);
            user =LoginUtil.ptlUser2UserEntity(ntPtl_userinfo);
        }
        /*end*/
        try {

//            if (!user.getUserPassword().equals(MD5Util.md5(json.getString("oldPass")))) {
//                return ApiResponse.fail("原密码输入错误，操作失败");
//            }
            /*add by panh for 登录验证从核心库读取*/
            if(LoginUtil.useNtPtlLogin){
                if (!user.getUserPassword().toLowerCase().equals(MD5Util.md5(json.getString("oldPass")))) {//注意原oa密码和核心库密码 md5加密时有大小写区分
                    return ApiResponse.fail("原密码输入错误，操作失败");
                }
            }else{
                if (!user.getUserPassword().equals(MD5Util.md5(json.getString("oldPass")))) {
                    return ApiResponse.fail("原密码输入错误，操作失败");
                }
            }
            /*end*/
            user.setUserPassword(MD5Util.md5(json.getString("newPass")));
            userMapper.updateById(user);

            /*add by panh for 登录验证从核心库读取*/
            if(LoginUtil.useNtPtlLogin){
                user.setUserPassword(MD5Util.md5(json.getString("newPass")).toUpperCase());//核心库密码 md5加密 必须要大写
                ntPtlMapper.updateUser(user);
            }
            /*end*/

            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 删除用户
     *
     * @param id 被删除用户的id
     * @return 状态码
     */
    @Override
    @Transactional
    public ApiResponse deleteUser(String id) {
        if (id == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        try {
            UserEntity userToDelete = userMapper.selectById(id);
            if (userToDelete == null) {
                return ApiResponse.fail(EnumCode.PART_SUCCESS);
            }
            userRoleService.deleteUserRoleByUserId(id);
            userMapper.deleteById(id);
            return ApiResponse.success(EnumCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 校验新用户名可注册性
     *
     * @param jsonString 用户数据集
     * @return exists 或 状态码
     */
    @Override
    public ApiResponse verifyUserName(String jsonString) {
        // 若传来id参数，说明正在更新用户而非创建

        if ("".equals(jsonString)) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        JSONObject json = JSON.parseObject(jsonString);
        if (json.containsKey("id") && (json.getString("id") == null || "".equals(json.getString("id"))) || json.getString("userName") == null || "".equals(json.get("userName"))) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", json.getString("userName"));
        UserEntity user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return ApiResponse.success();
        } else if (json.containsKey("id") && user.getId().equals(json.getString("id"))) {
            // 数据库中查到的用户不为空，但传来的id和数据库中查到的用户相同，说明用户名和原用户名相同，可继续占用该用户名
            return ApiResponse.success();
        } else {
            return ApiResponse.fail("exists");
        }
    }

    /**
     * 获取操作者后转发请求给UserRoleService
     *
     * @param id         被授予身份的用户的id
     * @param user       操作者
     * @param jsonString 授予的角色身份
     * @return 状态码
     */
    @Override
    public ApiResponse authorize(String id, String jsonString, UserEntity user) {
        return userRoleService.authorize(id, jsonString, user);
    }

    /**
     * 从操作者令牌获取操作者
     *
     * @param token 操作者令牌
     * @return 操作者
     */
    @Override
    public UserEntity getOperator(String token) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 树状显示用户角色关系
     * 转发请求给UserRoleService
     *
     * @param id 用户id
     * @return 用户持有的角色集
     */
    @Override
    public ApiResponse listUserRoleTreeSq(String id) {
        return userRoleService.listUserRoleTreeSq(id);
    }

    @Override
    public UserEntity getUserByColumnValue(String column, String value) {
        QueryWrapper<UserEntity> query = new QueryWrapper<>();
        query.eq(column, value);
        return userMapper.selectOne(query);
    }

    @Override
    @Transactional
    public ApiResponse register(String jsonString) {
        UserEntity newUser = JSON.parseObject(jsonString, UserEntity.class);
        newUser.setTelephone(newUser.getUserName());
        //查询是否已存在该登录名
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_Name", newUser.getUserName());
        try {
            if (userMapper.selectOne(queryWrapper) != null) {
                return ApiResponse.fail("该手机号已存在，注册失败");
            }
            String verification = (String) redisUtils.get(newUser.getTelephone());
            if(!newUser.getCaptchaCode().equals(verification)){
                return ApiResponse.fail("手机验证码错误，请检查");
            }
            //密码加密
            newUser.setUserPassword(MD5Util.md5(newUser.getUserPassword()));
            newUser.createTimeStamp(new UserEntity());
            userMapper.insert(newUser);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail("注册期间出错");
        }
    }

    @Override
    @Transactional
    public ApiResponse passUser(String id) {
        if (id == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        try {
            UserEntity userToPass = userMapper.selectById(id);
            if (userToPass == null) {
                return ApiResponse.fail(EnumCode.PART_SUCCESS);
            }
            String telephone =userToPass.getTelephone();
            userToPass.setStatus("2");
            userMapper.updateById(userToPass);
            sendMsg(telephone,"已审批通过，现可登录系统");
            return ApiResponse.success(EnumCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    @Override
    @Transactional
    public ApiResponse notPassUser(String id) {
        if (id == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        try {
            UserEntity userToDelete = userMapper.selectById(id);
            if (userToDelete == null) {
                return ApiResponse.fail(EnumCode.PART_SUCCESS);
            }
            String telephone = userToDelete.getTelephone();
//            userRoleService.deleteUserRoleByUserId(id);
            userMapper.deleteById(id);
            sendMsg(telephone,"审批不通过，请重新注册系统");
            return ApiResponse.success(EnumCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    public void sendMsg(String telephone,String msgContent){
        //组装发送数据
        Map colomnMap = new HashMap();
        colomnMap.put("contactTel", telephone);
        colomnMap.put("code", msgContent);
        //组装短信接口字段
        Map mapMes = new HashMap();
        mapMes.put("messageContent", colomnMap);
        mapMes.put("sendMobile", telephone);
        mapMes.put("source", smsApproveCode);
        feignMessageService.sendMessage(JSON.toJSONString(mapMes));
    }

    @Override
    @Transactional
    public ApiResponse forget(String jsonString) {
        UserEntity newUser = JSON.parseObject(jsonString, UserEntity.class);
        newUser.setTelephone(newUser.getUserName());
        //查询是否已存在该登录名
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_Name", newUser.getUserName());
        try {
            UserEntity userEntity = userMapper.selectOne(queryWrapper);
            if ( userEntity== null) {
                return ApiResponse.fail("该手机号不存在，请先注册");
            }
            String verification = (String) redisUtils.get(newUser.getTelephone());
            if(!newUser.getCaptchaCode().equals(verification)){
                return ApiResponse.fail("手机验证码错误，请检查");
            }
            //密码加密
            userEntity.setUserPassword(MD5Util.md5(newUser.getUserPassword()));
            userMapper.updateById(userEntity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail("忘记密码期间出错");
        }
    }

    @Override
    public ApiResponse selectAllUserAndDept() {
        //部门id-人员数组 Map
        HashMap<String,List> map = new HashMap();
        //查询所有人员
        List<UserEntity> userList = userMapper.selectAllUserAndDept();
        //根据部门id 分组人员
        for(UserEntity userEntity:userList){
            String deptId = userEntity.getDeptId();
            List list = map.get(deptId);
            if(null!=list){
                list.add(userEntity);
            }else{
                List newList = new ArrayList();
                newList.add(userEntity);
                map.put(userEntity.getDeptId(),newList);
            }
        }

        //获取部门树
        List<TDeptEntity> deptList = deptService.getDeptTree2();

        //每个部门放任
        putUser2Dept(deptList,map);

        return ApiResponse.success(deptList);
    }

    private void putUser2Dept(List<TDeptEntity> list,HashMap<String,List> map){
        for(TDeptEntity deptEntity:list){
            //有子部门继续迭代
            if(deptEntity.getChildren().size()>0){
                putUser2Dept(deptEntity.getChildren(),map);
                List userList = map.get(deptEntity.getId());
                if(null!=userList && userList.size()>0){
                    deptEntity.getChildren().addAll(userList);
                }else{
                    deptEntity.setDisabled(true);
                }
            }else{
                List userList = map.get(deptEntity.getId());
                if(null!=userList && userList.size()>0){
                    deptEntity.getChildren().addAll(userList);
                }else{
                    deptEntity.setDisabled(true);
                }
            }
        }
    }

}

