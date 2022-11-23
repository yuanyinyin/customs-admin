package com.nteport.admin.config;

import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.system.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    ITokenService tokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == UserEntity.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = request.getHeader("authToken");
        // 然后根据token获取用户登录信息，这里省略获取用户信息的过程，随便填写一些数据
        UserEntity userEntity;
        if (token.startsWith("OPENID:")) {
            //获取openId
            String openId = token.substring(token.indexOf(":") + 1);
            userEntity = tokenService.getUserByColumnValue("open_id", openId);
        } else {
            userEntity = tokenService.getUserByColumnValue("token", token);
        }
        return userEntity;
    }

}
