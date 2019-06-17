package com.isoft.igis.common.task;

import com.isoft.igis.common.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledService {
    @Autowired
    private LogService logService;

    /**
     * 定时清除日志
     */
    @Scheduled(cron = "0 0 3 ? * MON") //每周一上午3点执行任务
    public void closeLog() {
        System.out.println("执行定时清理日志");
        logService.DeleteAll();
    }
}
