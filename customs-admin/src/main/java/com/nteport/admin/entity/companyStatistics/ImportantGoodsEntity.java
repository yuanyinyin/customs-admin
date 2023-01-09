package com.nteport.admin.entity.companyStatistics;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IMPORTANT_GOODS")
public class ImportantGoodsEntity {
    @TableId("ID")
    private String id;

    @TableField("IEFLAG")
    private String ieflag;

    @TableField("HSCODE")
    private String hscode;

    @TableField("GOODS_NAME")
    private String goodsName;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("OWNER_ORG_ID")
    private String ownerOrg_id;

    @TableField("CREATE_DATE")
    private Date createDate;

    @TableField("UPDATE_DATE")
    private Date updateDate;

    @TableField("CREATE_USER")
    private String createUser;

    @TableField("UPDATE_USER")
    private String updateUser;


    @TableField("CREATE_USER_ID")
    private String createUserId;

    @TableField("UPDATE_USER_ID")
    private String updateUserId;

}
