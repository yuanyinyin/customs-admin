package com.nteport.admin.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author Yang, Botao
 * @since 2022-03-08
 */
@Data
@TableName("db_file")
@ApiModel(value = "FileEntity对象", description = "附件表")
public class FileEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("关联主表id")
    private Long recordId;

    @ApiModelProperty("类型: 1-?? 2-??")
    private String type;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件地址")
    private String fileUrl;

}
