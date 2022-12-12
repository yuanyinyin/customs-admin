package com.nteport.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.NtPtlMapper;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.util.LoginUtil;
import com.nteport.admin.util.MD5Util;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 拦截器，每次请求判断token是否有效
 *
 * @author zhangyue
 */
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NtPtlMapper ntPtlMapper;

    private String urls[] = {
            "/login/validate",
            "/login/mobileLogin",//app登录
            "/login/autoLogin",//自动登录
            "/user/register",
            "/user/forget",//忘记密码
            "/login/getVerifyCode",//获取短信验证
            "/dept/getDept",//部门查询
            "/app/queryNewVersion",//版本检查
            "/login/logout",
            "/wx/getWxLoginInfo",
            "/login/registerDepUser",//企业用户注册
    };

    /**
     * 拦截请求，判断token
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull Object handler) throws Exception {
        try {
            String url = httpServletRequest.getRequestURI();
            System.out.println(url);
            // 获取参数中的token验证相关参数
            String token = httpServletRequest.getHeader("authToken");
            // 遍历需要忽略拦截的路径
            for (String item : this.urls) {
                item = httpServletRequest.getContextPath() + item;
                //正式环境请删除
                if (url.contains("/swagger") || url.contains("/webjars")) {
                    return true;
                }
                //文件下载暂时放行
                if (url.contains("/file/downLoad/")) {
                    return true;
                }
                if (item.contains(url)) {
                    return true;
                }
            }

            String flag;
            if (null == token || "".equals(token)) {
                return false;
            }

            // 查询验证token
            QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
            //小程序端登录，用openId鉴权；web端登录，用token鉴权
            if (token.startsWith("OPENID:")) {
                //获取openId
                flag = "wx";
                String openId = token.substring(token.indexOf(":") + 1);
                token = openId;
                queryWrapper.eq("open_id", openId);
            } else {
                flag = "web";
                queryWrapper.eq("token", token);
            }

            UserEntity userEntity = userMapper.selectOne(queryWrapper);
            /*add by panh for 登录验证从核心库读取*/
            if(LoginUtil.useNtPtlLogin){
                Map ntPtl_userinfo=ntPtlMapper.selectUserByToken(token);
                userEntity =LoginUtil.ptlUser2UserEntity(ntPtl_userinfo);
            }
            /*end*/

            //todo 增加userEntity为空校验

            boolean verify = false;
            if(null!=userEntity){
                if ("wx".equals(flag)) {
//                verify = token.equals(userEntity.getOpenId());
                    verify = token.equals(userEntity.getOpenId());
                } else if ("web".equals(flag)) {
                    verify = token.equals(userEntity.getToken());
                }
            }

            if (userEntity == null || !verify) {
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json; charset=utf-8");
                PrintWriter out;
                try {
                    ApiResponse response = new ApiResponse();
                    response.fillMessage(EnumCode.UNAUTHORIZED);
                    String json = JSONObject.toJSONString(response);
                    httpServletResponse.setContentType("application/json");
                    out = httpServletResponse.getWriter();
                    // 返回json信息给前端
                    out.append(json);
                    out.flush();
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    httpServletResponse.sendError(500);
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.sendError(401);
            return false;
        }
        return true;
    }

}
