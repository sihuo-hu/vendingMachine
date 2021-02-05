package com.royal.admin.modular.system.task;


import com.royal.admin.core.common.constant.cache.Cache;
import com.royal.admin.core.common.constant.cache.CacheKey;
import com.royal.admin.core.util.CacheUtil;
import com.royal.admin.core.util.DateUtils;
import com.royal.admin.core.util.Tools;
import com.royal.admin.modular.system.entity.Order;
import com.royal.admin.modular.system.service.MachinesService;
import com.royal.admin.modular.system.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author royal
 * @ClassName: SchedulingConfig
 * @Description: TODO(定时任务Controller)
 * @date 2018年6月15日 下午2:30:33
 */
@Configuration
@EnableScheduling
public class SchedulingUtil {
    @Autowired
    private MachinesService machinesService;
    @Autowired
    private OrderService orderService;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }

    private static final Logger log = LoggerFactory.getLogger(SchedulingUtil.class);


    /**
     * 结果清算
     */
//    @Scheduled(cron = "1 1 1 * * ?")
    public void resultsClearing() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        log.info(methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr());
        machinesService.syncMachines();
        log.info(methodName + "定时任务结束======》当前时间：" + DateUtils.getCurrDateTimeStr());
    }

    /**
     * 1-创建 2-跳舞中 3-结算中 4-体验完成 5-已完成 6-已取消 7-出货失败 8-分数不到标
     */
//    @Scheduled(cron = "1/5 * * * * ?")
    public void updateOrder() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        log.info(methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr());
        Date date = new Date();
        //订单刚创建1，60秒没有变化，则变为6已取消
        Date createTime = DateUtils.getDateBeforeOrAfterMinute(date, -1);
        orderService.updateByCreateTime(createTime, "1", "6");
        //跳舞中，XX秒没变化则，变为6
        Date updateTime = DateUtils.getDateBeforeOrAfterMinute(date, -2);
        orderService.updateByUpdate(updateTime, "2", "6");
        //结算中，60秒没有变化则变为7
        updateTime = DateUtils.getDateBeforeOrAfterMinute(date, -2);
        orderService.updateByUpdate(updateTime, "3", "7");
        log.info(methodName + "定时任务结束======》当前时间：" + DateUtils.getCurrDateTimeStr());
    }

//    @Scheduled(cron = "1/10 * * * * ?")
    public void selectOrder() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        log.info(methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr());
        orderService.selectZdyOrder();
        log.info(methodName + "定时任务结束======》当前时间：" + DateUtils.getCurrDateTimeStr());
    }
}
