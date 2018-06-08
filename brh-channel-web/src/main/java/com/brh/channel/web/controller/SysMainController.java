package com.brh.channel.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brh.channel.common.service.SysLogService;
import com.brh.channel.common.utils.PageUtils;
import com.brh.channel.common.utils.Query;
import com.brh.channel.common.utils.R;
import com.brh.channel.shiro.controller.AbstractController;
import com.brh.channel.shiro.service.SysUserRoleService;
import com.brh.channel.shiro.service.SysUserService;
import com.brh.workflow.engine.entity.WorkflowEntity;
import com.brh.workflow.engine.manager.WorkflowManager;
import com.brh.workflow.engine.util.WorkflowPage;

/**
 * 流程查询相关controller
 * 
 * @author lcl
 * 
 */
@Controller
@RequestMapping("/sys")
public class SysMainController extends AbstractController {
	private static Logger LOG = Logger.getLogger(SysMainController.class);
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private WorkflowManager workflowManager;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/flow/list")
	@RequiresPermissions("sys:flow:list")
	public R listFlow(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		LOG.debug("查询已部署流程信息");
		Query query = new Query(params);
		int[] pageParams = new int[2];
		pageParams[0] = Integer.parseInt(query.get("offset").toString());
		pageParams[1] = Integer.parseInt(query.get("limit").toString());

		WorkflowPage<WorkflowEntity> page = new WorkflowPage<WorkflowEntity>();
		List<WorkflowEntity> listWork = workflowManager.queryProcessDefine(
				page, pageParams);

		PageUtils pageUtil = new PageUtils(listWork, Integer.parseInt(String
				.valueOf(page.getTotalCount())), query.getLimit(),
				query.getPage());
		LOG.debug(listWork.size());
		LOG.debug("查询已部署流程信息" + page.getTotalCount());
		return R.ok().put("page", pageUtil);
	}
}
