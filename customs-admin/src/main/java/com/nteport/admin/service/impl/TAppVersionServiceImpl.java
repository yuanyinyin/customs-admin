package com.nteport.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TAppVersionEntity;
import com.nteport.admin.mapper.TAppVersionMapper;
import com.nteport.admin.service.TAppVersionService;
import org.springframework.stereotype.Service;

/**
* @author yinq
* @description 针对表【t_app_version】的数据库操作Service实现
* @createDate 2022-08-11 09:30:37
*/
@Service
public class TAppVersionServiceImpl extends ServiceImpl<TAppVersionMapper, TAppVersionEntity>
implements TAppVersionService {

}
