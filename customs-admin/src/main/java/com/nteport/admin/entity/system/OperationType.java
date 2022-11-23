package com.nteport.admin.entity.system;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 操作类型
 */
public enum OperationType {

    ADD("add", "新增"),
    DELETE("delete", "删除"),
    MODIFY("modify", "修改"),
    QUERY("query", "查询");

    @EnumValue
    private final String type;

    private final String name;

    OperationType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
