package com.brh.channel.web.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brh.channel.common.annotation.SysLog;
import com.brh.channel.common.utils.DateUtils;
import com.brh.channel.common.utils.PageUtils;
import com.brh.channel.common.utils.Query;
import com.brh.channel.common.utils.R;
import com.brh.channel.common.utils.RRException;
import com.brh.channel.shiro.controller.AbstractController;
import com.brh.channel.shiro.utils.ShiroUtils;
import com.brh.channel.web.entity.AgencyInfoEntity;
import com.brh.channel.web.entity.ProdInfoEntity;
import com.brh.channel.web.service.AgencyInfoService;
import com.brh.channel.web.service.ProdInfoService;


/**
 * PROD_INFO
 * 
 * @date 2017-08-14 20:36:05
 */
@RestController
@RequestMapping("/prod/prodinfo")
public class ProdInfoController extends AbstractController{
	@Autowired
	private ProdInfoService prodInfoService;
	@Autowired
	private AgencyInfoService agencyInfoService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("prod:prodinfo:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ProdInfoEntity> prodInfoList = prodInfoService.queryList(query);
		int total = prodInfoService.queryTotal(query);
		
		prodInfoList = dealWithProdInfoList(prodInfoList);
		
		PageUtils pageUtil = new PageUtils(prodInfoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	private List<ProdInfoEntity> dealWithProdInfoList(List<ProdInfoEntity> prodInfoList) {
		// TODO Auto-generated method stub
		for(ProdInfoEntity prodInfoEntity:prodInfoList){
			// 产品类型  0:车抵贷  1:房抵贷
			if("0".equals(prodInfoEntity.getProdTyp())){
				prodInfoEntity.setProdTyp("车抵贷");
			}else if("1".equals(prodInfoEntity.getProdTyp())){
				prodInfoEntity.setProdTyp("房抵贷");
			}
			//结构类型   0:担保方式
//			if("0".equals(prodInfoEntity.getStructTyp())){
//				prodInfoEntity.setStructTyp("担保方式");
//			}
			//还款方式    0:等额本金  1:等额本息  2:一次性还本付息 3:月度结息 4:到期还本  5:季度结息 6:其他   
			if("0".equals(prodInfoEntity.getRepayWay())){
				prodInfoEntity.setRepayWay("等额本金");
			}else if("1".equals(prodInfoEntity.getRepayWay())){
				prodInfoEntity.setRepayWay("等额本息");
			}else if("2".equals(prodInfoEntity.getRepayWay())){
				prodInfoEntity.setRepayWay("一次性还本付息");
			}else if("3".equals(prodInfoEntity.getRepayWay())){
				prodInfoEntity.setRepayWay("月度结息，到期还本");
			}else if("4".equals(prodInfoEntity.getRepayWay())){
				prodInfoEntity.setRepayWay("季度结息，到期还本");
			}else if("5".equals(prodInfoEntity.getRepayWay())){
				prodInfoEntity.setRepayWay("其他");
			}
			//还款周期单位   D:日  M: 月  Y:年
			if("D".equals(prodInfoEntity.getRepayCycle())){
				prodInfoEntity.setRepayCycle("日");
			}else if("M".equals(prodInfoEntity.getRepayCycle())){
				prodInfoEntity.setRepayCycle("月");
			}else{
				prodInfoEntity.setRepayCycle("年");
			}
			//状态 :0草稿  1生效  2失效
			if("0".equals(prodInfoEntity.getProdSts())){
				prodInfoEntity.setProdSts("草稿");
			}else if("1".equals(prodInfoEntity.getProdSts())){
				prodInfoEntity.setProdSts("生效");
			}else if("2".equals(prodInfoEntity.getProdSts())){
				prodInfoEntity.setProdSts("失效");
			}
			prodInfoEntity.setPaymentCycle(prodInfoEntity.getRepayTime() + prodInfoEntity.getRepayCycle());
		}
		return prodInfoList;
	}


	/**
	 * 信息
	 */
	@RequestMapping("/info/{prodId}")
	@RequiresPermissions("prod:prodinfo:info")
	public R info(@PathVariable("prodId") String prodId){
		ProdInfoEntity prodInfo = prodInfoService.queryObject(prodId);
		AgencyInfoEntity agencyInfo = agencyInfoService.queryObject(prodInfo.getIssuer());
		prodInfo.setAgencyInfo(agencyInfo);
		return R.ok().put("prodInfo", prodInfo);
	}
	
	/**
	 * 保存
	 */
	@SysLog("新增产品")
	@RequestMapping("/save")
	@RequiresPermissions("prod:prodinfo:create")
	@Transactional
	public R save(@RequestBody ProdInfoEntity prodInfo){
		logger.debug("保存产品信息{}", prodInfo.toString());
		
		prodInfo.setProdSts("0");  //新增 默认为草稿状态
		prodInfo.setIssuer(prodInfo.getAgencyInfo().getAgencyId());
		prodInfo.setCreateTm(DateUtils.getTimestamp());
		prodInfo.setTimestamp(DateUtils.getTimestamp());
		prodInfo.setOperId(ShiroUtils.getUserId().toString());
		verifyForm(prodInfo);
		
		//校验PROD_NO 业务主键不可重复
		int repeatProdNo = prodInfoService.queryReProdNoByProdNo(prodInfo.getProdNo());
		
		if(repeatProdNo > 0){
			return R.error("产品编号不能重复");
		}
		
		//校验PROD_NM 不能重复
		int repeatProdNm = prodInfoService.queryReProdNmByProdNm(prodInfo.getProdNm());
		
		if(repeatProdNm > 0){
			return R.error("产品名称不能重复");
		}
		
		checkAmount(prodInfo);
		
		prodInfoService.save(prodInfo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改产品")
	@RequestMapping("/update")
	@RequiresPermissions("prod:prodinfo:update")
	@Transactional
	public R update(@RequestBody ProdInfoEntity prodInfo){
		prodInfo.setIssuer(prodInfo.getAgencyInfo().getAgencyId());
//		verifyForm(prodInfo);
		String prodId = prodInfo.getProdId();
		//校验PROD_NM 不能重复
		String repeatProdId = prodInfoService.queryReProdIdByProdNm(prodInfo.getProdNm());
				
		if(null != repeatProdId && !"".equals(repeatProdId)){
			if(!prodId.equals(repeatProdId)){
				return R.error("产品名称不能重复");
			}
		}
		
		checkAmount(prodInfo);
		prodInfo.setTimestamp(DateUtils.getTimestamp());
		prodInfoService.update(prodInfo);
		
		return R.ok();
	}
	
	private void checkAmount(ProdInfoEntity prodInfo) {
		// TODO Auto-generated method stub
		BigDecimal loadMax = prodInfo.getLoadMax();
		BigDecimal loadMin = prodInfo.getLoadMin();
		
		if(null == prodInfo.getLoadMin() || "".equals(prodInfo.getLoadMin()) || loadMin.compareTo(BigDecimal.ZERO) == -1){
			throw new RRException("最低贷款额必须大于等于0");
		}
		if(null == prodInfo.getLoadMax() || "".equals(prodInfo.getLoadMax()) || loadMax.compareTo(BigDecimal.ZERO) != 1){
			throw new RRException("最高贷款额必须大于0");
		}
		if(loadMax.compareTo(loadMin) == -1){
			throw new RRException("最高贷款额必须大于等于最低贷款额");
		}
	}


	/**
	 * 修改产品状态
	 */
	@SysLog("修改产品状态")
	@RequestMapping("/updateProdSts")
	@RequiresPermissions("prod:prodinfo:updateProdSts")
	@Transactional
	public R updateProdSts(@RequestBody ProdInfoEntity prodInfo){
		prodInfo.setIssuer(prodInfo.getAgencyInfo().getAgencyId());
		prodInfo.setTimestamp(DateUtils.getTimestamp());
		prodInfoService.update(prodInfo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除产品")
	@RequestMapping("/delete")
	@Transactional
	@RequiresPermissions("prod:prodinfo:delete")
	public R delete(@RequestBody String[] prodIds){
		prodInfoService.deleteBatch(prodIds);
		
		return R.ok();
	}
	/**
	 * 删除
	 */
	@SysLog("删除产品")
	@RequestMapping("/deleteProd")
	@RequiresPermissions("prod:prodinfo:deleteProd")
	@Transactional
	public R deleteProd(@RequestBody String prodId){
		prodInfoService.deleteProd(prodId);
		
		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(ProdInfoEntity prodInfo ){

		if(null != prodInfo.getProdNo() && "".equals(prodInfo.getProdNo())){
			throw new RRException("产品编号不能为空");
		}	
		if(StringUtils.isBlank(prodInfo.getProdNm())){
			throw new RRException("产品名称不能为空");
		}
		if(null == prodInfo.getProdTyp() || "".equals(prodInfo.getProdTyp())){
			throw new RRException("产品类型不能为空");
		}
		if(null == prodInfo.getAgencyInfo()){
			throw new RRException("产品发行方不能为空");
		}
		if(null == prodInfo.getStructTyp() || "".equals(prodInfo.getStructTyp())){
			throw new RRException("产品结构类型不能为空");
		}
		if(null == prodInfo.getGuaranteeTyp() || "".equals(prodInfo.getGuaranteeTyp())){
			throw new RRException("担保方式不能为空");
		}
		if(null == prodInfo.getRepayWay() || "".equals(prodInfo.getRepayWay())){
			throw new RRException("还款方式不能为空");
		}
		if(null == prodInfo.getRepayCycle() || "".equals(prodInfo.getRepayCycle())){
			throw new RRException("货款期限不能为空");
		}
		if(null == prodInfo.getIssuer() || "".equals(prodInfo.getIssuer())){
			throw new RRException("发行商不能为空");
		}
		if(null == prodInfo.getProdSts() || "".equals(prodInfo.getProdSts())){
			throw new RRException("状态不能为空");
		}
	}
	
}
