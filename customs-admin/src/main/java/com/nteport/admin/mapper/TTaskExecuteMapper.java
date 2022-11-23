package com.nteport.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nteport.admin.entity.TTaskExecuteEntity;
import org.springframework.stereotype.Repository;


/**
* @author User
* @description 针对表【t_task_execute(指令执行表)】的数据库操作Mapper
* @createDate 2022-09-16 19:23:28
* @Entity com.nteport.admin.entity.TTaskExecute
*/
@Repository
public interface TTaskExecuteMapper extends BaseMapper<TTaskExecuteEntity> {
    /**
     * 更新频率为每天的指令执行
     */
    void updateLate();
}
