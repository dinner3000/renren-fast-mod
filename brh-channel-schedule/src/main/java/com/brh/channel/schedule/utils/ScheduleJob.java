package com.brh.channel.schedule.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.brh.channel.common.utils.DateUtils;
import com.brh.channel.common.utils.R;
import com.brh.channel.common.utils.SpringContextUtils;
import com.brh.channel.schedule.entity.ScheduleJobEntity;
import com.brh.channel.schedule.entity.ScheduleJobLogEntity;
import com.brh.channel.schedule.service.ScheduleJobLogService;


/**
 * 定时任务
 * 
 * @author ab052627
 * @date 2017年8月9日 下午12:44:21
 */
public class ScheduleJob extends QuartzJobBean {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ExecutorService service = Executors.newSingleThreadExecutor(); 
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJobEntity scheduleJob = (ScheduleJobEntity) context.getMergedJobDataMap()
        		.get(ScheduleJobEntity.JOB_PARAM_KEY);
        
        //获取spring bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");
        
        //数据库保存执行记录
        ScheduleJobLogEntity log = new ScheduleJobLogEntity();
        log.setJobId(scheduleJob.getJobId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        log.setCreateTime(DateUtils.getTimestamp());
        
        //任务开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            //执行任务
        	logger.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
//            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), 
//            		scheduleJob.getMethodName(), scheduleJob.getParams());
        	ScheduleCallable task = new ScheduleCallable(scheduleJob.getBeanName(), 
             		scheduleJob.getMethodName(), scheduleJob.getParams());
           // Future<?> future = service.submit(task);
        	Future<?> future=service.submit(task);
        	R r=(R)future.get();
        	logger.info("线程执行结果返回"+r.toString());//ExecutorService submit 封装了异步返回的线程执行结果
            
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			//任务状态    0：成功    1：失败
			if(String.valueOf(r.get("code")).equals("0")){
			log.setStatus(0);
			}else{
			log.setStatus(1);	
			}
			log.setError(StringUtils.substring(r.get("msg").toString(), 0, 2000));
			
			logger.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
		} catch (Exception e) {
			logger.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			
			//任务状态    0：成功    1：失败
			log.setStatus(1);
			log.setError(StringUtils.substring(e.toString(), 0, 2000));
		}finally {
			scheduleJobLogService.save(log);
		}
    }
}
