package com.brh.channel.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brh.channel.common.annotation.SysLog;
import com.brh.channel.common.enums.LoadStatus;
import com.brh.channel.common.enums.PaySts;
import com.brh.channel.common.enums.RepayTyp;
import com.brh.channel.common.enums.StructType;
import com.brh.channel.common.enums.TimeCls;
import com.brh.channel.common.utils.DateUtils;
import com.brh.channel.common.utils.ExcelFileUtil;
import com.brh.channel.common.utils.PageUtils;
import com.brh.channel.common.utils.Query;
import com.brh.channel.common.utils.R;
import com.brh.channel.common.utils.SerialGen;
import com.brh.channel.shiro.controller.AbstractController;
import com.brh.channel.web.entity.StandingBookEntity;
import com.brh.channel.web.service.AgencyInfoService;
import com.brh.channel.web.service.LoadIncomOrdService;
import com.brh.channel.web.service.ProdInfoService;
import com.brh.workflow.engine.manager.WorkflowManager;

/**
 * 进件管理 LOAD_INCOM_ORD
 * 
 * @author WJLong
 * @date 2017-08-14 10:51:49
 */
@RestController
@RequestMapping("load/income")
public class LoadIncomOrdController extends AbstractController {

	@Autowired
	private LoadIncomOrdService loadIncomOrdService;
	@Autowired
	private SerialGen serialGen;
	@Autowired
	private WorkflowManager workflowManager;
	@Autowired
	private ProdInfoService prodInfoService;
	@Autowired
	private AgencyInfoService agencyInfoService;

