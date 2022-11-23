package com.nteport.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nteport.admin.entity.TAppVersionEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.FileEntity;
import com.nteport.admin.service.TAppVersionService;
import com.nteport.admin.service.system.IFileService;
import com.nteport.admin.tool.MinIOTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/app")
public class AppVersionController {
    @Autowired
    private TAppVersionService appVersionService;

    /**
     *  查询最新版本信息
     * @return
     */
    @RequestMapping("/queryNewVersion")
    public ApiResponse queryNewVersion(){
        QueryWrapper<TAppVersionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_new","1");
        TAppVersionEntity entity = appVersionService.getOne(queryWrapper);
        return ApiResponse.success(entity);
    }

//    @RequestMapping("/downloadNew/{id}")
//    public void downloadNew(@PathVariable Long id, HttpServletResponse response){
//        try {
//            FileEntity fileEntity = fileService.getById(id);
//            minIOTool.download(fileEntity.getFileName(), fileEntity.getFileUrl(), response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
