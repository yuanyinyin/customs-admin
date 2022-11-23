package com.nteport.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.FileEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.service.system.IFileService;
import com.nteport.admin.tool.MinIOTool;
import com.nteport.admin.util.Base64DecodedMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 附件管理 前端控制器
 *
 * @author Yang, Botao
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private MinIOTool minIOTool;

    @Autowired
    private IFileService fileService;

    /**
     * <p>文件上传</p>
     *
     * @param file 合同附件(支持多格式)
     * @param user 文件上传人
     * @return 状态码
     */
    @PostMapping("/upload")
    public ApiResponse upload(MultipartFile file, @RequestParam String type,
                              UserEntity user) {
        String originFileName = file.getOriginalFilename();
        String suffix = originFileName.substring(originFileName.lastIndexOf(".") + 1);
        String reFileName= UUID.randomUUID() + "." + suffix;
        minIOTool.upload(file, reFileName);
        return ApiResponse.success(fileService.upload(file.getOriginalFilename(), reFileName,  type, user));
    }

    /**
     * <p>上传相关附件</p>
     * @param id 主合同id
     * @param file 合同附件(支持多格式)
     * @param user 上传人
     * @return 状态码
     */
    @PostMapping("/upload/{id}")
    public ApiResponse upload(@PathVariable Long id,
                              @RequestParam String type,
                              MultipartFile file,
                              UserEntity user){
        String originFileName = file.getOriginalFilename();
        String suffix = originFileName.substring(originFileName.lastIndexOf(".") + 1);
        String reFileName= UUID.randomUUID() + "." + suffix;
        minIOTool.upload(file, reFileName);
        return ApiResponse.success(fileService.upload(id, file.getOriginalFilename(), reFileName, type, user));
    }

    /**
     * <p>查看文件列表</p>
     *
     * @param id 合同修订记录id
     * @return 文件信息 json[id           文件id,
     *                     type         文件类型 1-合同 2-中标通知书 3-招标文件 4-验收报告 5-发票签收单,
     *                     fileName     文件名称,
     *                     fileUrl      文件地址]
     */
    @GetMapping("/getFileList/{id}")
    public ApiResponse getFileList(@PathVariable Long id){
        return fileService.getFileList(id);
    }

    /**
     * <p>下载某条合同修订文件</p>
     *
     * @param id 附件id
     * @return 文件
     */
    @GetMapping("/downLoad/{id}")
    public void downLoad(@PathVariable Long id, HttpServletResponse response){
        try {
            FileEntity fileEntity = fileService.getById(id);
            minIOTool.download(fileEntity.getFileName(), fileEntity.getFileUrl(), response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>查看某条合同修订文件详情</p>
     *
     * @param id 关联主表id
     * @return 文件信息 json[id           文件id,
     *                     type         文件类型 1-合同
     *                     fileName     文件名称,
     *                     fileUrl      文件地址]
     */
//    @GetMapping("/getContractFile/{id}")
//    public ApiResponse getContractFile(@PathVariable Long id){
//        return fileService.getContractFile(id);
//    }

    /**
     * <p>通过文件Id查文件</p>
     *
     * @param id 文件id
     * @return 文件信息 json[id           文件id,
     *                     type         文件类型 1-合同 2-中标通知书 3-招标文件 4-验收报告 5-发票签收单,
     *                     fileName     文件名称,
     *                     fileUrl      文件地址]
     */
    @GetMapping("/getFile/{id}")
    public ApiResponse getFile(@PathVariable Long id){
        return fileService.getFile(id);
    }

    /**
     * <p>base64转MultipartFile</p>
     *
     * @param json base64
     * @param user 文件上传人
     * @return 状态码
     */
    @PostMapping("/uploadBase64")
    public ApiResponse uploadBase64(@RequestBody String json, UserEntity user) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        MultipartFile file = Base64DecodedMultipartFile.base64ToMultipart(jsonObject.getString("base64"));
        String reFileName= UUID.randomUUID() + ".png";
        minIOTool.upload(file, reFileName);
        return ApiResponse.success(fileService.upload(reFileName, reFileName,  "6", user));
    }
    /**
     * <p>删除相关附件</p>
     * @return 状态码
     */
    @PostMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id, HttpServletResponse response){
        System.out.println(fileService.getById(id).getFileName());
        System.out.println(fileService.getById(id).getFileUrl());
        minIOTool.remove(fileService.getById(id).getFileUrl());
        return fileService.deleteFile(id);
    }

}
