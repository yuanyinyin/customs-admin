package com.nteport.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nteport.admin.entity.TStreet;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.service.TAreaService;
import com.nteport.admin.service.TStreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/street")
public class StreetController {

    @Autowired
    private TStreetService streetService;

    /**
     * 查询所有
     */
    @GetMapping("/queryAll")
    public ApiResponse queryAll(){
        return ApiResponse.success(streetService.list());
    }

    @GetMapping("/queryByArea/{id}")
    public ApiResponse queryByArea(@PathVariable Long id){
        QueryWrapper<TStreet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("area_id",id);
        return ApiResponse.success(streetService.list(queryWrapper));
    }

}
