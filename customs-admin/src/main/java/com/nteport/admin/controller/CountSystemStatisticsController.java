package com.nteport.admin.controller;


import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.companyStatistics.IQpDecHeadService;
import com.nteport.admin.service.companyStatistics.ImportantCompanyService;
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
    @PostMapping("/cancelKeyCompany")
    public ApiResponse cancelCompany(@RequestParam Map<String, String> params, UserEntity user) {

        return importantCompanyService.cancelCompany(params, user);
    }
}
