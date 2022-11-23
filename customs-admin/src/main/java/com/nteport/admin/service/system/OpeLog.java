package com.nteport.admin.service.system;

import com.nteport.admin.entity.system.OperationType;

import java.lang.annotation.*;

/**
 * 自定义操作日志的注解
 *
 * @author zhangyue
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpeLog {

    /**
     * 操作模块
     **/
    String opeModule() default "";

    /**
     * 操作类型 增删改
     **/
    OperationType opeType();

    /**
     * 操作描述
     **/
    String opeDesc() default "";

}
