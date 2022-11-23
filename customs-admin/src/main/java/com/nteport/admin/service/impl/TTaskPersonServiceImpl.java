package com.nteport.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TTaskPersonEntity;
import com.nteport.admin.service.TTaskPersonService;
import com.nteport.admin.mapper.TTaskPersonMapper;
import org.springframework.stereotype.Service;

/**
* @author qianjun
* @description 针对表【t_task_person(巡查指令子表)】的数据库操作Service实现
* @createDate 2022-07-27 15:46:13
*/
@Service
public class TTaskPersonServiceImpl extends ServiceImpl<TTaskPersonMapper, TTaskPersonEntity>
    implements TTaskPersonService{

}




