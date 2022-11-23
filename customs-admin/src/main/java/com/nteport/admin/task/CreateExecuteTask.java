package com.nteport.admin.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nteport.admin.entity.TTaskEntity;
import com.nteport.admin.entity.TTaskExecuteEntity;
import com.nteport.admin.service.TTaskExecuteService;
import com.nteport.admin.service.TTaskService;
import com.nteport.admin.util.ConstantUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 生成指令执行
 */
@Component
@EnableScheduling
public class CreateExecuteTask {

    @Autowired
    private TTaskService taskService;

    @Autowired
    private TTaskExecuteService taskExecuteService;

    //每天凌晨1点执行
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0 0/2 * * * ?")
    @Transactional
    public synchronized void doHandle() {
        //更新已逾期的
        taskExecuteService.updateLate();
        //1.查询所有需要生成执行的指令
        QueryWrapper<TTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("frequency", ConstantUtil.TASK_FREQUENCY_EVERYDAY);
        String nowDate = LocalDate.now().toString();
        queryWrapper.ge("end_date",nowDate);
        queryWrapper.le("start_date",nowDate);
        List<TTaskEntity> list = taskService.list(queryWrapper);
        for(TTaskEntity entity:list){
            TTaskExecuteEntity taskExecute = new TTaskExecuteEntity();
            BeanUtils.copyProperties(entity,taskExecute);
            taskExecute.setId(null);
            taskExecute.setTaskId(entity.getId());
            taskExecute.setExecuted(ConstantUtil.TASK_EXECUTED_TODO);
            taskExecute.setCreateTime(LocalDateTime.now());
            taskExecute.setCreateUser(null);
            taskExecute.setUpdateTime(null);
            taskExecute.setUpdateUser(null);
            taskExecute.setCreatorName(null);
            taskExecuteService.save(taskExecute);
        }
    }

}
