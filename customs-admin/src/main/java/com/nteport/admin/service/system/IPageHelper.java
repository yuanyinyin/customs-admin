package com.nteport.admin.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

public interface IPageHelper<T> {

    QueryWrapper<T> query(Map<String, String> params);

}

