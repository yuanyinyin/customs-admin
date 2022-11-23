package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.dto.TLineInfoDto;
import com.nteport.admin.entity.TDeptEntity;
import com.nteport.admin.entity.TLineInfoEntity;
import com.nteport.admin.entity.TPatrolPersonEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.TLineInfoMapper;
import com.nteport.admin.mapper.TPatrolPersonMapper;
import com.nteport.admin.service.TDeptService;
import com.nteport.admin.service.TLineInfoService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.IUserRoleService;
import com.nteport.admin.service.system.impl.PageHelperImpl;
import com.nteport.admin.util.ConstantUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author qianjun
* @description 针对表【t_line_info】的数据库操作Service实现
* @createDate 2022-09-07 16:37:24
*/
@Service
public class TLineInfoServiceImpl extends ServiceImpl<TLineInfoMapper, TLineInfoEntity>
    implements TLineInfoService {

    @Autowired
    private TLineInfoMapper lineInfoMapper;
    @Autowired
    private TPatrolPersonMapper patrolPersonMapper;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private TDeptService tDeptService;

    @Override
    public ApiResponse create(String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(jsonString == null || user == null){
            response.fillMessage(EnumCode.ERROR_HANDLE);
            return response;
        }
        try {
            TLineInfoDto dto = JSON.parseObject(jsonString, TLineInfoDto.class);
            TLineInfoEntity entity = new TLineInfoEntity();
            BeanUtils.copyProperties(dto,entity);
            //前端目前只有提交 暂时设置成待审核态，后期可以加暂存按钮
            entity.setStatus("1");
            entity.createTimeStamp(user);
            lineInfoMapper.insert(entity);
            if(createPerson(entity.getId(),dto,user)) {
                return ApiResponse.success();
            }else {
                response.fillMessage(EnumCode.ERROR_HANDLE);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.fillMessage(EnumCode.ERROR_SERVER);
        }
        return response;
    }

    /**
     * 创建巡查负责人
     * @param dto
     * @param user
     * @return
     */
    public boolean createPerson(Long lineId,TLineInfoDto dto,UserEntity user){
        try {
            List<TPatrolPersonEntity> patrolPersonList = dto.getPatrolPersonList();
            if(patrolPersonList != null && patrolPersonList.size() > 0) {
                for (TPatrolPersonEntity entity :
                        patrolPersonList) {
                    entity.setLineId(lineId);
                    entity.createTimeStamp(user);
                    patrolPersonMapper.insert(entity);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    public ApiResponse audit(Long id,String status,UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(id == null || user == null){
            response.fillMessage(EnumCode.ERROR_HANDLE);
            return response;
        }
        try{
            QueryWrapper<TLineInfoEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            TLineInfoEntity entity = lineInfoMapper.selectOne(queryWrapper);
            if(entity == null){
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            }
            entity.setStatus(status);
            entity.updateTimeStamp(user);
            lineInfoMapper.updateById(entity);
            return ApiResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.fillMessage(EnumCode.ERROR_SERVER);
        }
        return response;
    }

    @Override
    public ApiResponse updateInfo(String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(jsonString == null || user == null){
            response.fillMessage(EnumCode.ERROR_HANDLE);
            return response;
        }
        try{
            TLineInfoDto dto = JSON.parseObject(jsonString,TLineInfoDto.class);
            TLineInfoEntity entity = new TLineInfoEntity();
            BeanUtils.copyProperties(dto,entity);
            QueryWrapper<TLineInfoEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",entity.getId());
            TLineInfoEntity exist =  lineInfoMapper.selectOne(queryWrapper);
            if(exist == null){
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            }
            entity.updateTimeStamp(user);
            entity.setStatus("1");
            lineInfoMapper.updateById(entity);
            QueryWrapper deleteWrapper = new QueryWrapper();
            deleteWrapper.eq("line_id",entity.getId());
            patrolPersonMapper.delete(deleteWrapper);
            if(createPerson(entity.getId(),dto,user)) {
                return ApiResponse.success();
            }else {
                response.fillMessage(EnumCode.ERROR_HANDLE);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.fillMessage(EnumCode.ERROR_SERVER);
        }
        return response;
    }

    @Override
    public ApiResponse listPage(Map<String, String> params, UserEntity user) {
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            //分页
            long pageSize = Long.parseLong(params.get("limit"));
            long pageNum = Long.parseLong(params.get("page"));
            IPage<TLineInfoEntity> userPage = new Page<>(pageNum, pageSize);
            //组装查询条件
            IPageHelper<TLineInfoEntity> pageHelper = new PageHelperImpl<>();
            QueryWrapper<TLineInfoEntity> queryWrapper = pageHelper.query(params);
            queryWrapper.orderByDesc("update_time");
            this.filterUserData(queryWrapper, user);
            //查询结果
            userPage = lineInfoMapper.selectPage(userPage, queryWrapper);
            List<TLineInfoEntity> list = userPage.getRecords();
            List<TLineInfoDto> dtoList = new ArrayList<>();
            for (TLineInfoEntity entity :
                    list) {
                Long id = entity.getId();
                QueryWrapper personWrapper = new QueryWrapper();
                personWrapper.eq("line_id",id);
                List<TPatrolPersonEntity> patrolPersonEntities = patrolPersonMapper.selectList(personWrapper);
                TLineInfoDto dto = new TLineInfoDto();
                BeanUtils.copyProperties(entity,dto);
                dto.setPatrolPersonList(patrolPersonEntities);
                dtoList.add(dto);
            }
            Long total = lineInfoMapper.selectCount(queryWrapper);
            //组装json数据
            data.put("items", dtoList);
            data.put("total", total);
            result.put("data", data);
            return ApiResponse.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_HANDLE);
        }
    }

    /**
     * 增加 根据角色 所在部门 筛选相关数据
     * @param queryWrapper
     * @param user
     */
    public void filterUserData(QueryWrapper queryWrapper, UserEntity user) {
        List<RoleEntity> roleList = userRoleService.getRoles(user.getId());
        TDeptEntity dept = tDeptService.getDeptEntityById(user.getDeptId());
        String roleString = "";
        for (RoleEntity roleEntity : roleList) {
            roleString += roleEntity.getRoleCode() + ",";
        }
        if (roleString.contains(ConstantUtil.ROLE_ADMIN)|| roleString.contains(ConstantUtil.ROLE_CITYADMIN) || roleString.contains(ConstantUtil.ROLE_SJBMADMIN) || roleString.contains(ConstantUtil.ROLE_CITYXUNFANG)) {
        }else if(roleString.contains(ConstantUtil.ROLE_QXDSBADMIN) || roleString.contains(ConstantUtil.ROLE_QXBMADMIN) || roleString.contains(ConstantUtil.ROLE_QXBMXUNFANG)){
            queryWrapper.eq("area_Id", dept.getAreaId());
        } else {
            queryWrapper.eq("area_Id", dept.getAreaId());
            queryWrapper.eq("street_Id", dept.getStreetId());
        }
    }

    @Override
    public ApiResponse queryLineByName(Map<String, String> params) {
        String lineName = params.get("lineName");
        QueryWrapper personWrapper = new QueryWrapper();
        personWrapper.like("name",lineName);
        return ApiResponse.success(this.baseMapper.selectList(personWrapper));
    }

    @Override
    public ApiResponse queryInfoById(Long id) {
        try {
            TLineInfoEntity tLineInfoEntity = lineInfoMapper.selectById(id);
            TLineInfoDto dto = new TLineInfoDto();
            BeanUtils.copyProperties(tLineInfoEntity,dto);
            QueryWrapper personWrapper = new QueryWrapper();
            personWrapper.eq("line_id",id);
            List<TPatrolPersonEntity> patrolPersonEntities = patrolPersonMapper.selectList(personWrapper);
            dto.setPatrolPersonList(patrolPersonEntities);
            return ApiResponse.success(dto);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse queryLines() {
        ApiResponse response = new ApiResponse();
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            List lineList = lineInfoMapper.selectList(queryWrapper);
            HashMap map = new HashMap();
            map.put("line",lineList);
            return response.fillMessage(map);
        } catch (Exception e) {
            e.printStackTrace();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse lineCount() {
        ApiResponse response = new ApiResponse();
        QueryWrapper<TLineInfoEntity> queryWrapper = new QueryWrapper<>();
        Long count = lineInfoMapper.selectCount(queryWrapper);
        response.fillMessage(count);
        return response;
    }

    @Override
    public ApiResponse queryLineGroupByArea() {
        return ApiResponse.success(lineInfoMapper.queryLineGroupByArea());
    }

}




