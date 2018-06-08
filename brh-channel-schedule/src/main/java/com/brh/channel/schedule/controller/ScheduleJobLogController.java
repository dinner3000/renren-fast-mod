package com.brh.channel.schedule.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brh.channel.common.utils.DateUtils;
import com.brh.channel.common.utils.PageUtils;
import com.brh.channel.common.utils.Query;
import com.brh.channel.common.utils.R;
import com.brh.channel.schedule.entity.ScheduleJobLogEntity;
import com.brh.channel.schedule.service.ScheduleJobLogService;

/**
 * 定时任务日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:39:52
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<ScheduleJobLogEntity> jobList = scheduleJobLogService.queryList(query);
		int total = scheduleJobLogService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public R info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);
		
		return R.ok().put("log", log);
	}
	
	/**
	 * 查询某一定时任务日志信息
	 */
	@RequestMapping("/scheduleInfo/{jobId}")
//	public R scheduleInfo(@PathVariable("jobId") Long jobId){
	public R scheduleInfo(@RequestParam Map<String, Object> params,
			@PathVariable("jobId") Long jobId){
	
		Query query = new Query(params);
		
		String date = "";
		List<ScheduleJobLogEntity> jobLoglist = scheduleJobLogService.queryList(query);
		
		for(ScheduleJobLogEntity scheduleJobLog : jobLoglist){
			date = DateUtils.transferTimeStr(scheduleJobLog.getCreateTime(),
					DateUtils.TIMESTAMP_FORMAT_NO_SEPARATOR,
					DateUtils.DATE_TIME_PATTERN);
			scheduleJobLog.setCreateTime(date);
		}
		int total = scheduleJobLogService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(jobLoglist, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
}
