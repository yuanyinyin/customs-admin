package com.nteport.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.FileEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.FileMapper;
import com.nteport.admin.service.system.IFileService;
import com.nteport.admin.tool.MinIOTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 附件表 服务实现类
 * </p>
 *
 * @author Yang, Botao
 * @since 2022-03-09
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements IFileService {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private MinIOTool minIOTool;

    /**
     * <p>查看某条合同修订文件详情</p>
     * 现已扩展为通过关联主表id查文件
     *
     * @param id 关联主表id
     * @return 文件信息 json[id           文件id,
     * type         文件类型 1-合同 2-中标通知书 3-招标文件 4-验收报告 5-发票签收单,
     * fileName     文件名称,
     * fileUrl      文件地址]
     */
    @Override
    public ApiResponse getFileList(String id) {
        ApiResponse response = new ApiResponse();
        QueryWrapper<FileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("record_id", id);

        return response.fillMessage(fileMapper.selectList(queryWrapper));
    }

    /**
     * 数据库中储存文件记录
     *
     * @param fileName 文件名
     * @param user     上传者
     * @return 附件id
     */
    @Override
    public String upload(String fileName, String path, String type, UserEntity user) {
        FileEntity file = new FileEntity();
        file.setFileName(fileName);
        file.setType(type);
        file.setFileUrl(path);
        file.createTimeStamp(user);
        fileMapper.insert(file);
        return file.getId();
    }

    /**
     * 数据库中储存文件记录
     *
     * @param id       主合同id
     * @param fileName 文件名
     * @param user     上传者
     * @return 附件id
     */
    @Override
    public String upload(String id, String fileName, String path, String type, UserEntity user) {
        FileEntity file = new FileEntity();
        file.setFileName(fileName);
        file.setRecordId(id);
        file.setType(type);
        file.setFileUrl(path);
        file.createTimeStamp(user);
        fileMapper.insert(file);
        return file.getId();
    }

    /**
     * 獲取合同附件
     *
     * @param id 合同id
     * @return
     */
//    @Override
//    public ApiResponse getContractFile(Long id) {
//        if (id == null) {
//            return ApiResponse.fail(EnumCode.BAD_REQUEST);
//        }
//        try {
//            ContractEntity contract = contractMapper.selectById(id);
//            if (contract == null) {
//                return ApiResponse.fail(EnumCode.ERROR_GONE);
//            }
//
//            return ApiResponse.success(fileMapper.selectById(contract.getFileId()));
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            return ApiResponse.fail(EnumCode.ERROR_SERVER);
//        }
//    }

    @Override
    public ApiResponse deleteFile(String id) {
        ApiResponse response = new ApiResponse();
        FileEntity fileEntity = fileMapper.selectById(id);
        if (fileEntity == null) {
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
        fileMapper.deleteById(id);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse getFile(String id) {
        if (id == null) {
            return ApiResponse.fail(EnumCode.BAD_REQUEST);
        }
        try {
            return ApiResponse.success(fileMapper.selectById(id));
        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponse.fail(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 获取预览图片的url
     *
     * @param fileId 图片id
     * @return 图片url
     */
    @Override
    public String getImageUrl(String fileId) {
        if (fileId == null) {
            return "";
        }

        FileEntity fileEntity = fileMapper.selectById(fileId);
        if (fileEntity == null) {
            return "";
        }

        String url = minIOTool.getPresignedUrl(fileEntity.getFileUrl());
        if ("false".equals(url)) {
            url = "";
        }

        return url;
    }
}
