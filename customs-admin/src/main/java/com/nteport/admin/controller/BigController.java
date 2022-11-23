package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TDailyRecordService;
import com.nteport.admin.service.TLineInfoService;
import com.nteport.admin.service.TPointInfoService;
import com.nteport.admin.service.TRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/big")
public class BigController {
    @Autowired
    private TPointInfoService tPointInfoService;

    @Autowired
    private TLineInfoService lineInfoService;

    @Autowired
    private TDailyRecordService tDailyRecordService;

    @Autowired
    private TRiskService tRiskService;

    /**
     * 大屏 点位统计 类型
     */
    @GetMapping("/queryPointGroupByType")
    public ApiResponse queryPointGroupByType(){
        return tPointInfoService.queryPointGroupByType();
    }

    /**
     * 大屏 点位统计 区县
     */
    @GetMapping("/queryPointGroupByArea")
    public ApiResponse queryPointGroupByArea(){
        return tPointInfoService.queryPointGroupByArea();
    }

    /**
     * 大屏 岸线统计 区县
     */
    @GetMapping("/queryLineGroupByArea")
    public ApiResponse queryLineGroupByArea(){
        return lineInfoService.queryLineGroupByArea();
    }

    /**
     * 大屏 巡查统计
     */
    @GetMapping("/queryXuncha/{yearMonth}")
    public ApiResponse queryXuncha(@PathVariable String yearMonth){
        return tDailyRecordService.queryXuncha(yearMonth);
    }

    /**
     * 大屏 巡查内容
     */
    @GetMapping("/queryXunchaContent")
    public ApiResponse queryXunchaContent(){
        return tDailyRecordService.queryXunchaContent();
    }

    /**
     * 大屏 案发情况
     */
    @GetMapping("/queryAnfaCount/{year}")
    public ApiResponse queryAnfaCount(@PathVariable String year){
        return tPointInfoService.queryAnfaCount(year);
    }

    /**
     * 大屏 隐患排查-发现总数 和 已处置总数
     */
    @GetMapping("/queryRiskCount/{year}")
    public ApiResponse queryRiskCount(@PathVariable String year){
        return tRiskService.queryRiskCount(year);
    }

    /**
     * 大屏 隐患排查-一般风险 较高风险占比
     */
    @GetMapping("/queryRiskRatio/{year}")
    public ApiResponse queryRiskRatio(@PathVariable String year){
        return tRiskService.queryRiskRatio(year);
    }

    /**
     * 大屏 点位低中高风险个数
     */
    @GetMapping("/queryPointRiskLevel")
    public ApiResponse queryPointRiskLevel(){
        return tPointInfoService.queryPointRiskLevel();
    }

    /**
     * 大屏 点位展示
     */
    @GetMapping("/queryApprovedPoints")
    public ApiResponse queryApprovedPoints(){
        return tPointInfoService.queryApprovedPoints();
    }
}
