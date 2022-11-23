package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.TPointInfoEntity;
import com.nteport.admin.entity.TTideEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TTideService;
import com.nteport.admin.mapper.TTideMapper;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.impl.PageHelperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_tide(潮汐发布表)】的数据库操作Service实现
* @createDate 2022-07-27 15:46:13
*/
@Service
public class TTideServiceImpl extends ServiceImpl<TTideMapper, TTideEntity>
    implements TTideService{

    @Autowired
    private TTideMapper tTideMapper;

    @Override
    @Transactional
    public ApiResponse create(String jsonString, UserEntity user) {
        if ("".equals(jsonString) || user == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        try{
            TTideEntity tTideEntity = JSON.parseObject(jsonString, TTideEntity.class);
            tTideEntity.setDeclareDate(LocalDate.now().toString());
            QueryWrapper<TTideEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("declare_date",tTideEntity.getDeclareDate());
            TTideEntity exist = tTideMapper.selectOne(queryWrapper);
            if(exist != null){
                return ApiResponse.fail("今日已添加过潮汐时间");
            }
            tTideEntity.createTimeStamp(user);
            this.baseMapper.insert(tTideEntity);
            return ApiResponse.success();
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    @Override
    public ApiResponse updateInfo(Long id,String jsonString, UserEntity user) {
        if(jsonString == null || user == null || null==id){
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        TTideEntity tTideEntity = JSON.parseObject(jsonString, TTideEntity.class);
        try {
//            TTideEntity entity = this.baseMapper.selectById(id);
//            if (entity == null) {
//                return ApiResponse.fail(EnumCode.ERROR_HANDLE);
//            }
            this.baseMapper.updateById(tTideEntity);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    @Override
    public ApiResponse listPage(Map<String, String> params) {
        //分页
        long pageSize = Long.parseLong(params.get("limit"));
        long pageNum = Long.parseLong(params.get("page"));
        IPage<TTideEntity> page = new Page<>(pageNum, pageSize);
        //组装查询条件
        IPageHelper<TTideEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<TTideEntity> queryWrapper = pageHelper.query(params);
        queryWrapper.orderByDesc("declare_date");
        page = this.baseMapper.selectPage(page, queryWrapper);
        List<TTideEntity> records = page.getRecords();
        Long total = this.baseMapper.selectCount(queryWrapper);
        //组装json数据
        JSONObject data = new JSONObject();
        data.put("items", records);
        data.put("total", total);
        return ApiResponse.success(data);
    }

    @Override
    public ApiResponse queryToday(){
        ApiResponse response = new ApiResponse();
        LocalDateTime today = LocalDateTime.now();
        String todayStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        QueryWrapper<TTideEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("declare_date",todayStr);
        TTideEntity entity = tTideMapper.selectOne(queryWrapper);
        HashMap map = new HashMap();
        if(entity == null){
            map.put("riseTime","暂无数据");
            map.put("fallTime","暂无数据");
            map.put("riseTime2","暂无数据");
            map.put("fallTime2","暂无数据");
        }else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            map.put("riseTime",entity.getRiseTime().format(formatter));
            map.put("fallTime",entity.getFallTime().format(formatter));
            map.put("riseTime2",entity.getRiseTime2().format(formatter));
            map.put("fallTime2",entity.getFallTime2().format(formatter));
        }
        return response.fillMessage(map);
    }

    @Override
    public Boolean ifInTide() {
        try {
            LocalDateTime now = LocalDateTime.now();
            String todayStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            QueryWrapper<TTideEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("declare_date",todayStr);
            TTideEntity entity = tTideMapper.selectOne(queryWrapper);
            LocalDateTime riseTime = entity.getRiseTime();
            LocalDateTime fallTime = entity.getFallTime();
            if(now.isAfter(riseTime) && now.isBefore(fallTime)) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}




