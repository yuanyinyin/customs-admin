package com.nteport.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TStreet;
import com.nteport.admin.mapper.TStreetMapper;
import com.nteport.admin.service.TStreetService;
import org.springframework.stereotype.Service;

/**
* @author User
* @description 针对表【t_street(乡镇街道表)】的数据库操作Service实现
* @createDate 2022-09-15 14:53:59
*/
@Service
public class TStreetServiceImpl extends ServiceImpl<TStreetMapper, TStreet>
implements TStreetService {

}
