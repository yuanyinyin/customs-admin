package com.nteport.admin.util;

import com.nteport.admin.entity.system.LogEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.LogMapper;
import com.nteport.admin.service.system.IUserService;
import com.nteport.admin.service.system.OpeLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 切面处理类，操作日志的记录
 *
 * @author zhangyue
 */
@Aspect
@Component
public class OpeLogAspect {

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private IUserService userService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.nteport.admin.service.system.OpeLog)")
    public void opeLogPoinCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     */
    @AfterReturning(value = "opeLogPoinCut()")
    public void saveOperLog(JoinPoint joinPoint) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        LogEntity operLog;
        UserEntity user = userService.getOperator(request.getHeader("authToken"));
        try {

            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OpeLog opLog = method.getAnnotation(OpeLog.class);

            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = className + "." + method.getName();

            if (opLog != null) {
                operLog = new LogEntity();
                operLog.setOpeModule(opLog.opeModule()); // 操作模块
                operLog.setOpeDesc(opLog.opeDesc()); // 操作描述
                operLog.setOpeMethod(methodName); // 请求方法
                operLog.setOpeReqUri(request.getRequestURI()); // 请求的url
                operLog.setOpeIp(request.getRemoteAddr()); // 请求IP
                if(null!=user){
                    operLog.setHandler(user.getRealName());
                    operLog.setCreateUser(user.getId());
                    operLog.setUpdateUser(user.getId());
                }
                operLog.setCreateTime(LocalDateTime.now());
                operLog.setUpdateTime(LocalDateTime.now());
                logMapper.insert(operLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
