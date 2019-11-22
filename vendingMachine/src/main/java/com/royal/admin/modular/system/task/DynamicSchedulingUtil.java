//package com.royal.admin.modular.system.task;
//
//import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;
//import cn.stylefeng.roses.kernel.model.exception.ApiServiceException;
//import cn.stylefeng.roses.kernel.model.exception.ServiceException;
//import com.royal.admin.core.common.constant.cache.Cache;
//import com.royal.admin.core.common.constant.cache.CacheKey;
//import com.royal.admin.core.common.exception.BizExceptionEnum;
//import com.royal.admin.core.util.CacheUtil;
//import com.royal.admin.core.util.DateUtils;
//import com.royal.admin.core.util.QuestionPaperUtil;
//import com.royal.admin.core.util.Tools;
//import com.royal.admin.modular.api.entity.QuestionPaperModel;
//import com.royal.admin.modular.api.service.QuestionPaperService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//@Configuration
//@EnableScheduling
//public class DynamicSchedulingUtil implements SchedulingConfigurer {
//
//    @Autowired
//    private QuestionPaperService questionPaperService;
//    private static final Logger log = LoggerFactory.getLogger(DynamicSchedulingUtil.class);
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//        scheduledTaskRegistrar.addTriggerTask(
//                //1.添加任务内容(Runnable)
//                () -> {
//                    log.info("定时器开始执行了---------------------");
//                    QuestionPaperModel questionPaperModel = questionPaperService.getSchedulingNearest();
//                    questionPaperService.lottery(questionPaperModel);
//                },
//                //2.设置执行周期(Trigger)
//                triggerContext -> {
//                    //2.1 从数据库获取执行周期
//                    QuestionPaperModel questionPaperModel = questionPaperService.getSchedulingNearest();
//                    if (questionPaperModel == null || questionPaperModel.getEndTime() == null) {
//                        Date date = new CronTrigger("15 * * * * ?").nextExecutionTime(triggerContext);
//                        log.info("定时器执行时间为：" + DateUtils.getFormatDateTime(date) + "-----" + "15 * * * * ?");
//                        return date;
//                    }else if(new Date().getTime()>questionPaperModel.getEndTime().getTime()){
//                        Date date = new CronTrigger("15 * * * * ?").nextExecutionTime(triggerContext);
//                        log.info("定时器执行时间为：" + DateUtils.getFormatDateTime(date) + "-----" + "15 * * * * ?");
//                        return date;
//                    }
//                    String cron = DateUtils.getCron(questionPaperModel.getEndTime());
//                    log.info("定时器执行时间为：" + DateUtils.getFormatDateTime(questionPaperModel.getEndTime()) + "-----" + cron);
////                    //2.3 返回执行周期(Date)
//                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
//                }
//        );
//    }
//}
