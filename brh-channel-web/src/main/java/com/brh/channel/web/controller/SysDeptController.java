package com.brh.channel.web.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brh.channel.common.annotation.SysLog;
import com.brh.channel.common.utils.PageUtils;
import com.brh.channel.common.utils.Query;
import com.brh.channel.common.utils.R;
import com.brh.channel.common.utils.RRException;
import com.brh.channel.web.entity.AgencyInfoEntity;
import com.brh.channel.web.entity.SysDeptEntity;
import com.brh.channel.web.service.AgencyInfoService;
import com.brh.channel.web.service.SysDeptService;

/**
 * SYS_DEPT
 * 
 * @date 2017-08-10 16:30:59
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private AgencyInfoService agencyInfoService;
	
	private static Logger logger = LoggerFactory
			.getLogger(SysDeptController.class);
	/**
	 * 列表
	 */
	@RequestMapping("/listAll")
	@RequiresPermissions("sys:dept:list")
	public List<SysDeptEntity> listAll(@RequestParam Map<String, Object> params){
		logger.info("部门列表查询开始{}", params.toString());
		//查询列表数据
		List<SysDeptEntity> sysDeptList = sysDeptService.queryListAll();
		
		return sysDeptList;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:dept:list")
	public R list(@RequestParam Map<String, Object> params){
		logger.info("部门列表查询开始{}", params.toString());
		//查询列表数据
        Query query = new Query(params);

		List<SysDeptEntity> sysDeptList = sysDeptService.queryList(query);
		int total = sysDeptService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysDeptList, total, query.getLimit(), query.getPage());
		logger.info("部门列表查询结束{}", params.toString());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 选择菜单(添加、修改部门菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:dept:select")
	public R select(){
		//查询列表数据
		List<SysDeptEntity> deptList = sysDeptService.queryNotButtonList();
		
		//添加顶级菜单
		SysDeptEntity root = new SysDeptEntity();
		root.setDeptId(new BigDecimal(0));
		root.setName("部门菜单");
		root.setParentId(new BigDecimal(-1));
		root.setOpen(true);
		deptList.add(root);
		return R.ok().put("deptList", deptList);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{deptId}")
	@RequiresPermissions("sys:dept:info")
	public R info(@PathVariable("deptId") BigDecimal deptId){
		SysDeptEntity sysDept = sysDeptService.queryObject(deptId);
		
		AgencyInfoEntity agencyInfo = new AgencyInfoEntity();
		logger.debug("agencyId= " + sysDept.getAgencyId());
		
		if(null != sysDept.getAgencyId() && !"".equals(sysDept.getAgencyId())){
			agencyInfo = agencyInfoService.queryObject(sysDept.getAgencyId());
		}
		sysDept.setAgencyInfo(agencyInfo);
		
		return R.ok().put("sysDept", sysDept);
	}
	
	/**
	 * 保存
	 */
	@SysLog("新增部门")
	@RequestMapping("/save")
	@RequiresPermissions("sys:dept:create")
	@Transactional
	public R save(@RequestBody SysDeptEntity sysDept){
		
//		sysDept.setDelFlag((long) 0);//是否删除标志
		sysDept.setAgencyId(sysDept.getAgencyInfo().getAgencyId());
		sysDept.setAgencyNm(sysDept.getAgencyInfo().getAgencyNm());
		verifyForm(sysDept);
//		checkReDeptName(sysDept.getDeptId(),sysDept.getName());
		sysDeptService.save(sysDept);
		

		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改部门")
	@RequestMapping("/update")
	@RequiresPermissions("sys:dept:update")
	@Transactional
	public R update(@RequestBody SysDeptEntity sysDept){
//		checkReDeptName(sysDept.getDeptId() ,sysDept.getName());
		
		if(null == sysDept.getAgencyInfo().getAgencyId()){
			sysDept.getAgencyInfo().setAgencyId("");
		}
		if(null == sysDept.getAgencyInfo().getAgencyNm()){
			sysDept.getAgencyInfo().setAgencyNm("");
		}
		
		sysDept.setAgencyId(sysDept.getAgencyInfo().getAgencyId());
		sysDept.setAgencyNm(sysDept.getAgencyInfo().getAgencyNm());
		sysDeptService.update(sysDept);
		
//		verifyForm(sysDept);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除部门")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:dept:delete")
	@Transactional
	public R delete(@RequestBody BigDecimal[] deptIds){
		sysDeptService.deleteBatch(deptIds);
		return R.ok();
	}
	
	/**
	 * 通过userId获取部门信息
	 */
	@RequestMapping("/deptInfoByUserId/{userId}")
	@RequiresPermissions("sys:dept:deptInfoByUserId")
	public R info(@PathVariable("userId") String userId){
		SysDeptEntity sysDept = sysDeptService.deptInfoByUserId(userId);
		
		return R.ok().put("sysDept", sysDept);
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysDeptEntity sysDept){
		if(StringUtils.isBlank(sysDept.getName())){
			throw new RRException("部门名称不能为空");
		}
		
		if(null == sysDept.getParentId()){
			throw new RRException("上级部门菜单不能为空");
		}
		
//		if(StringUtils.isBlank(sysDept.getAgencyId())){
//			throw new RRException("发行商ID不能为空");
//		}
//		
//		if(StringUtils.isBlank(sysDept.getAgencyNm())){
//			throw new RRException("发行商名称不能为空");
//		}
	}
	
	//新增和修改校验部门名称不能重复
	private void checkReDeptName (BigDecimal deptId, String deptName){
		
		//校验新增
		if(null == deptId){
			int repeatDeptNm = sysDeptService.queryReDeptNmByDeptNm(deptName);
			if(repeatDeptNm > 0){
				throw new RRException("部门名称不能重复");
			}
		}else{
			//校验修改
			BigDecimal repeatDeptId = sysDeptService.queryReDeptIdByDeptNm(deptName);
			
			if(null != repeatDeptId && !"".equals(repeatDeptId)){
				
				if(deptId.compareTo(repeatDeptId) != 0){
					throw new RRException("部门名称不能重复");
				}
			}
		}
				
	}
}
