package com.nteport.admin.entity.companyStatistics;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("IMPORTANT_COMPANY")
public class ImportantCompanyEntity  {
    @TableId("ID")
    private String id;

    @TableField("ORG_ID")
    private String orgId;

    @TableField("ORG_NAME")
    private String orgName;

    @TableField("REMARK")
    private String remark;

    @TableField("OWNER_ORG_ID")
    private String ownerOrg_id;

    @TableField("CUSTOM_CODE")
    private String customCode;


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