	/**
	 * 台账查询
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/standingBook")
	@RequiresPermissions("load:income:standingBook")
	public R listStandingBook(@RequestParam Map<String, Object> params) {
		logger.info("查询台账信息request{}", params.toString());
		// 查询列表数据
		Query query = new Query(params);
		//如果前端传入时间为空  则默认查询近3个月的数据
		if (params.get("startDay") == null || (String.valueOf(params.get("startDay")).equals(""))||params.get("endDay") == null || (String.valueOf(params.get("endDay")).equals(""))) {
			query.put("startDay", DateUtils.getPreMonth(2));//前三个月
			query.put("endDay", DateUtils.getCurrentDate());//当天
		} 
		String agencyId = agencyInfoService
				.getAgencyIdByUserId(getUserIdString());
		query.put("agencyId", agencyId);
		List<StandingBookEntity> bookList = loadIncomOrdService
				.queryStandingBookList(query);
		List<StandingBookEntity> list=new ArrayList<StandingBookEntity>();
		
		for (StandingBookEntity entity : bookList) {
			try {
				transferFiled(entity);//转译
				convertBean(entity);
				list.add(entity);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
			
		}
		int total = loadIncomOrdService.queryStandingBookListTotal(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(),
				query.getPage());
		logger.info("查询台账信息end{}", total);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@SysLog("导出台账")
	@RequestMapping("/exportStandingBook")
	public R exportExcel(@RequestBody Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("导出excel输入{}", params.toString());
		//如果前端传入时间为空  则默认查询近3个月的数据
		if (params.get("startDay") == null || (String.valueOf(params.get("startDay")).equals(""))) {
			params.put("startDay", DateUtils.getPreMonth(2));//前三个月
			params.put("endDay", DateUtils.getCurrentMonth());//当天
		} 
		String agencyId = agencyInfoService
				.getAgencyIdByUserId(getUserIdString());
		params.put("agencyId", agencyId);
		
		List<StandingBookEntity> bookList = loadIncomOrdService
				.queryStandingBookList(params);
		/**
		 * 加工list 对时间，null值就行处理
		 */
		List<StandingBookEntity> list=new ArrayList<StandingBookEntity>();
		for (StandingBookEntity entity : bookList) {
			
			try {
				transferFiled(entity);//转译
				convertBean(entity);//去掉null
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
		String filePath = ExcelFileUtil.createExcel("StandingBook", list,
				"台账信息", request, response);
		logger.debug("生成excel文件结束{}", filePath);
		return R.ok().put("filePath", filePath);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/downLoadStandingBook")
	public void downloadExcel(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("下载excel文件开始{}", request.getParameter("filePath"));
		ExcelFileUtil.downloadExcel(request.getParameter("filePath"), URLEncoder.encode("台账信息"),
				request, response);
		logger.info("下载文件结束{}", request.getParameter("filePath"));
	}
	
	
	/**
	 * 保险代收台账查询
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/insureFeeBook")
	@RequiresPermissions("load:income:insureFeeBook")
	public R listInsureFeeBook(@RequestParam Map<String, Object> params) {
		logger.info("查询保险代收台账信息request{}", params.toString());
		// 查询列表数据
		Query query = new Query(params);
		//如果前端传入时间为空  则默认查询近3个月的数据
		if (params.get("payStartDay") == null || (String.valueOf(params.get("payStartDay")).equals(""))) {
			query.put("payStartDay", DateUtils.getPreMonth(2));//前三个月
			query.put("payEndDay", DateUtils.getCurrentMonth());//当月
		}
		if (params.get("underStartDay") == null || (String.valueOf(params.get("underStartDay")).equals(""))) {
			query.put("underStartDay", DateUtils.getPreMonth(2));//前三个月
			query.put("underEndDay", DateUtils.getCurrentMonth());//当月
		} 
		
		List<StandingBookEntity> bookList = loadIncomOrdService
				.queryInsureFeeBookList(query);
		List<StandingBookEntity> list=new ArrayList<StandingBookEntity>();
		
		for (StandingBookEntity entity : bookList) {
			try {
				transferFiled(entity);//转译
				convertBean(entity);
				list.add(entity);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
			
		}
		int total = loadIncomOrdService.queryInsureFeeBookListTotal(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(),
				query.getPage());
		logger.info("查询台账信息end{}", total);
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@SysLog("导出保险代收台账")
	@RequestMapping("/exportInsureFeeBook")
	public R exportInsureFeeExcel(@RequestBody Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("导出保险代收台账excel输入{}", params.toString());
		//如果前端传入时间为空  则默认查询近3个月的数据
		if (params.get("payStartDay") == null || (String.valueOf(params.get("payStartDay")).equals(""))) {
			params.put("payStartDay", DateUtils.getPreMonth(2));//前三个月
			params.put("payEndDay", DateUtils.getCurrentMonth());//当月
		}
		if (params.get("underStartDay") == null || (String.valueOf(params.get("underStartDay")).equals(""))) {
			params.put("underStartDay", DateUtils.getPreMonth(2));//前三个月
			params.put("underEndDay", DateUtils.getCurrentMonth());//当月
		} 
		
		List<StandingBookEntity> bookList = loadIncomOrdService
				.queryInsureFeeBookList(params);
		/**
		 * 加工list 对时间，null值就行处理
		 */
		List<StandingBookEntity> list=new ArrayList<StandingBookEntity>();
		for (StandingBookEntity entity : bookList) {
			
			try {
				transferFiled(entity);//转译
				convertBean(entity);//去掉null
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
		String filePath = ExcelFileUtil.createExcel("InsureFeeBook", list,
				"保险代收台账", request, response);
		logger.debug("生成excel文件结束{}", filePath);
		return R.ok().put("filePath", filePath);
	}

	/**
	 * 通用下载excel方法
	 * @param request
	 * @param response
	 */
	@RequestMapping("/downLoadExcelBook")
	public void downloadInsureExcel(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("通用下载excel文件开始{}", request.getParameter("filePath"),request.getParameter("fileName"));
		ExcelFileUtil.downloadExcel(request.getParameter("filePath"), URLEncoder.encode(request.getParameter("fileName")),
				request, response);
		logger.info("通用下载文件结束{}", request.getParameter("filePath"));
	}
	
	
	/**
	 * 转译字段
	 * @param entity
	 */
	public void transferFiled(StandingBookEntity entity){
		logger.info(entity.toString());
		//贷款金额
		Float loadAmt=Float.valueOf(entity.getLoadAmt());
		//金额转译成以千为单位的数据
		DecimalFormat df = new DecimalFormat("#,###.00"); 
		if(loadAmt!=null){
	    String m = df.format(loadAmt);  
	    entity.setLoadAmt(m);
		}
	    //保单金额
	    Float insureAmt=Float.valueOf(entity.getInsurAmt());
	    if(insureAmt!=null){
	    entity.setInsurAmt(df.format(insureAmt));
	    }
	    //保费金额
	    Float insureFee=Float.valueOf(entity.getInsurFee());
	    if(insureFee!=null){
	    entity.setInsurFee(df.format(insureFee));
	    }
	    //保证金金额
	    Float depositFee=Float.valueOf(entity.getDepositFee());
	    if(depositFee!=null){
	    entity.setDepositFee(df.format(depositFee));	
	    }
	    //邦积分服务费金额
	    Float integralFee=Float.valueOf(entity.getIntegralFee());
	    if(integralFee!=null){
	    entity.setIntegralFee(df.format(integralFee));
	    }
	    //资金方服务费金额
	    Float capitalSideFee=Float.valueOf(entity.getCapitalSideFee());
	    if(capitalSideFee!=null){
	    entity.setCapitalSideFee(df.format(capitalSideFee));	
	    }
	    //缴费状态
	    if(entity.getPaySts()!=null){
		entity.setPaySts(PaySts.getPaySts(entity.getPaySts()).getDesc());
	    }
	    //结构类型
		entity.setStructTyp(StructType.getStructType(entity.getStructTyp()).getDesc());
		//贷款期限
		entity.setLoadTm1(entity.getLoadTm1()+TimeCls.getTimeCls(entity.getLoadCycle1()).getDesc());
		//保险期限
		entity.setLoadTm2(entity.getLoadTm2()+TimeCls.getTimeCls(entity.getLoadCycle2()).getDesc());
		//还款方式 0:一次性还本付息  1:先息后本  2:等额本息   
		entity.setRepayWay(RepayTyp.getRepayTyp(entity.getRepayWay()).getDesc());
		//状态
		entity.setStatus(LoadStatus.getLoadStatus(entity.getStatus()).getDesc());
		//质押信息
		//贷款用途
		entity.setLoadPurpose("企业流动资金补充");//写死值
	}
	/**
	 * 转换bean中null值为空  或初始值
	 * @param entity
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public void convertBean(StandingBookEntity entity) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {
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
				Type type = entity.getClass().getDeclaredField(typeName)
						.getGenericType();
				String typeStr = entity.getClass().getDeclaredField(typeName)
						.getGenericType().toString();
				
				Object obj = method.invoke(entity, null);
				if (obj == null) {
					if (typeStr.equals("class java.math.BigDecimal")) {
						Method m1 = StandingBookEntity.class.getMethod(setName,
								BigDecimal.class);
						m1.invoke(entity, new BigDecimal(0));
					}
					if (typeStr.equals("class java.lang.String")) {
						Method m1 = StandingBookEntity.class.getMethod(setName,
								String.class);
						m1.invoke(entity, "");
					}
				}
				//对oracle中省略小于1的小数第一位0的情况，例如.16 等，不现实小数第一位0的问题进行优化处理补0
				String notNull=String.valueOf(obj);
				if(notNull.startsWith(".")){
					Method m1 = StandingBookEntity.class.getMethod(setName,
							String.class);
					m1.invoke(entity, "0"+notNull);
				}
			}
		}
		logger.info("转换后entity{}", entity.toString());
	}

}
