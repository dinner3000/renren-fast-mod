package com.brh.channel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月24日 下午11:05:27
 */
@Controller
public class SysPageController {

	/**
	 * 进件信息路由
	 * @param url
	 * @return
	 */
	@RequestMapping("load/{url}.html")
	public String load(@PathVariable("url") String url){
		if (url.contains("check")) {
			return "load/check.html";
		} else {
			return "load/" + url + ".html";
		}
	}

	/**
	 * 页面跳转
	 * @param module
	 * @param function
	 * @param url
	 * @return
	 */
	@RequestMapping("{module}/{function}/{url}.html")
	public String page(@PathVariable("module") String module, @PathVariable("function") String function,
					   @PathVariable("url") String url) {
		return module + "/" + function + "/" + url + ".html";
	}

	/**
	 * 页面跳转
	 * @param module
	 * @param url
	 * @return
	 */
	@RequestMapping("{module}/{url}.html")
	public String page(@PathVariable("module") String module, @PathVariable("url") String url) {
		return module + "/" + url + ".html";
	}
}
