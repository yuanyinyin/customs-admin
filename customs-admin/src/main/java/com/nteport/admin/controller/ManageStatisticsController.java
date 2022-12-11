package com.nteport.admin.controller;


import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.companyStatistics.IQpDecHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 管理端
 * </p>
 *
 * @author Yyy
 * @since 2022-11-28
 */

@RestController
@RequestMapping("/manageStatistics")
public class ManageStatisticsController {
    @Autowired
    private IQpDecHeadService iQpDecHeadService;

    /**
     * 区域单量分析
     * @param params
     * @return
     */
    @GetMapping("/queryAreaData")
    public ApiResponse queryAreaData(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryAreaData(params,user);
    }
    /**
     * 管理端区域统计点击区县可进入该区县时间区间内各月数据
     * @param params
     * @return
     */
    @GetMapping("/queryAreaDataByCode")
    public ApiResponse queryAreaDataByCode(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryAreaDataByCode(params,user);
    }

    /**
     * 管理端
     *进出口商品货值
     * @param params
     * @param user
     * @return
     */
    @GetMapping("/queryGoodsData")
    public ApiResponse queryGoodsData(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryGoodsData(params,user);
    }
    /**
     * 管理端商品货值统计点击商品名称进入该商品详细分析界面
     * @param params
     * @return
     */
    @GetMapping("/queryGoodsDataByGNAME")
    public ApiResponse queryGoodsDataByGNAME(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryGoodsDataByGNAME(params,user);
    }
    /**
     * 管理端商品货值统计点击商品名称进入该商品详细分析界面
     * @param params
     * @return
     */
    @GetMapping("/queryGoodsDataDetail")
    public ApiResponse queryGoodsDataDetail(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryGoodsDataDetail(params,user);
    }
    /**
     *重点关注企业分析
     * @param params
     * @return
     */
    @GetMapping("/queryKeyCompanyData")
    public ApiResponse queryKeyCompanyData(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryKeyCompanyData(params,user);
    }
    /**
     *重点关注企业分析数据
     * @param params
     * @return
     */
    @GetMapping("/queryKeyCompanyDataSum")
    public ApiResponse queryKeyCompanyDataSum(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryKeyCompanyDataSum(params,user);
    }

    /**
     * 重点关注企业详情
     * @param params
     * @param user
     * @return
     */
    @GetMapping("/queryKeyCompanyDataByCode")
    public ApiResponse queryKeyCompanyDataByCode(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryKeyCompanyDataByCode(params,user);
    }
}
