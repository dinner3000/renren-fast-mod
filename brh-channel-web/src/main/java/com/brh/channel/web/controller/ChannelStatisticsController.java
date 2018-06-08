package com.brh.channel.web.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brh.channel.common.annotation.SysLog;
import com.brh.channel.common.utils.DateUtils;
import com.brh.channel.common.utils.ExcelFileUtil;
import com.brh.channel.common.utils.PageUtils;
import com.brh.channel.common.utils.Query;
import com.brh.channel.common.utils.R;
import com.brh.channel.shiro.controller.AbstractController;
import com.brh.channel.shiro.utils.ShiroUtils;
import com.brh.channel.web.service.ChannelStatisticsService;
import com.brh.channel.web.service.dto.ChannelInvestDto;
import com.brh.channel.web.service.dto.ChannelRegistDto;
import com.brh.channel.web.service.dto.ChannelStatisticsDto;


/**
 * CHANNEL_STATISTICS
 * 
 * @author auto
 * @email auto
 * @date 2018-05-23 14:55:25
 */
@RestController
@RequestMapping("channel")
public class ChannelStatisticsController extends AbstractController{
	@Autowired
	private ChannelStatisticsService channelStatisticsService;

	/**
	 * 渠道汇总统计列表总数查询
	 * @param request
	 * @param response
	 */
	@RequestMapping("/list")
	@RequiresPermissions("channelstatistics:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<ChannelStatisticsDto> channelStatisticsList = channelStatisticsService.queryList(query);
		
		PageUtils pageUtil = new PageUtils(channelStatisticsList, channelStatisticsList.size(), query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 查询单一渠道汇总信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/singleChannelInfo")
	@RequiresPermissions("channel:single:info")
	@SysLog("单一渠道汇总信息")
	public R singleInfo(@RequestParam Map<String, Object> params){
		String channel = (String) ShiroUtils.getSessionAttribute("username");
		params.put("channel", channel);
		//查询列表数据
		Query query = new Query(params);
		List<ChannelStatisticsDto> list = channelStatisticsService.querySingleInfo(query);
//		List<ChannelStatisticsDto> list = new ArrayList<ChannelStatisticsDto>();
//		list.add(dto);
		PageUtils pageUtil = new PageUtils(list, 1, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 渠道注册统计列表查询
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getChannelRegistInfoList")
	@RequiresPermissions("channel:regist:list")
	public R registList(@RequestParam Map<String, Object> params){
		if(StringUtils.isBlank(String.valueOf(params.get("channel")))){
			String channel = (String) ShiroUtils.getSessionAttribute("username");
			params.put("channel", channel);
		}
		//查询列表数据
		Query query = new Query(params);

		List<ChannelRegistDto> userInfoList = channelStatisticsService.getChannelRegistInfo(query);
		int total = channelStatisticsService.queryTotalChannelRegist(query);

		PageUtils pageUtil = new PageUtils(userInfoList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 生成注册数据统计excel
	 * @param request
	 * @param response
	 */
	@SysLog("生成注册数据统计")
	@RequestMapping("/genRegistData")
	public R genRegistDataExcel(@RequestBody Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) {
		//如果前端传入时间为空  则默认查询近3个月的数据
		if (StringUtils.isBlank(String.valueOf(params.get("startDay")))
				||StringUtils.isBlank(String.valueOf(params.get("endDay")))) {
			params.put("startDay", DateUtils.getPreMonth(2));//前三个月
			params.put("endDay", DateUtils.getCurrentMonth());//当月
		}
		if(StringUtils.isBlank(String.valueOf(params.get("channel")))){
			String channel = (String) ShiroUtils.getSessionAttribute("username");
			params.put("channel", channel);
		}
		logger.info("生成注册数据统计excel输入{}", params.toString());
		List<ChannelRegistDto> bookList = channelStatisticsService
				.getChannelRegistInfoTotal(params);
		//加工list 对时间，null值就行处理
		List<ChannelRegistDto> list=new ArrayList<ChannelRegistDto>();
		for (ChannelRegistDto entity : bookList) {
			try {
				convertBean(entity,ChannelRegistDto.class);//去掉null
				list.add(entity);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		if(list.isEmpty()){
			return R.error(400, "没有符合条件的导出数据");
		}
		logger.info("excel List{}", list.get(0).toString());
		String filePath = ExcelFileUtil.createExcel("ChannelRegist", list,
				"注册数据统计", request, response);
		logger.debug("生成excel文件结束{}", filePath);
		return R.ok().put("filePath", filePath);
	}
	
	/**
	 * 渠道注册excel下载
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/downloadRegistData")
	public void downloadRegistData(HttpServletRequest request, HttpServletResponse response) 
			throws UnsupportedEncodingException {
		logger.info("下载excel文件开始{}", request.getParameter("filePath"));
		ExcelFileUtil.downloadExcel(request.getParameter("filePath"), URLEncoder.encode("渠道注册统计", "UTF-8"),
				request, response);
		logger.info("下载文件结束{}", request.getParameter("filePath"));
	}

	/**
	 * 投资统计列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/investList")
	@RequiresPermissions("channel:invest:list")
	public R investList(@RequestParam Map<String, Object> params){
		if(StringUtils.isBlank(String.valueOf(params.get("channel")))){
			String channel = (String) ShiroUtils.getSessionAttribute("username");
			params.put("channel", channel);
		}
		//查询列表数据
		Query query = new Query(params);
		List<ChannelInvestDto> userInfoList = channelStatisticsService.getChannelInvestInfo(query);
		int total = channelStatisticsService.queryTotalChannelInvest(query);
		PageUtils pageUtil = new PageUtils(userInfoList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 被邀请人投资统计excel下载
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/downloadInviteeData")
	public void downloadInviteeData(HttpServletRequest request, HttpServletResponse response) 
			throws UnsupportedEncodingException {
		logger.info("下载excel文件开始{}", request.getParameter("filePath"));
		ExcelFileUtil.downloadExcel(request.getParameter("filePath"), URLEncoder.encode("渠道被邀请人投资统计","UTF-8"),
				request, response);
		logger.info("下载文件结束{}", request.getParameter("filePath"));
	}
	
	/**
	 * 生成投资数据统计excel
	 * @param request
	 * @param response
	 */
	@SysLog("生成投资数据统计")
	@RequestMapping("/genInvestData")
	public R genInvestDataExcel(@RequestBody Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) {
		//如果前端传入时间为空  则默认查询近3个月的数据
		if (StringUtils.isBlank(String.valueOf(params.get("startDay")))
				||StringUtils.isBlank(String.valueOf(params.get("endDay")))) {
			params.put("startDay", DateUtils.getPreMonth(2));//前三个月
			params.put("endDay", DateUtils.getCurrentMonth());//当月
		}
		if(StringUtils.isBlank(String.valueOf(params.get("channel")))){
			String channel = (String) ShiroUtils.getSessionAttribute("username");
			params.put("channel", channel);
		}
		logger.info("生成注册数据统计excel输入{}", params.toString());
		List<ChannelInvestDto> bookList = channelStatisticsService
				.getChannelInvestInfoTotal(params);
		//加工list 对时间，null值就行处理
		List<ChannelInvestDto> list=new ArrayList<ChannelInvestDto>();
		for (ChannelInvestDto entity : bookList) {
			try {
				convertBean(entity,ChannelInvestDto.class);//去掉null
				list.add(entity);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		if(list.isEmpty()){
			return R.error(400, "没有符合条件的导出数据");
		}
		logger.info("excel List{}", list.get(0).toString());
		String filePath = ExcelFileUtil.createExcel("ChannelInvest", list,
				"投资数据统计", request, response);
		logger.debug("生成excel文件结束{}", filePath);
		return R.ok().put("filePath", filePath);
	}
	
	/**
	 * 投资数据统计excel下载
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/downloadInvestData")
	public void downloadInvestData(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("下载excel文件开始{}", request.getParameter("filePath"));
		ExcelFileUtil.downloadExcel(request.getParameter("filePath"), URLEncoder.encode("渠道投资统计","UTF-8"),
				request, response);
		logger.info("下载文件结束{}", request.getParameter("filePath"));
	}

	/**
	 * 被邀请人投资统计列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/inviteeList")
	@RequiresPermissions("channel:invitee:list")
	public R inviteeList(@RequestParam Map<String, Object> params){
		if(StringUtils.isBlank(String.valueOf(params.get("channel")))){
			String channel = (String) ShiroUtils.getSessionAttribute("username");
			params.put("channel", channel);
		}
		//查询列表数据
		Query query = new Query(params);
		List<ChannelInvestDto> userInfoList = channelStatisticsService.getChannelInviteeInfo(query);
		int total = channelStatisticsService.queryTotalChannelInvitee(query);
		PageUtils pageUtil = new PageUtils(userInfoList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 生成被邀请人投资数据统计excel
	 * @param request
	 * @param response
	 */
	@SysLog("生成被邀请人投资数据统计")
	@RequestMapping("/genInviteeData")
	public R genInviteeDataExcel(@RequestBody Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(String.valueOf(params.get("startDay")))
				||StringUtils.isBlank(String.valueOf(params.get("endDay")))) {
			params.put("startDay", DateUtils.getPreMonth(2));//前三个月
			params.put("endDay", DateUtils.getCurrentMonth());//当月
		}
		if(StringUtils.isBlank(String.valueOf(params.get("channel")))){
			String channel = (String) ShiroUtils.getSessionAttribute("username");
			params.put("channel", channel);
		}
		logger.info("生成注册数据统计excel输入{}", params.toString());
		List<ChannelInvestDto> bookList = channelStatisticsService
				.getChannelInviteeInfoTotal(params);
		List<ChannelInvestDto> list=new ArrayList<ChannelInvestDto>();
		for (ChannelInvestDto entity : bookList) {
			try {
				convertBean(entity,ChannelInvestDto.class);//去掉null
				list.add(entity);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		if(list.isEmpty()){
			return R.error(400, "没有符合条件的导出数据");
		}
		logger.info("excel List{}", list.get(0).toString());
		String filePath = ExcelFileUtil.createExcel("ChannelInvest", list,
				"投资数据统计", request, response);
		logger.debug("生成excel文件结束{}", filePath);
		return R.ok().put("filePath", filePath);
	}
	
	/**
	 * 转换bean中null值为空  或初始值
	 * @param entity
	 * @param entityClass
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public void convertBean(Object entity,Class<?> entityClass) throws IllegalAccessException,
	IllegalArgumentException, InvocationTargetException,NoSuchMethodException, SecurityException,
	NoSuchFieldException {
		logger.info("转换前entity{}", entity.toString());
		Method[] methods = entity.getClass().getMethods();
		int length = methods.length;
		for (int i = 0; i < length; i++) {
			// methods[i]
			Method method = methods[i];
			if (method.getName().startsWith("get")) {
				// setName 设置
				String setName = "set"
						+ method.getName().substring(3,
								method.getName().length());
				String typeName = method.getName().substring(3,
						method.getName().length());
				typeName = typeName.substring(0, 1).toLowerCase()
						+ typeName.substring(1);
				if(typeName.equals("class")){
					continue;
				}
				entity.getClass().getDeclaredField(typeName)
				.getGenericType();
				String typeStr = entity.getClass().getDeclaredField(typeName)
						.getGenericType().toString();

				Object obj = method.invoke(entity);
				if (obj == null) {
					if (typeStr.equals("class java.math.BigDecimal")) {
						Method m1 = entityClass.getMethod(setName,
								BigDecimal.class);
						m1.invoke(entity, new BigDecimal(0));
					}
					if (typeStr.equals("class java.lang.String")) {
						Method m1 = entityClass.getMethod(setName,
								String.class);
						m1.invoke(entity, "");
					}
				}
				//对oracle中省略小于1的小数第一位0的情况，例如.16 等，不现实小数第一位0的问题进行优化处理补0
				String notNull=String.valueOf(obj);
				if(notNull.startsWith(".")){
					Method m1 = entityClass.getMethod(setName,
							String.class);
					m1.invoke(entity, "0"+notNull);
				}
			}
		}
		logger.info("转换后entity{}", entity.toString());
	}

}
