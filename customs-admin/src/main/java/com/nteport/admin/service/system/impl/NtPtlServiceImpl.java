package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.MenuEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.MenuMapper;
import com.nteport.admin.mapper.NtPtlMapper;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.system.IMenuService;
import com.nteport.admin.service.system.INtPtlService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class NtPtlServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements INtPtlService {

    @Autowired
    private NtPtlMapper ntPtlMapper;

    /**
     * 查询用户
     * @return
     */
    @Transactional
    @Override
    public Map selectUser(String username, String password) {
        return ntPtlMapper.selectUser(username,password);
    }

    @Override
    public Map selectUserByToken(String token) {
        return ntPtlMapper.selectUserByToken(token);
    }

    @Override
    public Map selectDept(String depId) {
        return ntPtlMapper.selectDept(depId);
    }

    @Override
    public int updateTokenById(String id, String token) {
        return ntPtlMapper.updateTokenById(id,token);
    }

    @Override
    public ArrayList<String> queryRoleCodes(String userId){
        ArrayList<String> list = (ArrayList<String>) ntPtlMapper.queryRoleCodes(userId);
        return list;
    }

    @Override
    @Transactional
    public int registerDepUser(UserEntity user,String jsonString) {
        int result=0;
        Map map=ntPtlMapper.selectRoleByCode("WMB_QYGLY");//企业管理员角色
        if (map!=null){
            JSONObject json = JSON.parseObject(jsonString);
            String roleId=map.get("ROLEID").toString();
            String roleName=map.get("NAME").toString();

            String orgNameCn=json.getString("orgNameCn");//单位全称
            String unionNo=json.getString("unionNo");//统一社会信用代码
            String areaName=json.getString("areaName");//所在地区
            String areaCode=json.getString("areaCode");//所在地区
            String userName=json.getString("userName");//管理员账户名
            String realName=json.getString("realName");//真实姓名
            String password=json.getString("password");//密码
            String telephone=json.getString("telephone");//手机号
            String registerPicId=json.getString("registerPicId");//机构图片id

            String userId= UUID.randomUUID().toString().substring(0,18);
            String orgId= UUID.randomUUID().toString().substring(0,18);

            String role_employee_id=UUID.randomUUID().toString().substring(0,18);

            Map map_org_baseinfo=new HashMap();
            map_org_baseinfo.put("ID",orgId);
            map_org_baseinfo.put("ORG_NAME_CN",orgNameCn);
            map_org_baseinfo.put("INSPECT_NAME_CN",orgNameCn);
            map_org_baseinfo.put("ORG_NAME_SCN",orgNameCn);
            map_org_baseinfo.put("AREA_NAME",areaName);
            map_org_baseinfo.put("AREA_CODE",areaCode);
            map_org_baseinfo.put("CREATE_USER",userName);
            map_org_baseinfo.put("CREATE_USER_ID",userId);
            map_org_baseinfo.put("UPDATE_USER",userName);
            map_org_baseinfo.put("UPDATE_USER_ID",userId);
            map_org_baseinfo.put("RELATION_CODE","NTDZKA");
            map_org_baseinfo.put("RELATION_CN","南通电子口岸有限公司");
            map_org_baseinfo.put("RELATION_SN","201306050000007559");
            map_org_baseinfo.put("UNION_NO",unionNo);
            map_org_baseinfo.put("REGISTER_PIC_ID",registerPicId);


            Map map_org_moreinfo=new HashMap();
            map_org_moreinfo.put("ORG_ID",orgId);

            Map map_user_baseinfo=new HashMap();
            map_user_baseinfo.put("ID",userId);
            map_user_baseinfo.put("ORG_ID",orgId);
            map_user_baseinfo.put("USERNAME",userName);
            map_user_baseinfo.put("PASSWORD", MD5Util.md5(password).toUpperCase());//注意md5密码保存到门户库时  必须要大写
            map_user_baseinfo.put("REALNAME",realName);
            map_user_baseinfo.put("MOBILE",telephone);
            map_user_baseinfo.put("CREATE_USER",userName);
            map_user_baseinfo.put("CREATE_USER_ID",userId);
            map_user_baseinfo.put("UPDATE_USER",userName);
            map_user_baseinfo.put("UPDATE_USER_ID",userId);

            Map map_user_moreinfo=new HashMap();
            map_user_moreinfo.put("USER_ID",userId);

            Map map_user_role=new HashMap();
            map_user_role.put("ID",role_employee_id);
            map_user_role.put("ROLE_ID",roleId);
            map_user_role.put("EMPLOYEE_ID",userId);
            map_user_role.put("EMPLOYEE_NAME",realName);
            map_user_role.put("ROLE_NAME",roleName);

            if (ntPtlMapper.insertOrgBaseInfo(map_org_baseinfo)>0){
                if (ntPtlMapper.insertOrgMoreInfo(map_org_moreinfo)>0){
                    if (ntPtlMapper.insertUserBaseInfo(map_user_baseinfo)>0){
                        if (ntPtlMapper.insertUserMoreInfo(map_user_moreinfo)>0){
                            if (ntPtlMapper.insertUserRole(map_user_role)>0){
                                result=1;
                            }
                        }
                    }
                }
            }
        }
        if (result==0){
            try {
                throw new Exception();//如果有一步执行失败，则抛异常  回滚事务
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public List<Map> registerCheck(String column, String value) {
        if ("ORG_NAME_CN".equals(column)||"UNION_NO".equals(column)){
            return ntPtlMapper.selectDeptByParam(column,value);
        }else{
            return ntPtlMapper.selectUserByParam(column,value);
        }
    }

    @Override
    public String sendYzm(String phone, String ip, HttpServletRequest request) {
        String result="";
//        String phone=env.getRequest().getParameter("telephone");//手机号
//        String ip=env.getRequest().getRemoteAddr();
        if (phone==null||phone.trim().equals("")
                ||ip==null||ip.trim().equals("")) {//防止攻击  验证手机号和ip都不能为空
            return "send message error (attach)!!";
        }
        if (!checkIfCanSendYzm(ip,phone)) {//不能发送验证码
            return "send message error (attach)!!";
        }else{//能发送验证码
            String source_code=AliMessageUtil.SOURCE_CODE;//阿里云短信模板code
            String yzm=GUtilUuid.getUUID(6,GUtilUuid.UUID_TYPE_ONLY_NUMBER);//生成6位数字随机数验证码
            Map jsonMap=new HashMap();
            jsonMap.put("code", yzm);
            String jsonParam=JSON.toJSONString(jsonMap);//阿里云短信变量内容json字符串
            request.getSession().setAttribute("yzm", yzm);//把验证码存入session中用于后续判断是否正确
            AliMessageResult aliMessageResult= AliMessageUtil.sendMsg(phone,source_code, jsonParam);
            if (!aliMessageResult.getRequestId().isEmpty()&&"OK".equals(aliMessageResult.getCode())) {
                result="1";//短信发送成功
            }else{
                result="阿里云短信发送失败";//短信发送失败
            }
            save2sendedYzmInfoList(ip,phone,yzm);//add in 20220722 for 防止短信轰炸  添加手机号发送短信验证码间隔判断
//			env.getResponse().getWriter().write(yzm);
//            env.getResponse().getWriter().write(result);
        }
        return result;
    }

    //判断是否满足发送验证码间隔条件
    private boolean checkIfCanSendYzm(String ip,String phone){
        int yzm_send_jiange=50;//验证码发送间隔 （秒）
        List<Object[]> sendedYzmInfoList=YzmSession.sendedYzmInfoList;
        for (int i = 0; i < sendedYzmInfoList.size(); i++) {
            Object[] o=sendedYzmInfoList.get(i);
            String ip_temp=o[0].toString();
            String phone_temp=o[1].toString();
            String yzm_temp=o[2].toString();
            Long time_temp=Long.parseLong(o[3].toString());
            if (ip_temp.equals(ip)||phone_temp.equals(phone)) {
                //ip相同或者手机号相同  判断是否满足时间间隔要求
                Date date=new Date();
                long time=date.getTime();
                if(time-time_temp<yzm_send_jiange*1000){
                    return false;//不满足发送条件
                }
            }
        }
        return true;//满足发送条件
    }
    //记录发送验证码信息  throws Exception
    private void save2sendedYzmInfoList(String ip,String phone,String yzm) {//0.1  18862828888    0.2  18862828888
        Date date=new Date();
        long time=date.getTime();
        List<Object[]> sendedYzmInfoList= YzmSession.sendedYzmInfoList;
        if (sendedYzmInfoList!=null) {
            boolean isin=false;
            for (int i = 0; i < sendedYzmInfoList.size(); i++) {
                Object[] o1=sendedYzmInfoList.get(i);
                String ip_temp=o1[0].toString();
                String phone_temp=o1[1].toString();
                String yzm_temp=o1[2].toString();
                Long time_temp=Long.parseLong(o1[3].toString());
                if (ip_temp.equals(ip)||phone_temp.equals(phone)) {//在里面  替换
                    Object[] o=new Object[4];
                    o[0]=ip;
                    o[1]=phone;
                    o[2]=yzm;
                    o[3]=time;
                    sendedYzmInfoList.set(i, o);
                    isin=true;
                    break;
                }
            }
            if (!isin) {//不在里面
                Object[] o=new Object[4];
                o[0]=ip;
                o[1]=phone;
                o[2]=yzm;
                o[3]=time;
                sendedYzmInfoList.add(o);
            }
        }
    }
}
