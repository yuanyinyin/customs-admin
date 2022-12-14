package com.nteport.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nteport.admin.entity.QpDecHead;
import com.nteport.admin.entity.system.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户接口
 *
 * @author zzq
 * @since 2020-11-19
 */
@Repository
public interface QpDecHeadMapper extends BaseMapper<QpDecHead> {

    List<HashMap<String,Object>>  exportExcel(Map params);

}
