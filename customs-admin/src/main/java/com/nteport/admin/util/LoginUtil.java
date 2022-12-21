package com.nteport.admin.util;

import com.nteport.admin.entity.system.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
/*
    登录相关配置辅助类
 */
@Component
public class LoginUtil {
    public static boolean useNtPtlLogin = true;//是否从核心库登录和读取权限

    //静态变量获取配置文件值
    @Autowired
    private Environment env;

    @PostConstruct
    public void config() {
        menu_root_id = env.getProperty("login.menu.rootId");
    }

//    public static String menu_root_id="A5615576-31E1-4212-A051-3AF93CA68AFD";
//    @Value("${login.menu.rootId}")
    public static String menu_root_id;//外贸帮rm菜单根节点
//
//    @Value("${sms_customs_register}")
//    private String smsCustomsRegister;

    public static UserEntity ptlUser2UserEntity(Map ntPtl_user){
        UserEntity user=null;
        if (ntPtl_user!=null){
            /*
            userEntity
                realName = "管理员二"
                userName = "admin2"
                deptId = "100"
                deptName = null
                userPassword = "202cb962ac59075b964b07152d234b70"
                telephone = "18012341234"
                token = "6e5ea89a-6ac5-4f57-bfc3-d721e2623798"
                openId = "b79ef7d4-f0cb-41a8-87d6-9a2c6ceb971d"
                status = "2"
                captchaCode = null
                job = null
                value = "8"
                roleCodes = {ArrayList@11485}  size = 0
                label = "管理员二"
                id = "8"
                createTime = {LocalDateTime@11486} "2022-07-29T14:34:15"
                updateTime = {LocalDateTime@11487} "2022-08-05T13:54:37"
                createUser = "1"
                updateUser = "1"
                creatorName = null
            nt_ptl user:
                "UPDATE_USER_ID" -> "201304270000003641"
                "ORG_ID" -> "201306050000007559"
                "PASSWORD_UPDATE_DATE" -> {Timestamp@11798} "2022-08-01 15:57:53.0"
                "REALNAME" -> "潘浩"
                "CREATE_USER" -> "admin"
                "UPDATE_DATE" -> {Timestamp@11804} "2022-08-01 14:52:45.0"
                "SEX" -> "男"
                "UPDATE_USER" -> "admin"
                "STATE" -> "2"
                "EMAIL_STATE" -> "1"
                "IS_ADMIN" -> "1"
                "NICKNAME" -> "panh"
                "CREATE_DATE" -> {Timestamp@11818} "2015-07-28 10:33:51.0"
                "PASSWORD" -> "8DDCFF3A80F4189CA1C9D4D902C3C909"
                "CREATE_USER_ID" -> "201304270000003641"
                "APP_CFG_STATE" -> "1"
                "USER_TYPE" -> "2"
                "USERNAME" -> "panh"
                "FIND_STATE" -> "0"
                "ID" -> "201507280000196807"
                "PHONE_STATE" -> "1"
                "MOBILE" -> "11111111111"
             */
//            if (ntPtl_userinfo!=null&&ntPtl_userinfo.size()>0){
//                Map ntPtl_user=(Map)ntPtl_userinfo.get(0);
                user=new UserEntity();
                user.setRealName(ntPtl_user.get("REALNAME").toString());
                user.setUserName(ntPtl_user.get("USERNAME").toString());
                user.setDeptId(ntPtl_user.get("ORG_ID").toString());
//                user.setDeptName();
                user.setUserPassword(ntPtl_user.get("PASSWORD").toString());
                user.setTelephone(getMapValue(ntPtl_user,"MOBILE"));
                user.setToken(getMapValue(ntPtl_user,"TOKEN"));
//                user.setOpenId();
                user.setStatus(ntPtl_user.get("STATE").toString());
//                user.setCaptchaCode();
//                user.setJob();
                user.setValue(ntPtl_user.get("ID").toString());
//                user.setRoleCodes();
                user.setLabel(ntPtl_user.get("REALNAME").toString());

                user.setId(ntPtl_user.get("ID").toString());
                user.setCreateTime(CommonUtil.string2LocalDateTime1(ntPtl_user.get("CREATE_DATE").toString(),"yyyy-MM-dd HH:mm:ss"));
                user.setUpdateTime(CommonUtil.string2LocalDateTime1(ntPtl_user.get("UPDATE_DATE").toString(),"yyyy-MM-dd HH:mm:ss"));
                user.setCreateUser(ntPtl_user.get("CREATE_USER_ID").toString());
                user.setUpdateUser(ntPtl_user.get("CREATE_USER_ID").toString());
//                user.setCreatorName();
//            }
        }
        return user;
    }
    public static String getMapValue(Map map,String key){
        if (map!=null&&map.get(key)!=null){
            return map.get(key).toString();
        }
        return null;
    }
}
