package com.brh.channel.common.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 日志拦截器 
 * @author lcl
 *
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory
			.getLogger(LogInterceptor.class);
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>(
			"startTime");

	/**
	 * 请求前拦截 并加入日志traceId
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		/**
		 * 生成一个reqid，作为线程id
		 */
		String traceId = UUID.randomUUID().toString();
		// 1、开始时间
		long beginTime = System.currentTimeMillis();
		startTimeThreadLocal.set(beginTime);
		/**
		 * 修改线程名字，请求开始时以start开头，结束时以end开头，并拼接上key和时间，这样做有以下好处：
		 * 通过jstack查看堆栈的时候，如果有线程被堵塞
		 * （以start开头的线程都是未跑完的，这种状态下的线程有可能被堵塞），就可以根据reqid去查日志，看程序的哪一步比较耗时。
		 */
		Thread.currentThread().setName("begin-" + traceId + "-" + beginTime);
		/**
		 * 把reqid存入MDC，这样在log4j等日志框架中才可以获取到traceId
		 */
		MDC.put("traceId", traceId);
		/**
		 * 把reqid存入request，这样在tomcat的访问日志中才可以获取到traceId
		 */
		request.setAttribute("traceId", traceId);
		logger.info("begin-" + traceId + "-" + beginTime);
		return super.preHandle(request, response, handler);
	}

	/**
	 * 线程处理完成
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis();// 2、结束时间
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		long consumeTime = endTime - beginTime;// 3、消耗的时间
		Thread.currentThread().setName("end-" + MDC.get("traceId") + "-"
				+ endTime);
		logger.info("end-" + MDC.get("traceId") + "-costTime:"+ consumeTime+"ms");
		super.afterCompletion(request, response, handler, ex);
	}

}
