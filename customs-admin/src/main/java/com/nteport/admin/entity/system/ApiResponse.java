package com.nteport.admin.entity.system;

import java.io.Serializable;

/**
 * @author ZZQ
 */
public class ApiResponse implements Serializable {

    private static final long serialVersionUID = 793034041048451317L;

    private int code;

    private String message;

    private Object data = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse() {
    }

    /**
     * 放入响应枚举
     */
    public ApiResponse fillMessage(EnumCode codeEnum) {
        this.setCode(codeEnum.getCode());
        this.setMessage(codeEnum.getMessage());
        return this;
    }

    /**
     * 放入响应码及信息
     */
    public ApiResponse fillMessage(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
        return this;
    }

    /**
     * 处理成功，放入自定义业务数据集合
     */
    public ApiResponse fillMessage(Object data) {
        this.setCode(EnumCode.SUCCESS.getCode());
        this.setMessage(EnumCode.SUCCESS.getMessage());
        this.data = data;
        return this;
    }

    public static ApiResponse success(Object data) {
        ApiResponse apiResponse =  new ApiResponse(EnumCode.SUCCESS.getCode(),EnumCode.SUCCESS.getMessage(),data);
        return apiResponse;
    }

    public static ApiResponse success() {
        ApiResponse apiResponse =  new ApiResponse(EnumCode.SUCCESS.getCode(),EnumCode.SUCCESS.getMessage());
        return apiResponse;
    }

    public static ApiResponse fail(EnumCode codeEnum) {
        ApiResponse apiResponse = new ApiResponse(codeEnum.getCode(), codeEnum.getMessage());
        return apiResponse;
    }

    public static ApiResponse fail(String message) {
        ApiResponse apiResponse = new ApiResponse(EnumCode.BAD_REQUEST.getCode(), message);
        return apiResponse;
    }

}
