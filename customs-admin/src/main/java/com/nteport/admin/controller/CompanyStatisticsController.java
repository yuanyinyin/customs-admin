package com.nteport.admin.controller;


import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.companyStatistics.IQpDecHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 企业端报关单单量信息
 * </p>
 *
 * @author Yyy
 * @since 2022-11-28
 */

@RestController
@RequestMapping("/companyStatistics")
public class CompanyStatisticsController {
    @Autowired
    private IQpDecHeadService iQpDecHeadService;

    /**
     * 进口、出口报关单单量信息
     * @param params
     * @return
     */
    @GetMapping("/queryImOrExCustomsData")
    public ApiResponse getImOrExCustomsData(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryImOrExCustomsData(params,user);
    }

    /**
     *进出口运输方式
     * @param params
     * @param user
     * @return
     */
    @GetMapping("/queryTransportMode")
    public ApiResponse queryTransportMode(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryTransportMode(params,user);
    }
    /**
     *进出口岸
     * @param params
     * @param user
     * @return
     */
    @GetMapping("/queryPortStatistic")
    public ApiResponse queryPortStatistic(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.portStatistic(params,user);
    }
    /**
     *贸易方式统计
     * @param params
     * @param user
     * @return
     */
    @GetMapping("/queryTradeMode")
    public ApiResponse queryExportTradeMode(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryTradeMode(params,user);
    }
    /**
     *报关货值统计
     * @param params
     * @param user
     * @return
     */
    @GetMapping("/queryGoodsTotal")
    public ApiResponse queryGoodsTotal(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryGoodsTotal(params,user);
    }
    /**
     *
     * 查验率统计
     * @param params
     * @return
     */
    @GetMapping("/queryInspectRate")
    public ApiResponse queryInspectRate(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryInspectRate(params,user);
    }
    /**
     *进出口商品金额TOP10
     * @param params
     * @param user
     * @return
     */
    @GetMapping("/queryTopGoods")
    public ApiResponse queryTopGoods(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryTopGoods(params,user);
    }

    /**
     *进出口商品申报频率TOP10
     * @param params
     * @param user
     * @return
     */
    @GetMapping("/queryTopFrequency")
    public ApiResponse queryTopFrequency(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryTopFrequency(params,user);
    }

    /**
     *
     * 查验率统计   首页
     * @param params
     * @return
     */
    @GetMapping("/queryInspectRateDash")
    public ApiResponse queryInspectRateDash(@RequestParam Map<String, String> params, UserEntity user) {
        return iQpDecHeadService.queryInspectRateDash(params,user);
    }
}
