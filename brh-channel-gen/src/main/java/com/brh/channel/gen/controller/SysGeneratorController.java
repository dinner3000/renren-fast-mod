package com.brh.channel.gen.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.brh.channel.common.utils.PageUtils;
import com.brh.channel.common.utils.Query;
import com.brh.channel.common.utils.R;
import com.brh.channel.common.xss.XssHttpServletRequestWrapper;
import com.brh.channel.gen.service.SysGeneratorService;
import com.brh.channel.gen.utils.CamelUtils;

/**
 * 代码生成器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午9:12:58
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {
	@Autowired
	private SysGeneratorService sysGeneratorService;

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:generator:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = sysGeneratorService.queryList(query);
		List<Map<String, Object>> newlist=new ArrayList<Map<String, Object>>();
		
		for (Map<String, Object> map : list) {
			newlist.add(CamelUtils.toReplaceKeyLow(map));
		}
		
		int total = sysGeneratorService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(newlist, total, query.getLimit(),
				query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	@RequiresPermissions("sys:generator:code")
	public void code(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String[] tableNames = new String[] {};
		// 获取表名，不进行xss过滤
		HttpServletRequest orgRequest = XssHttpServletRequestWrapper
				.getOrgRequest(request);
		String tables = orgRequest.getParameter("tables");
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		
		byte[] data = sysGeneratorService.generatorCode(tableNames);

		response.reset();
		response.setHeader("Content-Disposition",
				"attachment; filename=\"channel.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");

		IOUtils.write(data, response.getOutputStream());
	}
}
