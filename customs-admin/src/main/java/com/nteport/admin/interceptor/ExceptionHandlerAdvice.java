package com.nteport.admin.interceptor;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.nteport.admin.entity.system.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {

    /**
     * Exception
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse handleGlobalException(Exception exception, HandlerMethod handlerMethod) {
        if (exception instanceof MethodArgumentNotValidException) {
            List<ObjectError> errors = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors();
            StringBuilder sb = new StringBuilder();
            if (!CollectionUtils.isEmpty(errors)) {
                for (ObjectError error : errors) {
                    if (sb.length() != 0) {
                        sb.append("，");
                    }

                    sb.append(error.getDefaultMessage());
                }
            }
            return ApiResponse.fail(sb.toString());
        }

        // 约束异常
        if (exception instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> exceptionSet = ((ConstraintViolationException) exception).getConstraintViolations();
            StringBuilder sb = new StringBuilder();
            if (!CollectionUtils.isEmpty(exceptionSet)) {
                for (ConstraintViolation<?> set : exceptionSet) {
                    if (sb.length() != 0) {
                        sb.append("，");
                    }

                    sb.append(set.getMessageTemplate());
                }
            }

            return ApiResponse.fail(sb.toString());
        }

        // 参数类型转换异常处理
        if (exception instanceof MethodArgumentTypeMismatchException) {
            return ApiResponse.fail(((MethodArgumentTypeMismatchException) exception).getName() + " 类型不匹配");
        }

        if (exception instanceof JsonMappingException) {
            return ApiResponse.fail("JSON格式错误, " + exception.getLocalizedMessage());
        }

        if (exception instanceof HttpMessageNotReadableException) {
            return ApiResponse.fail("请求体格式错误, " + exception.getLocalizedMessage());
        }
        if (exception instanceof MissingServletRequestParameterException) {
            String paramName = ((MissingServletRequestParameterException) exception).getParameterName();
            return ApiResponse.fail(paramName + " 不能为空");
        }

        //if (exception instanceof MarketingException) {
        //    MarketingException marketingException = (MarketingException) exception;
        //    return RdfaResult.fail(marketingException.getErrorCodeEnum().getCode(), exception.getMessage());
        //}

        // 其他异常打印日志
        log.error("{}.{} error, ", handlerMethod.getBeanType().getSimpleName(), handlerMethod.getMethod().getName(), exception);

        //if (exception instanceof RpcException) {
        //    return RdfaResult.fail(ErrorCodeEnum.RPC_ERROR.getCode(), "RPC调用错误，请稍后重试");
        //}

        return ApiResponse.fail("服务器内部错误，请联系开发人员！");
    }


}
