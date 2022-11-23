package com.nteport.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TCameraInfo;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.service.TCameraInfoService;
import com.nteport.admin.mapper.TCameraInfoMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
* @author User
* @description 针对表【t_camera_info】的数据库操作Service实现
* @createDate 2022-11-08 09:19:10
*/
@Service
public class TCameraInfoServiceImpl extends ServiceImpl<TCameraInfoMapper, TCameraInfo>
implements TCameraInfoService{

    @Override
    public ApiResponse queryCameras() {
        ApiResponse response = new ApiResponse();
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            List lineList = this.baseMapper.selectList(queryWrapper);
            HashMap map = new HashMap();
            map.put("camera",lineList);
            return response.fillMessage(map);
        } catch (Exception e) {
            e.printStackTrace();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }
}
