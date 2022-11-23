package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nteport.admin.dto.LoginDataMobileDTO;
import com.nteport.admin.dto.LoginDataMobileSeriesDTO;
import com.nteport.admin.entity.TAppVersionEntity;
import com.nteport.admin.entity.TDailyRecordEntity;
import com.nteport.admin.entity.TLoginLogEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.TDailyRecordMapper;
import com.nteport.admin.mapper.TLoginLogMapper;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.FeignMessageService;
import com.nteport.admin.service.TAppVersionService;
import com.nteport.admin.service.system.ILoginService;
import com.nteport.admin.util.MD5Util;
import com.nteport.admin.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TLoginLogMapper tLoginLogMapper;
    @Autowired
    private TDailyRecordMapper tDailyRecordMapper;

    @Autowired
    private FeignMessageService feignMessageService;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${sms_verify_code}")
    private String smsVerifyCode;

    @Autowired
    private TAppVersionService appVersionService;

    /**
     * 登陆验证
     */
    @Override
    public ApiResponse loginValidate(String jsonString) {
        JSONObject object = JSONObject.parseObject(jsonString);

        String userName = object.get("userName").toString();
        if (userName == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        String password = object.get("password").toString();
        if (password == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        queryWrapper.eq("user_password", MD5Util.md5(password));
        try {
            UserEntity user = userMapper.selectOne(queryWrapper);
            if (user != null) {
                if(!"2".equals(user.getStatus())){
                    return ApiResponse.fail("该用户待审核中，审核通过后即可登录");
                }
                user.setToken(UUID.randomUUID().toString());
                userMapper.updateById(user);

                //登录成功变插入登录日志
                TLoginLogEntity logEntity = new TLoginLogEntity();
                logEntity.setUserName(user.getUserName());
                logEntity.setDeptId(user.getDeptId());
                logEntity.createTimeStamp(user);
                tLoginLogMapper.insert(logEntity);
                return ApiResponse.success(user.getToken());
            } else {
                return new ApiResponse().fillMessage(201, "用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    @Override
    public ApiResponse mobileLogin(String jsonString) {
        JSONObject object = JSONObject.parseObject(jsonString);
        //先检查版本
        String versionCode = object.get("versionCode").toString();
        boolean flag = this.checkVeersionCode(versionCode);
        if(flag){
            return ApiResponse.fail("APP版本需要更新,请重新打开并进行更新");
        }

        String userName = object.get("userName").toString();
        if (userName == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        String password = object.get("password").toString();
        if (password == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        queryWrapper.eq("user_password", MD5Util.md5(password));
        try {
            UserEntity user = userMapper.selectOne(queryWrapper);
            if (user != null) {
                if(!"2".equals(user.getStatus())){
                    return ApiResponse.fail("该用户待审核中，审核通过后即可登录");
                }
//                user.setToken(UUID.randomUUID().toString());
                user.setOpenId(UUID.randomUUID().toString());
                userMapper.updateById(user);

                //登录成功变插入登录日志
                TLoginLogEntity logEntity = new TLoginLogEntity();
                logEntity.setUserName(user.getUserName());
                logEntity.setDeptId(user.getDeptId());
                logEntity.createTimeStamp(user);
                tLoginLogMapper.insert(logEntity);
//                return ApiResponse.success(user.getToken());
                return ApiResponse.success(user.getOpenId());
            } else {
                return new ApiResponse().fillMessage(201, "用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    private boolean checkVeersionCode(String nowVersion){
        QueryWrapper<TAppVersionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_new","1");
        TAppVersionEntity entity = appVersionService.getOne(queryWrapper);
        if(nowVersion.equals(entity.getVersion())){
            return false;
        }else{
            return true;
        }
    }

    /**
     * App自动登录
     * @param jsonString
     * @return
     */
    @Override
    public ApiResponse autoLogin(String jsonString) {
        JSONObject object = JSONObject.parseObject(jsonString);
        String token = object.get("token").toString();
        if (token == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", token);
        try {
            UserEntity user = userMapper.selectOne(queryWrapper);
            if (user != null) {
                if(!"2".equals(user.getStatus())){
                    return ApiResponse.fail("该用户待审核中，审核通过后即可登录");
                }
//                user.setToken(UUID.randomUUID().toString());
                user.setOpenId(UUID.randomUUID().toString());
                userMapper.updateById(user);

                //登录成功变插入登录日志
                TLoginLogEntity logEntity = new TLoginLogEntity();
                logEntity.setUserName(user.getUserName());
                logEntity.setDeptId(user.getDeptId());
                logEntity.createTimeStamp(user);
                tLoginLogMapper.insert(logEntity);
//                return ApiResponse.success(user.getToken());
                return ApiResponse.success(user.getOpenId());
            } else {
                return new ApiResponse().fillMessage(201, "账号过期，请重新登录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 登出
     */
    @Override
    public ApiResponse logout(UserEntity user) {
        if (user == null) {
            return ApiResponse.success();
        }
        user.setToken("");
        try {
            userMapper.updateById(user);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    @Override
    public ApiResponse getVerifyCode(String jsonString) {
        try {
            JSONObject json = JSON.parseObject(jsonString);
            String telephone = json.getString("telephone");
            if (telephone == null) {
                return ApiResponse.fail(EnumCode.BAD_REQUEST);
            }
            StringBuilder str = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                str.append(random.nextInt(10));
            }
            String verification = str.toString();
            //组装发送数据
            Map colomnMap = new HashMap();
            colomnMap.put("contactTel", telephone);
            colomnMap.put("code", verification);
            //组装短信接口字段
            Map mapMes = new HashMap();
            mapMes.put("messageContent", colomnMap);
            mapMes.put("sendMobile", telephone);
            mapMes.put("source", smsVerifyCode);
            Map resultMap = feignMessageService.sendMessage(JSON.toJSONString(mapMes));
            if ("1".equals(resultMap.get("result"))) {
                redisUtils.setWithTime(telephone, verification, 5L, TimeUnit.MINUTES);
                return ApiResponse.success();
            } else {
                return ApiResponse.fail("验证码发送失败，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail("验证码发送出错");
        }
    }

    @Override
    public ApiResponse statLogin() {
        ApiResponse response = new ApiResponse();
        try {
            List list = tLoginLogMapper.listPage();
            return ApiResponse.success(list);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse statLoginMobile() {
        ApiResponse response = new ApiResponse();
        try {
            List<HashMap> list = tLoginLogMapper.listPage();
            //构造前端统计结构
            return ApiResponse.success(buildLoginDataMobile(list));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse personalCount(UserEntity user){
        //查询当月登录次数
        QueryWrapper<TLoginLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DATE_FORMAT(create_time,'%Y-%m')",LocalDate.now().toString().substring(0,7));
        queryWrapper.eq("user_name",user.getUserName());
        Long count = tLoginLogMapper.selectCount(queryWrapper);

        //查询当月每日巡次数
        QueryWrapper<TDailyRecordEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("DATE_FORMAT(create_time,'%Y-%m')",LocalDate.now().toString().substring(0,7));
        queryWrapper2.eq("create_user",user.getId());
        Long count2 = tDailyRecordMapper.selectCount(queryWrapper2);

        HashMap map = new HashMap();
        map.put("personnalLogin",count);
        map.put("personnalDaily",count2);
        return ApiResponse.success(map);
    }

    @Override
    public ArrayList<String> queryRoleCodes(UserEntity user){
        ArrayList<String> list = (ArrayList<String>) userMapper.queryRoleCodes(user.getId());
        return list;
    }

    private LoginDataMobileDTO buildLoginDataMobile(List<HashMap> list){
        LoginDataMobileDTO dto = new LoginDataMobileDTO();
        ArrayList<String> categoriesList = new ArrayList<>();
        LoginDataMobileSeriesDTO todayTimesDTO = new LoginDataMobileSeriesDTO();
        LoginDataMobileSeriesDTO monthTimesDTO = new LoginDataMobileSeriesDTO();

        for(Map map :list){
            categoriesList.add((String)map.get("dept_name"));
            todayTimesDTO.getData().add((Long)map.get("todayTimes"));
            monthTimesDTO.getData().add((Long)map.get("monthTimes"));
        }
        todayTimesDTO.setName("当日登录次数");
        monthTimesDTO.setName("当月登录次数");
        dto.setCategories(categoriesList);
        dto.getSeries().add(todayTimesDTO);
        dto.getSeries().add(monthTimesDTO);
        return dto;
    }

}
