package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.service.system.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private ILogService logService;

    @PostMapping("/listPage")
    public ApiResponse listPage(@RequestBody String jsonString) {
        return logService.listPage(jsonString);
    }

}
