package com.nteport.admin.tool;

import com.nteport.admin.config.MinIOConfig;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@Slf4j
@Component
public class MinIOTool {

    @Autowired
    private MinIOConfig minIOConfig;

    @Autowired
    private MinioClient minioClient;

    /**
     * 附件上传
     *
     * @param multipartFile          附件
     * @param attachmentNameInServer 存储在文件服务器中的附件名称
     */
    public String upload(MultipartFile multipartFile, String attachmentNameInServer) {
        // 获取 原始文件名，例如 hello.txt
        String originalFilename = multipartFile.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            originalFilename = attachmentNameInServer;
        }
        String contentType = multipartFile.getContentType();

        // 桶名称
        String bucketName = minIOConfig.getBucketName();

        try (InputStream inputStream = multipartFile.getInputStream()) {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName)
                    .object(attachmentNameInServer)
                    .stream(inputStream, multipartFile.getSize(), -1)
                    .contentType(contentType).build()
            );
            log.info("附件上传成功, fileName: {}, contentType: {}, size(Byte): {}", originalFilename, contentType, multipartFile.getSize());

            // 异步更新附件URL
            String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(attachmentNameInServer).build());
            return presignedObjectUrl;
        } catch (Exception e) {
            log.error("附件上传失败, fileName: {}, contentType: {}, size(Byte): {}", originalFilename, contentType, multipartFile.getSize());
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 附件下载
     *
     * @param attachmentName         附件原始名称
     * @param attachmentNameInServer 存储在文件服务器中的附件名称
     * @param httpServletResponse    httpServletResponse
     */
    public void download(String attachmentName, String attachmentNameInServer, HttpServletResponse httpServletResponse) throws IOException {
        // 桶名称
        String bucketName = minIOConfig.getBucketName();
        byte[] buffer = null;
        ServletOutputStream out = null;
        InputStream inputStream = null;
        try {
            StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(attachmentNameInServer).build());
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(attachmentName, "UTF-8"));
            httpServletResponse.setContentType(statObjectResponse.contentType());
            httpServletResponse.setCharacterEncoding("UTF-8");

            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(attachmentNameInServer).build());
            IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
            out = httpServletResponse.getOutputStream();
            //读取文件流
            int len = 0;
            buffer = new byte[1024 * 10];
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            log.error("附件下载失败, fileName: {}, bucketName: {}, attachmentNameInServer: {}", attachmentName, bucketName, attachmentNameInServer);
            e.printStackTrace();
        } finally {
            out.close();
            inputStream.close();
        }
    }

    /**
     * 获取预览URL
     * 注：.txt的附件会乱码，PDF正常
     * 注：默认有效期7天
     *
     * @param attachmentNameInServer 存储在文件服务器中的附件名称
     * @return
     */
    public String getPresignedUrl(String attachmentNameInServer) {
        try {
            String bucketName = minIOConfig.getBucketName();
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(attachmentNameInServer).build());

            String serverName = minIOConfig.getUrl();
            String serverUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(attachmentNameInServer).build());
            //todo 临时
//            serverUrl = serverUrl.replace(serverName, "https://www.nteport.com/");
            return serverUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 删除附件
     *
     * @param attachmentNameInServer 存储在文件服务器中的附件名称
     */
    public void remove(String attachmentNameInServer) {
        try {
            String bucketName = minIOConfig.getBucketName();
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(attachmentNameInServer).build());
            log.info("附件: {}, 删除成功!", attachmentNameInServer);
        } catch (Exception e) {
            log.error("附件: {}, 删除失败...", attachmentNameInServer);
            e.printStackTrace();
        }
    }
}
