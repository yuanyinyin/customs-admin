package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.service.TAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private TAreaService areaService;

    /**
     * 查询所有
     */
    @GetMapping("/queryAll")
    public ApiResponse queryAll(){
        return ApiResponse.success(areaService.list());
    }

}
