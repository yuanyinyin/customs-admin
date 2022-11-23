package com.nteport.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.nteport.admin.entity.system.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @TableName t_notice
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_notice")
@Data
public class TNoticeEntity extends BaseEntity implements Serializable {
    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String text;

    /**
     * 标题前的分类提示
     */
    private String tips;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
