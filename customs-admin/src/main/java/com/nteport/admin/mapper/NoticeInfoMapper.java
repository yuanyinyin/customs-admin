package com.nteport.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nteport.admin.entity.NoticeInfo;
import com.nteport.admin.entity.QpDecUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户接口
 *
 * @author zzq
 * @since 2020-11-19
 */
@Repository
public interface NoticeInfoMapper extends BaseMapper<NoticeInfo> {



    int updateByIds(List<String> ids);

}
