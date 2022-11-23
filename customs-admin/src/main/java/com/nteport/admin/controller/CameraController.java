package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.service.TCameraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/camera")
public class CameraController {

    @Autowired
    private TCameraInfoService cameraInfoService;

    /**
     * 查询所有巡查点位
     * @return
     */
    @GetMapping("/queryCameras")
    public ApiResponse queryCameras(){
        return cameraInfoService.queryCameras();
    }

}
