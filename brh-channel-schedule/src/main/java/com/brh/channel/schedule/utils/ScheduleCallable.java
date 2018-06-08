package com.brh.channel.schedule.utils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.brh.channel.common.utils.R;
import com.brh.channel.common.utils.SpringContextUtils;
/**
 * 支持异步返回的callable线程包装类
 * @author lcl
 *
 */
public class ScheduleCallable implements Callable{
	private Object target;
	private Method method;
	private String params;
	
	public ScheduleCallable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
		this.target = SpringContextUtils.getBean(beanName);
		this.params = params;
		
		if(StringUtils.isNotBlank(params)){
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		}else{
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}
	@Override
	public Object call() throws Exception {
		R r=null;
		try {
			ReflectionUtils.makeAccessible(method);
			if(StringUtils.isNotBlank(params)){
				r=(R)method.invoke(target, params);
			}else{
				r=(R)method.invoke(target);
			}
		}catch (Exception e) {
			return R.error("执行定时任务失败,原因:"+e);
		}
		return r;
	}

}
