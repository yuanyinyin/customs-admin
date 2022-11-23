package com.nteport.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TArea;
import com.nteport.admin.mapper.TAreaMapper;
import com.nteport.admin.service.TAreaService;
import org.springframework.stereotype.Service;

/**
* @author User
* @description 针对表【t_area(区县表)】的数据库操作Service实现
* @createDate 2022-09-15 14:50:32
*/
@Service
public class TAreaServiceImpl extends ServiceImpl<TAreaMapper, TArea>
implements TAreaService {

}
