package com.nteport.admin.service.companyStatistics.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.companyStatistics.ImportantCompanyEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.statistics.ImportantCompanyMapper;
import com.nteport.admin.service.companyStatistics.ImportantCompanyService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.impl.PageHelperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class ImportantCompanyServiceImpl extends ServiceImpl<ImportantCompanyMapper, ImportantCompanyEntity>implements ImportantCompanyService {

    @Autowired
    private ImportantCompanyMapper importantCompanyMapper;

    @Override
    public ApiResponse queryKeyCompany(Map<String, String> params,  UserEntity user) {
        long pageSize = Long.parseLong(params.get("limit"));
        long pageNum = Long.parseLong(params.get("page"));
        IPage<ImportantCompanyEntity> rolePage = new Page<>(pageNum, pageSize);
        //组装查询条件
        IPageHelper<ImportantCompanyEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<ImportantCompanyEntity> queryWrapper = pageHelper.query(params);
        queryWrapper.orderByDesc("update_Date");

        //查询结果
        rolePage = importantCompanyMapper.selectPage(rolePage, queryWrapper);
        List<ImportantCompanyEntity> list = rolePage.getRecords();
        Long total = importantCompanyMapper.selectCount(queryWrapper);
        JSONObject data = new JSONObject();
        // 组装json数据
        data.put("items", list);
        data.put("total", total);
        return ApiResponse.success(data);
    }

    @Override
    public ApiResponse cancelKeyCompany(Long id) {
        ApiResponse response = new ApiResponse();
        try {
            if (null == id) {
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            } else {
                importantCompanyMapper.deleteById(id);
                response.fillMessage(EnumCode.SUCCESS);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    @Override
    public ApiResponse queryCompanyList(Map<String, String> params, UserEntity user) {
        //分页
        Long pageSize = Long.parseLong(params.get("limit"));
        Long pageNum = Long.parseLong(params.get("page"));
        try {
            //查询结果
            String customNo = String.valueOf(params.get("customNo"));
            String orgName = String.valueOf(params.get("orgName"));

            HashMap map = new HashMap();

            map.put("customNo", customNo);
            map.put("orgName", orgName);
            long current = (pageNum - 1) * pageSize;
            map.put("current", current);
            map.put("pageSize", pageNum* pageSize);
            List<Map>list  = importantCompanyMapper.queryCompanyList(map);
            Integer bidPageListByUserCount = importantCompanyMapper.queryCompanyListCount(map);
            //组装json数据

            JSONObject data = new JSONObject();
            data.put("items", list);
            data.put("total", bidPageListByUserCount);

            return ApiResponse.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    @Override
    @Transactional
    public ApiResponse addCompany(String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        List<Map<String,String>> listObjectSec = JSONArray.parseObject(jsonString,List.class);
        ImportantCompanyEntity importantCompany=new ImportantCompanyEntity();
            try {
            if(listObjectSec.size()!=0){
                for(int i=0;i<listObjectSec.size();i++){
                    String ORG_NAME_CN = listObjectSec.get(i).get("ORG_NAME_CN");
                    String ID = listObjectSec.get(i).get("ID");
                    QueryWrapper<ImportantCompanyEntity> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("ORG_ID",ID);
                    List<ImportantCompanyEntity> list = importantCompanyMapper.selectList(queryWrapper);
                    if(list.size()!=0){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ApiResponse.fail("重点企业设置失败，"+ORG_NAME_CN+"已存在！");
                    }

                    if( listObjectSec.get(i).get("CUSTOM_NO")==null|| listObjectSec.get(i).get("CUSTOM_NO")==""){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ApiResponse.fail("重点企业设置失败，缺少海关编码！");
                    }
                    String CUSTOM_NO = listObjectSec.get(i).get("CUSTOM_NO");
                    String AREA_NAME = listObjectSec.get(i).get("AREA_NAME") == null ? "" : (String) listObjectSec.get(i).get("AREA_NAME");
                    importantCompany=new ImportantCompanyEntity();
                    importantCompany.setOrgId(ID);
                    importantCompany.setOrgName(ORG_NAME_CN);
                    importantCompany.setCustomCode(CUSTOM_NO);
                    importantCompany.setCreateUserId(user.getId());
                    importantCompany.setCreateUser(user.getRealName());
                    importantCompany.setUpdateUserId(user.getId());
                    importantCompany.setUpdateUser(user.getRealName());
                    importantCompanyMapper.insert(importantCompany);

                }
            }
                return response.fillMessage(EnumCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    @Override
    @Transactional
    public ApiResponse cancelCompany(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        try {
            String ids = String.valueOf(params.get("ids"));
            String id[] = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                importantCompanyMapper.deleteById(id[i]);
            }
            return response.fillMessage(EnumCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }


}
