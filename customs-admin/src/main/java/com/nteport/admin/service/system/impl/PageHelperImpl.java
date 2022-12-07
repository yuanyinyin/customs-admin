package com.nteport.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class PageHelperImpl<T> implements IPageHelper<T> {

    @Override
    public QueryWrapper<T> query(Map<String, String> params) {
        params.remove("page");
        params.remove("limit");
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(StringUtils.isBlank(value)){
                continue;
            }
            queryWrapper.like(MyStringUtil.humpToLineUp(key), value);
        }
        return queryWrapper;
    }

}
