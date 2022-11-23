package com.nteport.admin.entity.system;

public enum EnumCode {

    // 根据业务需求进行添加
    SUCCESS(200, "处理成功"),

    PART_SUCCESS(206, "部分处理成功"),

    ERROR_HANDLE(300, "处理数据失败"),

    BAD_REQUEST(400, "请求本身存在错误"),

    UNAUTHORIZED(401, "用户未认证"),

    FORBIDDEN(403, "用户无权限访问该资源"),

    ERROR_PATH(404, "请求资源不存在"),

    ERROR_GONE(410, "请求对象不存在"),

    UNPROCESSABLE_ENTITY(422, "请求的实体不允许修改"),

    ERROR_SERVER(500, "服务器内部发生错误");

    private int code;
    private String message;

    EnumCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

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

}
