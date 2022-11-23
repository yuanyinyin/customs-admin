package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.dto.TPointInfoDto;
import com.nteport.admin.entity.TDeptEntity;
import com.nteport.admin.entity.TMaintainPersonEntity;
import com.nteport.admin.entity.TPointInfoEntity;
import com.nteport.admin.entity.TSupervisePersonEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.RoleEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.*;
import com.nteport.admin.service.TDeptService;
import com.nteport.admin.service.TPointInfoService;
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
* @description 针对表【t_point_info】的数据库操作Service实现
* @createDate 2022-07-27 15:46:13
*/
@Service
public class TPointInfoServiceImpl extends ServiceImpl<TPointInfoMapper, TPointInfoEntity>
    implements TPointInfoService{

    @Autowired
    private TPointInfoMapper tPointInfoMapper;
    @Autowired
    private TMaintainPersonMapper tMaintainPersonMapper;
    @Autowired
    private TSupervisePersonMapper tSupervisePersonMapper;
    @Autowired
    private TLineInfoMapper lineInfoMapper;
    @Autowired
    private TDeptMapper deptMapper;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private TDeptService tDeptService;
    @Autowired
    private TCameraInfoMapper cameraInfoMapper;


    @Override
    public ApiResponse create(String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(jsonString == null || user == null){
            response.fillMessage(EnumCode.ERROR_HANDLE);
            return response;
        }
        try {
            TPointInfoDto dto = JSON.parseObject(jsonString, TPointInfoDto.class);
            TPointInfoEntity entity = new TPointInfoEntity();
            BeanUtils.copyProperties(dto,entity);
            //前端目前只有提交 暂时设置成待审核态，后期可以加暂存按钮
            entity.setStatus("1");
            entity.createTimeStamp(user);
            tPointInfoMapper.insert(entity);
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
    public ApiResponse audit(Long id,String status,UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(id == null || user == null){
            response.fillMessage(EnumCode.ERROR_HANDLE);
            return response;
        }
        try{
            QueryWrapper<TPointInfoEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            TPointInfoEntity entity = tPointInfoMapper.selectOne(queryWrapper);
            if(entity == null){
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            }
            entity.setStatus(status);
            entity.updateTimeStamp(user);
            tPointInfoMapper.updateById(entity);
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
            TPointInfoDto dto = JSON.parseObject(jsonString,TPointInfoDto.class);
            TPointInfoEntity entity = new TPointInfoEntity();
            BeanUtils.copyProperties(dto,entity);
            QueryWrapper<TPointInfoEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",entity.getId());
            TPointInfoEntity exist =  tPointInfoMapper.selectOne(queryWrapper);
            if(exist == null){
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            }
            entity.updateTimeStamp(user);
            entity.setStatus("1");
            tPointInfoMapper.updateById(entity);
            QueryWrapper deleteWrapper = new QueryWrapper();
            deleteWrapper.eq("point_id",entity.getId());
            tSupervisePersonMapper.delete(deleteWrapper);
            tMaintainPersonMapper.delete(deleteWrapper);
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
            IPage<TPointInfoEntity> userPage = new Page<>(pageNum, pageSize);
            //组装查询条件
            IPageHelper<TPointInfoEntity> pageHelper = new PageHelperImpl<>();
            QueryWrapper<TPointInfoEntity> queryWrapper = pageHelper.query(params);
            queryWrapper.orderByDesc("update_time");
            this.filterUserData(queryWrapper,user);
            //查询结果
            userPage = tPointInfoMapper.selectPage(userPage, queryWrapper);
            List<TPointInfoEntity> list = userPage.getRecords();
            List<TPointInfoDto> dtoList = new ArrayList<>();
            for (TPointInfoEntity entity :
                    list) {
                Long id = entity.getId();
                QueryWrapper personWrapper = new QueryWrapper();
                personWrapper.eq("point_id",id);
                List<TMaintainPersonEntity> maintainPersonEntities = tMaintainPersonMapper.selectList(personWrapper);
                List<TSupervisePersonEntity> supervisePersonEntities = tSupervisePersonMapper.selectList(personWrapper);
                TPointInfoDto dto = new TPointInfoDto();
                BeanUtils.copyProperties(entity,dto);
                dto.setSupervisePersonList(supervisePersonEntities);
                dto.setMaintainPersonList(maintainPersonEntities);
                dtoList.add(dto);
            }
            Long total = tPointInfoMapper.selectCount(queryWrapper);
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
    public void filterUserData(QueryWrapper queryWrapper,UserEntity user){
        List<RoleEntity> roleList = userRoleService.getRoles(user.getId());
        TDeptEntity dept = tDeptService.getDeptEntityById(user.getDeptId());
        String roleString = "";
        for (RoleEntity roleEntity : roleList) {
            roleString += roleEntity.getRoleCode() + ",";
        }
        if (roleString.contains("admin")|| roleString.contains("cityAdmin") || roleString.contains("sjbmAdmin") || roleString.contains("cityXunfang")) {
        }else if(roleString.contains("qxdsbAdmin") || roleString.contains("qxbmAdmin") || roleString.contains("qxbmXunfang")){
            queryWrapper.eq("area_Id", dept.getAreaId());
        } else  {
            queryWrapper.eq("area_Id", dept.getAreaId());
            queryWrapper.eq("street_Id", dept.getStreetId());
        }
    }

    @Override
    public ApiResponse queryPointByName(Map<String, String> params) {
        String pointName = params.get("pointName");
        QueryWrapper personWrapper = new QueryWrapper();
        personWrapper.like("name",pointName);
        return ApiResponse.success(this.baseMapper.selectList(personWrapper));
    }

    /**
     * 创建各类负责人
     * @param dto
     * @param user
     * @return
     */
    public boolean createPerson(Long pointId,TPointInfoDto dto,UserEntity user){
        try {
            List<TMaintainPersonEntity> maintainPersonList = dto.getMaintainPersonList();
            if(maintainPersonList != null && maintainPersonList.size() > 0) {
                for (TMaintainPersonEntity entity :
                        maintainPersonList) {
                    entity.setPointId(pointId);
                    entity.createTimeStamp(user);
                    tMaintainPersonMapper.insert(entity);
                }
            }
            List<TSupervisePersonEntity> supervisePersonList = dto.getSupervisePersonList();
            if(supervisePersonList != null && supervisePersonList.size() > 0) {
                for (TSupervisePersonEntity entity :
                        supervisePersonList) {
                    entity.setPointId(pointId);
                    entity.createTimeStamp(user);
                    tSupervisePersonMapper.insert(entity);
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
    public ApiResponse queryInfoById(Long id) {
        try {
            TPointInfoEntity tPointInfoEntity = tPointInfoMapper.selectById(id);
            TPointInfoDto dto = new TPointInfoDto();
            BeanUtils.copyProperties(tPointInfoEntity,dto);
            QueryWrapper personWrapper = new QueryWrapper();
            personWrapper.eq("point_id",id);
            List<TMaintainPersonEntity> maintainPersonEntities = tMaintainPersonMapper.selectList(personWrapper);
            List<TSupervisePersonEntity> supervisePersonEntities = tSupervisePersonMapper.selectList(personWrapper);
            dto.setSupervisePersonList(supervisePersonEntities);
            dto.setMaintainPersonList(maintainPersonEntities);
            return ApiResponse.success(dto);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse queryPoints() {
        ApiResponse response = new ApiResponse();
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            List pointList = tPointInfoMapper.selectList(queryWrapper);
            List lineList = lineInfoMapper.selectList(queryWrapper);
            HashMap map = new HashMap();
            map.put("point",pointList);
            map.put("line",lineList);
            return response.fillMessage(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse pointCount() {
        List<HashMap> list = tPointInfoMapper.pointCount();
        HashMap result = new HashMap();
        for (HashMap map :
                list) {
            String type = map.get("name").toString();
            Object value = map.get("value");
            switch (type){
                case "码头":result.put("dockNum",value);break;
                case "渔港":result.put("fishingPortNum",value);break;
                case "船闸":result.put("shipLockNum",value);break;
                case "船舶临时停靠点":result.put("temporaryStopPointNum",value);break;
                case "其他走私风险点":result.put("otherNum",value);break;
                case "低风险":result.put("low",value);break;
                case "中风险":result.put("middle",value);break;
                case "高风险":result.put("high",value);break;
            }
        }
        return ApiResponse.success(result);
    }

    @Override
    public ApiResponse getUpdateList(UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(user == null){
            return response.fillMessage(EnumCode.BAD_REQUEST);
        }
        //控制展示权限
        List<RoleEntity> roleList = userRoleService.getRoles(user.getId());
        List<String> roleCodes = new ArrayList<>();
        for (RoleEntity role :
                roleList) {
            roleCodes.add(role.getRoleCode());
        }
        if(roleCodes.size() <= 0){
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
        QueryWrapper<TDeptEntity> deptWrapper = new QueryWrapper<>();
        deptWrapper.eq("id",user.getDeptId());
        TDeptEntity dept = deptMapper.selectOne(deptWrapper);
        if(dept == null){
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
        HashMap map = new HashMap();
        if(roleCodes.contains("admin") || roleCodes.contains("cityAdmin") || roleCodes.contains("sjbmAdmin") || roleCodes.contains("cityXunfang")){

        }else if(roleCodes.contains("qxdsbAdmin") || roleCodes.contains("qxbmAdmin") || roleCodes.contains("qxbmXunfang")){
            map.put("area_id",dept.getAreaId());
        }else{
            map.put("street_id",dept.getStreetId());
        }
        List list = tPointInfoMapper.selectUpdateTop5(map);
        return response.fillMessage(list);
    }

    @Override
    public ApiResponse getAllPoints() {
        ApiResponse response = new ApiResponse();
        try {
            QueryWrapper<TPointInfoEntity> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("along","2");
            List<TPointInfoEntity> seaList = tPointInfoMapper.selectList(queryWrapper1);
            QueryWrapper<TPointInfoEntity> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("along","1");
            List<TPointInfoEntity> riverList = tPointInfoMapper.selectList(queryWrapper2);
            HashMap map = new HashMap();
            map.put("sea",seaList);
            map.put("river",riverList);
            return response.fillMessage(map);
        } catch (Exception e) {
            e.printStackTrace();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public ApiResponse queryPointGroupByType() {
        return ApiResponse.success(tPointInfoMapper.queryPointGroupByType());
    }

    @Override
    public ApiResponse queryPointGroupByArea() {
        return ApiResponse.success(tPointInfoMapper.queryPointGroupByArea());
    }

    @Override
    public ApiResponse queryAnfaCount(String year) {
        return ApiResponse.success(tPointInfoMapper.queryAnfaCount(year));
    }

    @Override
    public ApiResponse queryPointRiskLevel() {
        List<HashMap> list = tPointInfoMapper.queryPointRiskLevel();
        HashMap map = new HashMap();
        for(HashMap mm:list){
            if("低".equals(mm.get("name"))){
                map.put("low",mm.get("num"));
            }else if("中".equals(mm.get("name"))){
                map.put("middle",mm.get("num"));
            }else if("高".equals(mm.get("name"))){
                map.put("high",mm.get("num"));
            }
        }
        return ApiResponse.success(map);
    }

    @Override
    public ApiResponse queryApprovedPoints() {
        ApiResponse response = new ApiResponse();
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("status","2");
            List<TPointInfoEntity> pointList = tPointInfoMapper.selectList(queryWrapper);
            translateName4Point(pointList);
            List lineList = lineInfoMapper.selectList(queryWrapper);
            List cameraList = cameraInfoMapper.selectList(queryWrapper);
            HashMap map = new HashMap();
            map.put("point",pointList);
            map.put("line",lineList);
            map.put("camera",cameraList);
            return response.fillMessage(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail(EnumCode.ERROR_HANDLE);
        }
    }

    public void translateName4Point(List<TPointInfoEntity> pointList){
        for(TPointInfoEntity entity:pointList){
            entity.setAlongName(null==entity.getAlong() ?"":ConstantUtil.POINT_ALONGMAP.get(entity.getAlong()));
            entity.setPropertiesName(ConstantUtil.POINT_PROPERTIESMAP.get(entity.getProperties()));
            if("1".equals(entity.getType())){
                entity.setCategoryName(ConstantUtil.POINT_CATEGORYMAP_1.get(entity.getCategory()));
            }else if("2".equals(entity.getType())){
                entity.setCategoryName(ConstantUtil.POINT_CATEGORYMAP_2.get(entity.getCategory()));
            }else if("3".equals(entity.getType())){
                entity.setCategoryName(ConstantUtil.POINT_CATEGORYMAP_3.get(entity.getCategory()));
            }
            entity.setCraneName(ConstantUtil.POINT_CRANEMAP.get(entity.getCrane()));
            entity.setWhetherSailName(ConstantUtil.POINT_WHETHERSAILMAP.get(entity.getWhetherSail()));
            entity.setRiskLevelName(ConstantUtil.POINT_RISKLEVELMAP.get(entity.getRiskLevel()));
            entity.setPartName(ConstantUtil.POINT_PARTMAP.get(entity.getPart()));
            entity.setGoodstypeName(ConstantUtil.POINT_GOODSTYPEMAP.get(entity.getGoodstype()));
        }
    }
}
