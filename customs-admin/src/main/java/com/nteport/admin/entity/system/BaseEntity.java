package com.nteport.admin.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author ZZQ
 */
@Data
public class BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

    private String creatorName;

    /**
     * 	备注
     */
//    private String memo;

    /**
     * 创建记录调用
     *
     * @param user 执行者
     */
    public void createTimeStamp(@NonNull UserEntity user) {
        createUser = user.getId();
        createTime = LocalDateTime.now();
        updateUser = user.getId();
        updateTime = LocalDateTime.now();
        creatorName = user.getRealName();
    }

    /**
     * 更新记录调用
     *
     * @param user 执行者
     */
    public void updateTimeStamp(@NonNull UserEntity user) {
        updateUser = user.getId();
        updateTime = LocalDateTime.now();
    }

}
