package com.nteport.admin.controller;

import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.TNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private TNoticeService noticeService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody String jsonString, UserEntity user){
        return noticeService.create(jsonString,user);
    }

    @GetMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id){
        return noticeService.delete(id);
    }

    @PostMapping("/update/{id}")
    public ApiResponse update(@PathVariable Long id,UserEntity user,@RequestBody String jsonString){
        return noticeService.update(id,user,jsonString);
    }

    @GetMapping("/listPage")
    public ApiResponse listPage(@RequestParam Map<String, String> params, UserEntity user){
        return noticeService.listPage(params,user);
    }
}
