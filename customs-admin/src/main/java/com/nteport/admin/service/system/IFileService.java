package com.nteport.admin.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.FileEntity;
import com.nteport.admin.entity.system.UserEntity;


/**
 * <p>
 * 附件表 服务类
 * </p>
 *
 * @author Yang, Botao
 * @since 2022-03-09
 */
public interface IFileService extends IService<FileEntity> {

    /**
     * <p>查看某条合同修订文件详情</p>
     * 现已扩展为通过关联主表id查文件
     *
     * @param id 关联主表id
     * @return 文件信息 json[id           文件id,
     *                     type         文件类型 1-合同 2-中标通知书 3-招标文件 4-验收报告 5-发票签收单,
     *                     fileName     文件名称,
     *                     fileUrl      文件地址]
     */
    ApiResponse getFileList(String id);

    /**
     * 数据库中储存文件记录
     * @param fileName 文件名
     * @param user 上传者
     * @return 附件id
     */
    String upload(String fileName, String path, String type, UserEntity user);

    /**
     * 数据库中储存文件记录
     * @param id 主合同id
     * @param fileName 文件名
     * @param user 上传者
     * @return 附件id
     */
    String upload(String id, String fileName, String path, String type, UserEntity user);

    /**
     * 獲取合同附件
     * @param id 合同id
     * @return
     */
//    ApiResponse getContractFile(Long id);

    ApiResponse deleteFile(String id);

    /**
     * <p>通过文件id查找文件</p>
     * @param id
     * @return
     */
    ApiResponse getFile(String id);

    String getImageUrl(String fileId);
}
