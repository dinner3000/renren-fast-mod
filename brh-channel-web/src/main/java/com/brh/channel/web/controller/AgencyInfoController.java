package com.brh.channel.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.brh.channel.common.utils.SerialGen;
import com.brh.channel.shiro.controller.AbstractController;
import com.brh.channel.web.annotation.TokenEnabled;
import com.brh.channel.web.annotation.TokenVerificationEnabled;
import com.brh.channel.web.entity.AgencyInfoEntity;
import com.brh.channel.web.service.AgencyInfoService;

/**
 * AGENCY_INFO
 * 
 * @date 2017-08-08 14:44:47
 */
@RestController
@RequestMapping("/cust/agencyinfo")
public class AgencyInfoController extends AbstractController {
	@Autowired
	private AgencyInfoService agencyInfoService;
	@Autowired
	private SerialGen serialGen;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	// @RequiresPermissions("agencyinfo:list")
	public R list(@RequestParam Map<String, Object> params) {
		logger.debug("合作商列表查询" + params);
		// 查询列表数据
		Query query = new Query(params);
		query.put("agencyId", params.get("agencyId"));
		query.put("agencyNm", params.get("agencyNm"));
		if (params.get("cooTyp") != null) {
			query.put("cooTyp", params.get("cooTyp").toString().trim());
		}
		if (params.get("status") != null) {
			query.put("status", params.get("status").toString().trim());
		}
		logger.debug("cooTyp" + query.get("cooTyp"));
		logger.debug("status" + query.get("status"));
		List<AgencyInfoEntity> agencyInfoList = agencyInfoService
				.queryList(query);
		int total = agencyInfoService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(agencyInfoList, total,
				query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{agencyId}")
	// @RequiresPermissions("agencyinfo:info")
	@TokenEnabled
	public R info(@PathVariable("agencyId") String agencyId) {
		AgencyInfoEntity agencyInfo = agencyInfoService.queryObject(agencyId);

		return R.ok().put("agencyInfo", agencyInfo);
	}

	/**
	 * 保存
	 */
	@SysLog("新增合作机构")
	@RequestMapping("/save")
	@RequiresPermissions("agencyinfo:save")
	@TokenVerificationEnabled
	public R save(@RequestBody AgencyInfoEntity agencyInfo) {
		logger.info("新增合作机构信息开始{}", agencyInfo.toString());
		List agencyList = agencyInfoService.queryObject(agencyInfo);
		if (!agencyList.isEmpty()) {
			// 如果有重名 抛出错误
			return R.error(400, "已有名称为" + agencyInfo.getAgencyNm() + "的合作机构");
		}
		/**
		 * payment-2147新增统一社会信用代码与传统三证的判断 如果统一社会信用代码为空 则三证必须同时上送
		 */
		if (StringUtils.isBlank(agencyInfo.getOrgCode())
				&& StringUtils.isBlank(agencyInfo.getOrganCode())
				&& StringUtils.isBlank(agencyInfo.getLicence())
				&& StringUtils.isBlank(agencyInfo.getTaxNo())) {
			return R.error(400, "统一社会信用代码与企业三证不能同时为空");
		}
		if (StringUtils.isBlank(agencyInfo.getOrgCode())) {
			boolean isEnough = false;
			StringBuilder builder = new StringBuilder();
			if (StringUtils.isBlank(agencyInfo.getOrganCode())) {
				isEnough = true;
				builder.append("组织机构代码、");
			}
			if (StringUtils.isBlank(agencyInfo.getLicence())) {
				isEnough = true;
				builder.append("营业执照、");
			}
			if (StringUtils.isBlank(agencyInfo.getTaxNo())) {
				isEnough = true;
				builder.append("纳税人识别号、");
			}
			if (isEnough) {
				int len = builder.toString().length();
				return R.error(400, builder.toString().substring(0, len - 1)
						+ "不能为空");
			}
		}
		agencyInfo.setAgencyId(serialGen.getCreditNo());
		agencyInfo.setOperId(String.valueOf(getUserId()));
		agencyInfo.setStatus("0");// 0有效 1终止
		agencyInfo.setCreateTm(DateUtils.getTimestamp());
		agencyInfo.setTimeStamp(DateUtils.getTimestamp());
		agencyInfoService.save(agencyInfo);
		logger.info("新增合作机构信息结束{}", agencyInfo.toString());
		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("修改合作机构")
	@RequestMapping("/update")
	@RequiresPermissions("agencyinfo:update")
	@TokenVerificationEnabled
	public R update(@RequestBody AgencyInfoEntity agencyInfo) {
		agencyInfo.setTimeStamp(DateUtils.getTimestamp());
		if (StringUtils.isBlank(agencyInfo.getOrgCode())
				&& StringUtils.isBlank(agencyInfo.getOrganCode())
				&& StringUtils.isBlank(agencyInfo.getLicence())
				&& StringUtils.isBlank(agencyInfo.getTaxNo())) {
			return R.error(400, "统一社会信用代码与企业三证不能同时为空");
		}
		if (StringUtils.isBlank(agencyInfo.getOrgCode())) {
			boolean isEnough = false;
			StringBuilder builder = new StringBuilder();
			if (StringUtils.isBlank(agencyInfo.getOrganCode())) {
				isEnough = true;
				builder.append("组织机构代码、");
			}
			if (StringUtils.isBlank(agencyInfo.getLicence())) {
				isEnough = true;
				builder.append("营业执照、");
			}
			if (StringUtils.isBlank(agencyInfo.getTaxNo())) {
				isEnough = true;
				builder.append("纳税人识别号、");
			}
			if (isEnough) {
				int len = builder.toString().length();
				return R.error(400, builder.toString().substring(0, len - 1)
						+ "不能为空");
			}
		}
		agencyInfoService.update(agencyInfo);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除合作机构")
	@RequestMapping("/delete")
	@RequiresPermissions("agencyinfo:delete")
	@TokenVerificationEnabled
	public R delete(@RequestBody String[] agencyIds) {
		agencyInfoService.deleteBatch(agencyIds);

		return R.ok();
	}

	/**
	 * 信息
	 */
	@RequestMapping("/infoByUserId")
	// @RequiresPermissions("agencyinfo:infoByUserId")
	@TokenEnabled
	public R infoByUserId() {
		String userId = getUserIdString();
		String agencyId = agencyInfoService.getAgencyIdByUserId(userId);

		return R.ok().put("agencyId", agencyId);
	}
}
