package run.aquan.iron.system.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import run.aquan.iron.system.exception.IronException;

/**
 * @Class TestJob
 * @Description
 * @Author Saving
 * @Date 2020/10/30 15:33
 * @Version 1.0
 **/
@Slf4j
public class TestJob extends QuartzJobBean {

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            Trigger trigger = jobExecutionContext.getTrigger();
            log.warn("定时任务执行成功executeInternal:" + new DateTime());
            DateTime startTime = DateUtil.offsetSecond(new DateTime(), 10);
            log.info("下次定时任务执行时间executeInternal:" + startTime);
            Trigger build = trigger.getTriggerBuilder().startAt(startTime).build();
            scheduler.rescheduleJob(trigger.getKey(), build);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw new IronException("任务调度异常:" + e.getMessage());
        }
    }

    public static void createJob(Integer ttl) {
        try {
            //TODO 根据返回的过去的时间创建定时刷新任务
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 启动scheduler
            scheduler.start();
            // 创建HelloworldJob的JobDetail实例，并设置name/group
            JobDetail jobDetail = JobBuilder.newJob(TestJob.class)
                    .withIdentity("TestJob","testGroup")
                    //JobDataMap可以给任务传递参数
                    // .usingJobData("job_param","job_param1")
                    .storeDurably()
                    .build();
            // 创建Trigger触发器设置使用cronSchedule方式调度
            DateTime startTime = DateUtil.offsetSecond(new DateTime(), ttl);
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("TestJobTrigger","testTriggerGroup")
                    // .usingJobData("job_trigger_param","job_trigger_param1")
                    // .startNow()
                    .startAt(startTime)
                    // .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                    // .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ? "))
                    // .withSchedule(simpleSchedule().withIntervalInSeconds(Math.toIntExact(5)).withRepeatCount(0))
                    .build();
            // 注册JobDetail实例到scheduler以及使用对应的Trigger触发时机
            scheduler.scheduleJob(jobDetail,trigger);
            log.info("initTestJob下次定时任务执行时间:" + startTime);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            throw new IronException("ErpAccessToken定时刷新任务创建失败:" + e.getMessage());
        }
    }

}
