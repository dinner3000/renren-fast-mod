package com.brh.channel.schedule.dao;

import java.util.List;

import com.brh.channel.common.dao.BaseDao;
import com.brh.channel.schedule.entity.ScheduleJobLogEntity;

/**
 * 定时任务日志
 * 
 * @author lcl
 * @date 2016年12月1日 下午10:30:02
 */
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {

	List<ScheduleJobLogEntity> queryInfoByJobId(Long jobId);
	
}
