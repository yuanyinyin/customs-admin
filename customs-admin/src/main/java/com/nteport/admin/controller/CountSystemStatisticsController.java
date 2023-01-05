package com.nteport.admin.controller;


import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.companyStatistics.ImportantCompanyService;
import com.nteport.admin.service.companyStatistics.ImportantGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/countSystemStatistics")
public class CountSystemStatisticsController {
    @Autowired
    private ImportantCompanyService importantCompanyService;
    @Autowired
    private ImportantGoodsService importantGoodsService;
    /**
     * 重点企业设置模块列表
     * @param
     * @return
     */
    @GetMapping("/queryKeyCompany")
    public ApiResponse queryKeyCompany(@RequestParam Map<String, String> params, UserEntity user) {

        return importantCompanyService.queryKeyCompany(params, user);
    }

    /**
     * 重点商品设置模块列表
     * @param
     * @return
     */
    @GetMapping("/queryKeyGoods")
    public ApiResponse queryKeyGoods(@RequestParam Map<String, String> params, UserEntity user) {

        return importantGoodsService.queryKeyGoods(params, user);
    }
    /**
     * 取消重点企业设置
     * @param id
     * @return
     */
    @DeleteMapping("/cancelKeyCompany/{id}")
    public ApiResponse cancelKeyCompany(@PathVariable(value = "id") Long id) {
        return importantCompanyService.cancelKeyCompany(id);
    }

    /**
     * 所有企业
     * @param
     * @return
     */
    @GetMapping("/queryCompanyList")
    public ApiResponse queryCompanyList(@RequestParam Map<String, String> params, UserEntity user) {

        return importantCompanyService.queryCompanyList(params, user);
    }
    /**
     * 所有商品
     * @param
     * @return
     */
    @GetMapping("/queryGoodsList")
    public ApiResponse queryGoodsList(@RequestParam Map<String, String> params, UserEntity user) {

        return importantGoodsService.queryGoodsList(params, user);
    }
    /**
     * 重点企业设置
     * @param
     * @param user
     * @return
     */
    @PostMapping("/addCompany")
    public ApiResponse addCompany(@RequestBody String jsonString,
                                               UserEntity user) {
        return importantCompanyService.addCompany(jsonString,user);
    }
    /**
     * 重点商品设置
     * @param
     * @param user
     * @return
     */
    @PostMapping("/addGoods")
    public ApiResponse addGoods(@RequestBody String jsonString,
                                  UserEntity user) {
        return importantGoodsService.addGoods(jsonString,user);
    }
    @PostMapping("/cancelKeyCompany")
    public ApiResponse cancelCompany(@RequestParam Map<String, String> params, UserEntity user) {

        return importantCompanyService.cancelCompany(params, user);
    }
    /**
     * 取消
     * @param id
     * @return
     */
    @DeleteMapping("/cancelKeyGoods/{id}")
    public ApiResponse cancelKeyGoods(@PathVariable(value = "id") Long id) {
        return importantGoodsService.cancelKeyGoods(id);
    }

    @PostMapping("/cancelGoods")
    public ApiResponse cancelGoods(@RequestParam Map<String, String> params, UserEntity user) {

        return importantGoodsService.cancelGoods(params, user);
    }
}
