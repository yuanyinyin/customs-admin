package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.service.TFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frequency")
public class FrequencyController {

    @Autowired
    private TFrequencyService frequencyService;

    /**
     * 查询所有
     */
    @GetMapping("/queryAll")
    public ApiResponse queryAll(){
        return ApiResponse.success(frequencyService.list());
    }

}
