package com.nteport.admin.service.companyStatistics.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.companyStatistics.ImportantCompanyEntity;
import com.nteport.admin.entity.companyStatistics.ImportantGoodsEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.statistics.ImportantGoodsMapper;
import com.nteport.admin.service.companyStatistics.ImportantGoodsService;
import com.nteport.admin.service.system.IPageHelper;
import com.nteport.admin.service.system.impl.PageHelperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImportantGoodsServiceImpl extends ServiceImpl<ImportantGoodsMapper, ImportantGoodsEntity>implements ImportantGoodsService {

    @Autowired
    private ImportantGoodsMapper importantGoodsMapper;


    @Override
    public ApiResponse queryKeyGoods(Map<String, String> params, UserEntity user) {
        long pageSize = Long.parseLong(params.get("limit"));
        long pageNum = Long.parseLong(params.get("page"));
        String manageflag = String.valueOf(params.get("manageflag"));
        IPage<ImportantGoodsEntity> rolePage = new Page<>(pageNum, pageSize);
        //组装查询条件
        IPageHelper<ImportantGoodsEntity> pageHelper = new PageHelperImpl<>();
        QueryWrapper<ImportantGoodsEntity> queryWrapper = pageHelper.query(params);
        queryWrapper.eq("OWNER_ORG_ID",user.getDeptId());

        queryWrapper.orderByDesc("update_Date");

        //查询结果
        rolePage = importantGoodsMapper.selectPage(rolePage, queryWrapper);
        List<ImportantGoodsEntity> list = rolePage.getRecords();
        Long total = importantGoodsMapper.selectCount(queryWrapper);
        JSONObject data = new JSONObject();
        // 组装json数据
        data.put("items", list);
        data.put("total", total);
        return ApiResponse.success(data);
    }

    @Override
    public ApiResponse cancelKeyGoods(Long id) {
        ApiResponse response = new ApiResponse();
        try {
            if (null == id) {
                response.fillMessage(EnumCode.ERROR_HANDLE);
                return response;
            } else {
                importantGoodsMapper.deleteById(id);
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
    public ApiResponse queryGoodsList(Map<String, String> params, UserEntity user) {
        //分页
        Long pageSize = Long.parseLong(params.get("limit"));
        Long pageNum = Long.parseLong(params.get("page"));
        try {
            //查询结果
            String codets = String.valueOf(params.get("codets"));
            String gname = String.valueOf(params.get("gname"));

            HashMap map = new HashMap();

            map.put("codets", codets);
            map.put("gname", gname);
            map.put("orgId", user.getDeptId());
            long current = (pageNum - 1) * pageSize;
            map.put("current", current);
            map.put("pageSize", pageNum* pageSize);
            List<Map>list  = importantGoodsMapper.queryGoodsList(map);
            Integer goodsListCount = importantGoodsMapper.queryGoodsListCount(map);
            //组装json数据

            JSONObject data = new JSONObject();
            data.put("items", list);
            data.put("total", goodsListCount);

            return ApiResponse.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    @Override
    @Transactional
    public ApiResponse addGoods(String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        List<Map<String,String>> listObjectSec = JSONArray.parseObject(jsonString,List.class);
        ImportantGoodsEntity importantGoods=new ImportantGoodsEntity();
        try {
            if(listObjectSec.size()!=0){
                for(int i=0;i<listObjectSec.size();i++){
                    String GNAME = listObjectSec.get(i).get("GNAME");
                    String CODETS = listObjectSec.get(i).get("CODETS");
                    String IEFLAG = listObjectSec.get(i).get("IEFLAG");
                    QueryWrapper<ImportantGoodsEntity> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("HSCODE",CODETS);
                    queryWrapper.eq("GOODS_NAME",GNAME);
                    queryWrapper.eq("IEFLAG",IEFLAG);
                    queryWrapper.eq("OWNER_ORG_ID",user.getDeptId());
                    List<ImportantGoodsEntity> list = importantGoodsMapper.selectList(queryWrapper);
                    if(list.size()!=0){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ApiResponse.fail("重点商品设置失败，"+GNAME+"已存在！");
                    }

                    String GNAME1 = listObjectSec.get(i).get("GNAME") == null ? "" : (String) listObjectSec.get(i).get("GNAME");
                    importantGoods=new ImportantGoodsEntity();
                    importantGoods.setGoodsName(GNAME1);
                    importantGoods.setHscode(CODETS);
                    importantGoods.setIeflag(IEFLAG);
                    importantGoods.setOwnerOrg_id(user.getDeptId());
                    importantGoods.setOwnerOrg_name(user.getDeptName());
                    importantGoods.setCreateUserId(user.getId());
                    importantGoods.setCreateUser(user.getRealName());
                    importantGoods.setUpdateUserId(user.getId());
                    importantGoods.setUpdateUser(user.getRealName());
                    importantGoodsMapper.insert(importantGoods);

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
    public ApiResponse cancelGoods(Map<String, String> params, UserEntity user) {
        ApiResponse response = new ApiResponse();
        try {
            String ids = String.valueOf(params.get("ids"));
            String id[] = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                importantGoodsMapper.deleteById(id[i]);
            }
            return response.fillMessage(EnumCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

}
