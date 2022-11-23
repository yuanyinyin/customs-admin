package com.nteport.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TMaintainPersonEntity;
import com.nteport.admin.service.TMaintainPersonService;
import com.nteport.admin.mapper.TMaintainPersonMapper;
import org.springframework.stereotype.Service;

/**
* @author qianjun
* @description 针对表【t_maintain_person(主要负责人表)】的数据库操作Service实现
* @createDate 2022-07-27 15:46:13
*/
@Service
public class TMaintainPersonServiceImpl extends ServiceImpl<TMaintainPersonMapper, TMaintainPersonEntity>
    implements TMaintainPersonService{

}




